package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteDaoRepository extends JpaRepository<NoteDao, UUID> {
}
