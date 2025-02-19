package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.SignatureDto;
import org.mmi.MMIPlatform.domain.models.Signature;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureDtoMapper {

    private final StudentDtoMapper userStudentDtoMapper;

    public SignatureDto signatureToSignatureDto(Signature signature) {
        return SignatureDto.builder()
                .sign(signature.getSign())
                .studentDto(userStudentDtoMapper.studentToStudentDto(signature.getStudent()))
                .justification(signature.getJustification())
                .build();
    }

    public List<SignatureDto> signatureListToSignatureDtoList(List<Signature> signatureList) {
        return signatureList.stream().map(this::signatureToSignatureDto).toList();
    }

    public Signature signatureDtoToSignature(SignatureDto signatureDto) {
        return Signature.builder()
                .sign(signatureDto.getSign())
                .student(userStudentDtoMapper.studentDtoToStudent(signatureDto.getStudentDto()))
                .justification(signatureDto.getJustification())
                .build();
    }

    public List<Signature> signatureDtoListToSignatureList(List<SignatureDto> signatureDtoList) {
        return signatureDtoList.stream().map(this::signatureDtoToSignature).toList();
    }
}
