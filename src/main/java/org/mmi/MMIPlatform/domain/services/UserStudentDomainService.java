package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.UserStudent;
import org.mmi.MMIPlatform.infrastructure.dao.UserStudentDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.UserStudentDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.UserStudentDaoMapper;
import org.mmi.MMIPlatform.infrastructure.security.jwt.JwtAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStudentDomainService {

    private final UserStudentDBAdapter userStudentDBAdapter;
    private final UserStudentDaoMapper userStudentDaoMapper;
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    private final JwtAdapter jwtAdapter;


    public UserStudent postUserStudent(UserStudent userStudent) {
        UserStudentDao userStudentDao = this.userStudentDaoMapper.userStudentToUserStudentDao(userStudent);
        return this.userStudentDaoMapper.userStudentDaoToUserStudent(this.userStudentDBAdapter.postUserStudent(userStudentDao));
    }

    public String authenticateStudentUser(String email, String password) {
        try {
            UserStudentDao userStudentDao = this.userStudentDBAdapter.getUserStudentByEmail(email);
            if (userStudentDao == null) {
                throw new IllegalArgumentException("User Student with email : '" + email + "' not found");
            }
            if(argon2PasswordEncoder.matches(password, userStudentDao.getPassword())){
                return this.jwtAdapter.generateTokenForUserStudent(userStudentDao);
            }else{
                throw new IllegalArgumentException("Incorrect password");
            }
        } catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }


}
