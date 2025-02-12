package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.SignatureSheet;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureSheetDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureSheetDaoMapper {

    private final SignatureDaoMapper signatureDaoMapper;
    private final UserStudentDaoMapper userStudentDaoMapper;
    private final StudentDaoMapper studentDaoMapper;

    public SignatureSheet signatureSheetDaoToSignatureSheet(SignatureSheetDao signatureSheetDao) {
        if(signatureSheetDao == null) {
            throw new NullPointerException("signature sheet required");
        }
        return SignatureSheet.builder()
                .id(signatureSheetDao.getId())
                .promo(String.valueOf(signatureSheetDao.getPromo()))
                .moduleName(signatureSheetDao.getModuleName())
                .createdAt(signatureSheetDao.getCreatedAt())
                .finishAt(signatureSheetDao.getFinishAt())
                .signatures(signatureDaoMapper.signatureDaoListToSignatureList(signatureSheetDao.getSignatureDaos()))
                .students(studentDaoMapper.studentsDaostoStudents(signatureSheetDao.getStudentDaos()))
                .build();
    }

    public List<SignatureSheet> signatureSheetDaoListToSignatureSheetList(List<SignatureSheetDao> signatureSheetDaoList) {
        return signatureSheetDaoList.stream().map(this::signatureSheetDaoToSignatureSheet).toList();
    }

    public SignatureSheetDao signatureSheetToSignatureSheetDao(SignatureSheet signatureSheet) {
        if(signatureSheet == null) {
            throw new NullPointerException("signature sheet required");
        }
        return SignatureSheetDao.builder()
                .id(signatureSheet.getId())
                .promo(PromoEnum.valueOf(signatureSheet.getPromo()))
                .moduleName(signatureSheet.getModuleName())
                .createdAt(signatureSheet.getCreatedAt())
                .finishAt(signatureSheet.getFinishAt())
                .signatureDaos(signatureDaoMapper.signatureListToSignatureDaoList(signatureSheet.getSignatures()))
                .studentDaos(studentDaoMapper.studentListToStudentDaoList(signatureSheet.getStudents()))
                .build();
    }

    public List<SignatureSheetDao> signatureSheetListToSignatureSheetDaoList(List<SignatureSheet> signatureSheets) {
        return signatureSheets.stream().map(this::signatureSheetToSignatureSheetDao).toList();
    }
}
