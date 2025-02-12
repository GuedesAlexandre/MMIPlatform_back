package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Signature;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureDaoMapper {


    private final StudentDaoMapper studentDaoMapper;

    public Signature signatureDaoToSignature(SignatureDao signatureDao) {
        if(signatureDao == null) {
            throw new NullPointerException("signature required");
        }
        return Signature.builder()
                .id(signatureDao.getId())
                .sign(signatureDao.getSign())
                .student(studentDaoMapper.studentDaoToStudent(signatureDao.getStudentDao()))
                .build();
    }

    public List<Signature> signatureDaoListToSignatureList(List<SignatureDao> signatureDaoList) {
        return signatureDaoList.stream().map(this::signatureDaoToSignature).toList();
    }

    public SignatureDao signatureToSignatureDao(Signature signature) {
        if(signature == null) {
            throw new NullPointerException("signature required");
        }
        return SignatureDao.builder()
                .id(signature.getId())
                .sign(signature.getSign())
                .studentDao(studentDaoMapper.studentToStudentDao(signature.getStudent()))
                .build();
    }

    public List<SignatureDao> signatureListToSignatureDaoList(List<Signature> signatureList) {
        return signatureList.stream().map(this::signatureToSignatureDao).toList();
    }
}
