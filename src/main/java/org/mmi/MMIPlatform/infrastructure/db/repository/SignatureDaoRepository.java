package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.SignatureDao;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SignatureDaoRepository extends JpaRepository<SignatureDao, UUID> {
}
