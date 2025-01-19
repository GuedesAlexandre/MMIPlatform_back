package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InternshipDaoRepository extends JpaRepository<InternshipDao, UUID> {
}
