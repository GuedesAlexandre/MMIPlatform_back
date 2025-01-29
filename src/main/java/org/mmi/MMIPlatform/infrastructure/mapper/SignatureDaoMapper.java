package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Signature;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignatureDaoMapper {

    private final UserStudentDaoMapper userStudentDaoMapper;

    public Signature signatureDaoToSignature(SignatureDao signatureDao) {
        return Signature.builder()
                .id(signatureDao.getId())
                .sign(signatureDao.getSign())
                .userStudent(userStudentDaoMapper.userStudentDaoToUserStudent(signatureDao.getUserStudentDao()))
                .build();
    }

    public SignatureDao signatureToSignatureDao(Signature signature) {
        return SignatureDao.builder()
                .id(signature.getId())
                .sign(signature.getSign())
                .userStudentDao(userStudentDaoMapper.userStudentToUserStudentDao(signature.getUserStudent()))
                .build();
    }
}
