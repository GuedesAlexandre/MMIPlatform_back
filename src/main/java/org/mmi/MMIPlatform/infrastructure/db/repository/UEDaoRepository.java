package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.UEDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UEDaoRepository extends JpaRepository<UEDao, UUID> {
}
