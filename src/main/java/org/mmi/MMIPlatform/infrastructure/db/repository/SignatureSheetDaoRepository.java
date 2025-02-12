package org.mmi.MMIPlatform.infrastructure.db.repository;

import lombok.NonNull;
import org.mmi.MMIPlatform.infrastructure.dao.SignatureSheetDao;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface SignatureSheetDaoRepository extends JpaRepository<SignatureSheetDao, UUID> {
    List<SignatureSheetDao> findByPromo(@NonNull PromoEnum promo);

    List<SignatureSheetDao> findByModuleName(@NonNull String moduleName);

    SignatureSheetDao findByModuleNameAndPromoAndCreatedAtAndFinishAt(@NonNull String moduleName, @NonNull PromoEnum promo, @NonNull Date createdAt, @NonNull Date finishAt);

    void deleteSignatureSheetDaoByModuleNameAndPromoAndCreatedAtAndFinishAt(@NonNull String moduleName, @NonNull PromoEnum promo, @NonNull Date createdAt, @NonNull Date finishAt);



}
