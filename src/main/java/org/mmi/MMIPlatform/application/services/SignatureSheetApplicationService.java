package org.mmi.MMIPlatform.application.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.mmi.MMIPlatform.application.dto.SignatureSheetDto;
import org.mmi.MMIPlatform.domain.mapper.SignatureSheetDtoMapper;
import org.mmi.MMIPlatform.domain.services.SignatureSheetDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SignatureSheetApplicationService {

    private final SignatureSheetDomainService signatureSheetDomainService;
    private final SignatureSheetDtoMapper signatureSheetDtoMapper;


    public SignatureSheetDto getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        return signatureSheetDtoMapper.signatureSheetToSignatureSheetDto(this.signatureSheetDomainService.getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt));
    }

    public List<SignatureSheetDto> getSignatureSheetByModuleName(String moduleName) throws Exception {
        return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetListByModuleName(moduleName));
    }

    public List<SignatureSheetDto> getSignatureSheetList() throws Exception {

        return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetList());

    }

    public List<SignatureSheetDto> getSignatureSheetListByPromo(String promo) throws Exception {
        return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetListByPromo(promo));
    }

    public List<SignatureSheetDto> getSignatureSheetListByPromoAndNumEtu(String Promo, String numEtu) throws Exception {
        return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetListByPromoAndNumEtu(Promo, numEtu));
    }

    public SignatureSheetDto postSignatureSheet(SignatureSheetDto signatureSheetDto) throws Exception {

        return signatureSheetDtoMapper.signatureSheetToSignatureSheetDto(this.signatureSheetDomainService.postSignatureSheet(this.signatureSheetDtoMapper.signatureSheetDtoToSignatureSheet(signatureSheetDto)));

    }

    public String deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {

        return this.signatureSheetDomainService.deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName
                , promo, createdAt, finishAt);

    }

    public String deleteSignatureSheet(SignatureSheetDto signatureSheetDto) throws Exception {
        return this.signatureSheetDomainService.deleteSignatureSheet(this.signatureSheetDtoMapper.signatureSheetDtoToSignatureSheet(signatureSheetDto));
    }
}
