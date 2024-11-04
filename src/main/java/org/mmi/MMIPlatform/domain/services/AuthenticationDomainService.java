package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.User;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.UserDbAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.UserDaoMapper;
import org.mmi.MMIPlatform.infrastructure.security.jwt.JwtAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationDomainService {

    private final UserDaoMapper userDaoMapper;
    private final UserDbAdapter userDbAdapter;
    private final JwtAdapter jwtAdapter;
    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public String initUser(User user){
        UserDao userDao = this.userDaoMapper.userToUserDao(user);
        try{
         return this.userDbAdapter.saveUserDao(userDao);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    public String updateUser(User user){
        UserDao userDao = this.userDaoMapper.userToUserDao(user);
        try{
            this.userDbAdapter.updateUserDao(userDao);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
        return "User updated successfully : " + userDao ;
    }

    public String deleteUser(User user){
        UserDao userDao = this.userDaoMapper.userToUserDao(user);
        try{
            this.userDbAdapter.deleterUserDaoById(UUID.fromString(userDao.getId()));
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
        return "User deleted successfully : " + userDao ;
    }

    public String authenticateUser(String email, String password){
        try{
            UserDao userDb = this.userDbAdapter.getUserDaoByEmail(email);
            if(argon2PasswordEncoder.matches(password, userDb.getPassword())){
                return this.jwtAdapter.generateToken(userDb);
            }else{
                return "User not found with email :" + email;
            }
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    public Boolean validateToken(String token) {
        return this.jwtAdapter.validateToken(token);
    }
}
