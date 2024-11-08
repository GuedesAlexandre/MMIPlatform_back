package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleDaoRepository extends JpaRepository<ModuleDao, UUID> {
}
