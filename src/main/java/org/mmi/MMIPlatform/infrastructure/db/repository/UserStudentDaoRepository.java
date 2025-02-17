package org.mmi.MMIPlatform.infrastructure.db.repository;

import org.mmi.MMIPlatform.infrastructure.dao.UserStudentDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserStudentDaoRepository extends JpaRepository<UserStudentDao, UUID> {

    UserStudentDao findByEmail(String email);
    Optional<UserStudentDao> findByNumEtu(String numEtu);
}
