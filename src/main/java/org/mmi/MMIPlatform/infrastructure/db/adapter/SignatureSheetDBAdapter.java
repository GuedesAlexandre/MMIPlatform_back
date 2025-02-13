package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.AllArgsConstructor;

import org.mmi.MMIPlatform.infrastructure.dao.SignatureDao;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureSheetDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.SignatureDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.SignatureSheetDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.stereotype.Service;

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

    public List<SignatureSheetDao> getSignatureSheetListByModuleName(String moduleName) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByModuleName(moduleName);
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
        }
    }

    public SignatureSheetDao getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, PromoEnum.valueOf(promo), new Date(Long.parseLong(createdAt)), new Date(Long.parseLong(finishAt)));
        } catch (Exception e) {
            throw (new Exception(e.getMessage()));
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
            throw new Exception("Failed to save signature sheet: " + e.getMessage(), e);
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

}
