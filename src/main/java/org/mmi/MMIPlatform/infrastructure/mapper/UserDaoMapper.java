package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.User;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PermissionsEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDaoMapper {

    public User userDaoToUser(UserDao userDao) {
        return User.builder()
                .id(userDao.getId())
                .email(null == userDao.getEmail() ? null : userDao.getEmail())
                .password(userDao.getPassword())
                .username(userDao.getUsername())
                .name(userDao.getName())
                .firstName(userDao.getFirstName())
                .phone(userDao.getPhone())
                .address(userDao.getAddress())
                .city(userDao.getCity())
                .country(userDao.getCountry())
                .establishment(userDao.getEstablishment())
                .access(userDao.getAccess().toString())
                .build();
    }

    public UserDao userToUserDao(User user){
        return UserDao.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .name(user.getName())
                .firstName(user.getFirstName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .city(user.getCity())
                .country(user.getCountry())
                .establishment(user.getEstablishment())
                .access(PermissionsEnum.valueOf(user.getAccess()))
                .build();
    }


}
