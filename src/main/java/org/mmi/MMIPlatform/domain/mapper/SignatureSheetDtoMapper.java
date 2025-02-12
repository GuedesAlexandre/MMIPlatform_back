package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.SignatureSheetDto;
import org.mmi.MMIPlatform.domain.models.SignatureSheet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureSheetDtoMapper {

    private final SignatureDtoMapper signatureDtoMapper;
    private final UserStudentDtoMapper userStudentDtoMapper;
    private final StudentDtoMapper studentDtoMapper;

    public SignatureSheetDto signatureSheetToSignatureSheetDto(SignatureSheet signatureSheet) {
        return SignatureSheetDto.builder()
                .promo(signatureSheet.getPromo())
                .moduleName(signatureSheet.getModuleName())
                .createdAt(signatureSheet.getCreatedAt())
                .finishAt(signatureSheet.getFinishAt())
                .signatureDtos(signatureDtoMapper.signatureListToSignatureDtoList(signatureSheet.getSignatures()))
                .studentDtos(studentDtoMapper.studentListToStudentDtoList(signatureSheet.getStudents()))
                .build();
    }

    public List<SignatureSheetDto> signatureSheetListToSignatureSheetDtoList(List<SignatureSheet> signatureSheetList) {
        return signatureSheetList.stream().map(this::signatureSheetToSignatureSheetDto).toList();
    }

    public SignatureSheet signatureSheetDtoToSignatureSheet(SignatureSheetDto signatureSheetDto) {
        return SignatureSheet.builder()
                .promo(signatureSheetDto.getPromo())
                .moduleName(signatureSheetDto.getModuleName())
                .createdAt(signatureSheetDto.getCreatedAt())
                .finishAt(signatureSheetDto.getFinishAt())
                .signatures(signatureDtoMapper.signatureDtoListToSignatureList(signatureSheetDto.getSignatureDtos()))
                .students(studentDtoMapper.studentDtoListToStudent(signatureSheetDto.getStudentDtos()))
                .build();
    }

    public List<SignatureSheet> signatureSheetDtoListToSignatureSheetList(List<SignatureSheetDto> signatureSheetListDto) {
        return signatureSheetListDto.stream().map(this::signatureSheetDtoToSignatureSheet).toList();
    }
}
