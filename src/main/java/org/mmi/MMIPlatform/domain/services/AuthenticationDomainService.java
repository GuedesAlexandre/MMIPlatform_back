package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.User;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.UserDbAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.UserDaoMapper;
import org.mmi.MMIPlatform.infrastructure.security.jwt.JwtAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationDomainService {

    private final UserDaoMapper userDaoMapper;
    private final UserDbAdapter userDbAdapter;
    private final JwtAdapter jwtAdapter;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public User initUser(User user){
        UserDao userDao = this.userDaoMapper.userToUserDao(user);
        try{
            if(userDbAdapter.getUserDaoByEmail(user.getEmail()) != null){
                 log.error("User already exists with email : " + user.getEmail());
            }
         this.userDbAdapter.saveUserDao(userDao);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return this.userDaoMapper.userDaoToUser(userDao);
    }

    public List<User> getAllUsers(){
        return this.userDaoMapper.userDaoListToUserList(this.userDbAdapter.getAllUserDao());
    }

    public User updateUser(User user){
        UserDao userDao = this.userDaoMapper.userToUserDao(user);
        try{
            this.userDbAdapter.updateUserDao(userDao);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return this.userDaoMapper.userDaoToUser(userDao) ;
    }

    public String deleteUser(String email){
        try{
            this.userDbAdapter.deleteUserDaoByEmail(email);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
        return "User deleted successfully with email : " + email;
    }

    public String authenticateUser(String email, String password){
        try{
            UserDao userDb = this.userDbAdapter.getUserDaoByEmail(email);
            if(userDb == null) {
                throw new IllegalArgumentException("User not found with : " + email);
            }
            if(argon2PasswordEncoder.matches(password, userDb.getPassword())){
                return this.jwtAdapter.generateToken(userDb);
            }else{
                throw new IllegalArgumentException("Incorrect password");
            }
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }
    public Boolean validateToken(String token) {
        return this.jwtAdapter.validateToken(token);
    }
}
