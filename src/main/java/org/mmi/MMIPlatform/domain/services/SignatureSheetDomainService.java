package org.mmi.MMIPlatform.domain.services;


import lombok.AllArgsConstructor;
import org.mmi.MMIPlatform.domain.models.SignatureSheet;
import org.mmi.MMIPlatform.infrastructure.db.adapter.SignatureSheetDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.SignatureSheetDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SignatureSheetDomainService {

    private final SignatureSheetDBAdapter signatureSheetDBAdapter;
    private final SignatureSheetDaoMapper signatureSheetDaoMapper;

    public SignatureSheet postSignatureSheet(SignatureSheet signatureSheet) throws Exception {
        return this.signatureSheetDaoMapper.signatureSheetDaoToSignatureSheet(this.signatureSheetDBAdapter.saveSignatureSheet(this.signatureSheetDaoMapper.signatureSheetToSignatureSheetDao(signatureSheet)));
    }

    public SignatureSheet getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        return this.signatureSheetDaoMapper.signatureSheetDaoToSignatureSheet(this.signatureSheetDBAdapter.getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt));
    }


    public List<SignatureSheet> getSignatureSheetList() throws Exception {
        return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetList());
    }

    public List<SignatureSheet> getSignatureSheetListByPromo(String promo) throws Exception {
        return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetListByPromo(promo));
    }

    public List<SignatureSheet> getSignatureSheetListByPromoAndNumEtu(String Promo, String numEtu) throws Exception {
        return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetListByPromoAndNumEtu(Promo, numEtu));
    }

    public List<SignatureSheet> getSignatureSheetListByModuleName(String moduleName) throws Exception {

        return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetListByModuleName(moduleName));

    }

    public String deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {

        return this.signatureSheetDBAdapter.deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt);

    }

    public String deleteSignatureSheet(SignatureSheet signatureSheet) throws Exception {
        return this.signatureSheetDBAdapter.deleteSignatureSheet(this.signatureSheetDaoMapper.signatureSheetToSignatureSheetDao(signatureSheet));
    }

}
