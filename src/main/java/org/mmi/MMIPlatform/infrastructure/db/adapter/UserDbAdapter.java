package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.User;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.db.repository.UserDaoRepository;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDbAdapter {

    private final UserDaoRepository userDaoRepository;
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


    public String saveUserDao(UserDao userDao) {
        if (!userDao.getPassword().matches(PASSWORD_PATTERN)) {
            return "Password must be at least 8 characters long and include a digit, a lowercase letter, an uppercase letter, and a special character.";
        }
        try {
            userDao.setPassword(argon2PasswordEncoder.encode(userDao.getPassword()));
            this.userDaoRepository.save(userDao);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "User saved successfully\n" + userDao;
    }

    public UserDao getUserDaoById(UUID id) {
        return this.userDaoRepository.findById(id).orElse(null);
    }

    public UserDao getUserDaoByEmail(String email) {
        UserDao userDao = null;
        try {
            userDao = this.userDaoRepository.findByEmail(email);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return userDao;
    }

    public String deleterUserDaoById(UUID id) {
        try {
            this.userDaoRepository.deleteById(id);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "User deleted successfully";
    }

    public String updateUserDao(UserDao userDao) {
        try {
            this.userDaoRepository.save(userDao);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "User updated successfully";
    }

}
