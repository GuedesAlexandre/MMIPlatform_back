package org.mmi.MMIPlatform.application.services;

import lombok.AllArgsConstructor;
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
        try {
            return signatureSheetDtoMapper.signatureSheetToSignatureSheetDto(this.signatureSheetDomainService.getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet by module name and promo and created at and finish at"));
        }
    }

    public List<SignatureSheetDto> getSignatureSheetByModuleName(String moduleName) throws Exception {
        try {
            return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetListByModuleName(moduleName));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet by module name"));
        }
    }

    public List<SignatureSheetDto> getSignatureSheetList() throws Exception {
        try {
            return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetList());
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list"));
        }
    }

    public List<SignatureSheetDto> getSignatureSheetListByPromo(String promo) throws Exception {
        try {
            return signatureSheetDtoMapper.signatureSheetListToSignatureSheetDtoList(this.signatureSheetDomainService.getSignatureSheetListByPromo(promo));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list by promo"));
        }
    }

    public SignatureSheetDto postSignatureSheet(SignatureSheetDto signatureSheetDto) throws Exception {
        try {
            return signatureSheetDtoMapper.signatureSheetToSignatureSheetDto(this.signatureSheetDomainService.postSignatureSheet(this.signatureSheetDtoMapper.signatureSheetDtoToSignatureSheet(signatureSheetDto)));
        } catch (Exception e) {
            throw (new Exception("Error while posting signature sheet"));
        }
    }

    public String deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            return this.signatureSheetDomainService.deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName
                    , promo, createdAt, finishAt);
        } catch (Exception e) {
            throw (new Exception("Error while deleting signature sheet"));
        }
    }

    public String deleteSignatureSheet(SignatureSheetDto signatureSheetDto) throws Exception {
        try {
            return this.signatureSheetDomainService.deleteSignatureSheet(this.signatureSheetDtoMapper.signatureSheetDtoToSignatureSheet(signatureSheetDto));
        } catch (Exception e) {
            throw (new Exception("Error while deleting signature sheet"));
        }
    }
}
