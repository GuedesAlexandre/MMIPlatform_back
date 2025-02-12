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
        try {
            return this.signatureSheetDaoMapper.signatureSheetDaoToSignatureSheet(this.signatureSheetDBAdapter.saveSignatureSheet(this.signatureSheetDaoMapper.signatureSheetToSignatureSheetDao(signatureSheet)));
        } catch (Exception e) {
            throw (new Exception("Error while saving signature sheet"));
        }
    }

    public SignatureSheet getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            return this.signatureSheetDaoMapper.signatureSheetDaoToSignatureSheet(this.signatureSheetDBAdapter.getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet by module name and promo and created at and finish at"));
        }
    }


    public List<SignatureSheet> getSignatureSheetList() throws Exception {
        try {
            return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetList());
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list"));
        }
    }

    public List<SignatureSheet> getSignatureSheetListByPromo(String promo) throws Exception {
        try {
            return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetListByPromo(promo));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list by promo"));
        }
    }

    public List<SignatureSheet> getSignatureSheetListByModuleName(String moduleName) throws Exception {
        try {
            return this.signatureSheetDaoMapper.signatureSheetDaoListToSignatureSheetList(this.signatureSheetDBAdapter.getSignatureSheetListByModuleName(moduleName));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list by module name"));
        }
    }

    public String deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
          return  this.signatureSheetDBAdapter.deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt);
        } catch (Exception e) {
            throw (new Exception("Error while deleting signature sheet"));
        }
    }

    public String deleteSignatureSheet(SignatureSheet signatureSheet) throws Exception {
        try {
           return this.signatureSheetDBAdapter.deleteSignatureSheet(this.signatureSheetDaoMapper.signatureSheetToSignatureSheetDao(signatureSheet));
        } catch (Exception e) {
            throw (new Exception("Error while deleting signature sheet"));
        }
    }

}
