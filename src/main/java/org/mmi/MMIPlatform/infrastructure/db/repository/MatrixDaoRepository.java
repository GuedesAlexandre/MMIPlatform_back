package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.MatrixDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatrixDaoRepository extends JpaRepository<MatrixDao, UUID>{
}
