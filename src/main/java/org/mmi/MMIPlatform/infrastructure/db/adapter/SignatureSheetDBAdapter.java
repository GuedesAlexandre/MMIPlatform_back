package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.AllArgsConstructor;

import org.mmi.MMIPlatform.infrastructure.dao.SignatureDao;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureSheetDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.JustificationEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.SignatureDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.SignatureSheetDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SignatureSheetDBAdapter {

    private final SignatureSheetDaoRepository signatureSheetDaoRepository;
    private final SignatureDaoRepository signatureDaoRepository;
    private final StudentDaoRepository studentDaoRepository;

    public List<SignatureSheetDao> getSignatureSheetList() throws Exception {
        try {
            return this.signatureSheetDaoRepository.findAll();
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public List<SignatureSheetDao> getSignatureSheetListByPromo(String promo) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByPromo(PromoEnum.valueOf(promo));
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public List<SignatureSheetDao> getSignatureSheetListByPromoAndNumEtu(String promo, String numEtu) throws Exception {
        try {
            return this.getSignatureSheetListByPromo(promo).stream().filter(signatureSheetDao -> signatureSheetDao.getStudentDaos().stream().anyMatch(student -> student.getNumEtu().equals(numEtu))).toList();
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public List<SignatureSheetDao> getSignatureSheetListByModuleName(String moduleName) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByModuleName(moduleName);
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public SignatureSheetDao getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(
            String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            createdAt = createdAt.replace(" ", "+");
            finishAt = finishAt.replace(" ", "+");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

            OffsetDateTime createdAtDateTime = OffsetDateTime.parse(createdAt, dateTimeFormatter);
            Date createdAtDate = Date.from(createdAtDateTime.toInstant());

            OffsetDateTime finishAtDateTime = OffsetDateTime.parse(finishAt, dateTimeFormatter);
            Date finishAtDate = Date.from(finishAtDateTime.toInstant());

            return this.signatureSheetDaoRepository.findByModuleNameAndPromoAndCreatedAtAndFinishAt(
                    moduleName, PromoEnum.valueOf(promo), createdAtDate, finishAtDate);
        } catch (DateTimeParseException e) {
            throw new Exception("Failed to parse date: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("An error occurred: " + e.getMessage(), e);
        }
    }


    public SignatureSheetDao saveSignatureSheet(SignatureSheetDao signatureSheetDao) throws Exception {
        try {
            signatureSheetDao.setSignatureDaos(this.mapSignatureStudentWithStudentObjectInBase(signatureSheetDao.getSignatureDaos()));
            signatureSheetDao.setStudentDaos(signatureSheetDao.getStudentDaos().stream().map(studentDao -> {
                StudentDao existingStudentDao = this.studentDaoRepository.findByNumEtu(studentDao.getNumEtu());
                return existingStudentDao != null ? existingStudentDao : studentDao;
            }).toList());
            this.signatureDaoRepository.saveAll(signatureSheetDao.getSignatureDaos());
            return this.signatureSheetDaoRepository.save(signatureSheetDao);
        } catch (Exception e) {
            throw new Exception("Failed to save signature sheet: " + e.getMessage());
        }
    }

    public List<SignatureDao> mapSignatureStudentWithStudentObjectInBase(List<SignatureDao> signatureDaos) {
        signatureDaos.forEach(signatureDao -> {
            StudentDao studentDao = this.studentDaoRepository.findByNumEtu(signatureDao.getStudentDao().getNumEtu());
            if (studentDao != null) {
                signatureDao.setStudentDao(studentDao);
            } else {
                studentDao = this.studentDaoRepository.save(signatureDao.getStudentDao());
                signatureDao.setStudentDao(studentDao);
            }
        });
        return signatureDaos;
    }

    public String deleteSignatureSheet(SignatureSheetDao signatureSheetDao) throws Exception {
        try {
            this.signatureSheetDaoRepository.delete(signatureSheetDao);
            return "Signature sheet deleted";
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public String deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            this.signatureSheetDaoRepository.deleteSignatureSheetDaoByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, PromoEnum.valueOf(promo), new Date(Long.parseLong(createdAt)), new Date(Long.parseLong(finishAt)));
            return "Signature sheet deleted";
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public String justifyMissing(String moduleName, String promo, String createdAt, String finishAt, String numEtu) {
        try {
            SignatureSheetDao signatureSheetDao = this.getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt);
            signatureSheetDao.getSignatureDaos().stream().filter(signatureDao -> signatureDao.getStudentDao().getNumEtu().equals(numEtu)).findFirst().ifPresent(signatureDao -> {
                signatureDao.setJustification(JustificationEnum.JUSTIFIED);
                this.signatureDaoRepository.save(signatureDao);
            });
            this.signatureSheetDaoRepository.save(signatureSheetDao);
            return "Signature sheet justified for numEtu: " + numEtu;
        } catch (Exception e) {
            throw (new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

}