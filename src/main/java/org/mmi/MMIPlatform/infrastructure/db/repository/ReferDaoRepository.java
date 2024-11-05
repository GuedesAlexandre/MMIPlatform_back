package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.ReferDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReferDaoRepository extends JpaRepository<ReferDao, UUID> {
}
