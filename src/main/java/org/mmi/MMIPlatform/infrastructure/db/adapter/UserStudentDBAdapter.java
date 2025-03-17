package org.mmi.MMIPlatform.infrastructure.db.adapter;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.UserStudentDao;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.UserStudentDaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStudentDBAdapter {

    private final UserStudentDaoRepository userStudentDaoRepository;
    private final StudentDaoRepository studentDaoRepository;
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@edu\\.univ-eiffel\\.fr$";


    public UserStudentDao postUserStudent(UserStudentDao userStudentDao) {
        if (!userStudentDao.getPassword().matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and include a digit, a lowercase letter, an uppercase letter, and a special character.");
        }

        StudentDao studentDaoByNumEtu = this.studentDaoRepository.findByNumEtu(userStudentDao.getNumEtu());
        if (studentDaoByNumEtu == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with numEtu " + userStudentDao.getNumEtu() + " does not exist in the registry. If this is not normal, please contact your university.");
        }

        this.userStudentDaoRepository.findByNumEtu(userStudentDao.getNumEtu()).ifPresent(optionalUserStudent -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student with numEtu" + userStudentDao.getNumEtu() + "already exist");
        });

        if (!userStudentDao.getEmail().matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("This email: '" + userStudentDao.getEmail() + "' does not belong to the university");
        }
        if(!userStudentDao.getEmail().equals(studentDaoByNumEtu.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your email does not match with the email in the registry");
        };

        if (!studentDaoByNumEtu.getFirstName().equals(userStudentDao.getFirstName()) ||
                !studentDaoByNumEtu.getLastName().equals(userStudentDao.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Your personal information doesn't match with the information in the registry");
        }

        userStudentDao.setPassword(argon2PasswordEncoder.encode(userStudentDao.getPassword()));
        userStudentDao.setPromo(String.valueOf(studentDaoByNumEtu.getPromo()));
        userStudentDao.setGroup(studentDaoByNumEtu.getGroup());
        userStudentDao.setCreatedAt(new Date());

        try {
            this.userStudentDaoRepository.save(userStudentDao);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the user student");
        }

        return userStudentDao;
    }

    public UserStudentDao getUserStudentByEmail(String email) {
        UserStudentDao userStudentDao = null;
        try {
            userStudentDao = this.userStudentDaoRepository.findByEmail(email);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return userStudentDao;
    }
}
