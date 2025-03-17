package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentDaoRepository extends JpaRepository<StudentDao, UUID> {
    StudentDao findByNumEtu(String numEtu);
    List<StudentDao> findByPromoAndTp(PromoEnum promo, String tp);
}
