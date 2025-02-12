package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.AllArgsConstructor;

import org.mmi.MMIPlatform.application.dto.SignatureSheetDto;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureSheetDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.SignatureDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.SignatureSheetDaoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SignatureSheetDBAdapter {

    private final SignatureSheetDaoRepository signatureSheetDaoRepository;
    private final SignatureDaoRepository signatureDaoRepository;

    public List<SignatureSheetDao> getSignatureSheetList() throws Exception {
        try {
            return this.signatureSheetDaoRepository.findAll();
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list"));
        }
    }

    public List<SignatureSheetDao> getSignatureSheetListByPromo(String promo) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByPromo(PromoEnum.valueOf(promo));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list by promo"));
        }
    }

    public List<SignatureSheetDao> getSignatureSheetListByModuleName(String moduleName) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByModuleName(moduleName);
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet list by module name"));
        }
    }

    public SignatureSheetDao getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            return this.signatureSheetDaoRepository.findByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, PromoEnum.valueOf(promo), new Date(Long.parseLong(createdAt)), new Date(Long.parseLong(finishAt)));
        } catch (Exception e) {
            throw (new Exception("Error while fetching signature sheet by module name and promo and created at and finish at"));
        }
    }

    public SignatureSheetDao saveSignatureSheet(SignatureSheetDao signatureSheetDao) throws Exception {
        try {

            return this.signatureSheetDaoRepository.save(signatureSheetDao);
        } catch (Exception e) {
            throw (new Exception("Error while saving signature sheet"));
        }
    }

    public String deleteSignatureSheet(SignatureSheetDao signatureSheetDao) throws Exception {
        try {
            this.signatureSheetDaoRepository.delete(signatureSheetDao);
            return "Signature sheet deleted";
        } catch (Exception e) {
            throw (new Exception("Error while deleting signature sheet"));
        }
    }

    public String deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(String moduleName, String promo, String createdAt, String finishAt) throws Exception {
        try {
            this.signatureSheetDaoRepository.deleteSignatureSheetDaoByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, PromoEnum.valueOf(promo), new Date(Long.parseLong(createdAt)), new Date(Long.parseLong(finishAt)));
            return "Signature sheet deleted";
        } catch (Exception e) {
            throw (new Exception("Error while deleting signature sheet by module name and promo and created at and finish at"));
        }
    }

}
