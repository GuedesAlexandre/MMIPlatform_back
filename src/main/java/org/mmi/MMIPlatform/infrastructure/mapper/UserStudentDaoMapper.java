package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.UserStudent;
import org.mmi.MMIPlatform.infrastructure.dao.UserStudentDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStudentDaoMapper {

    public UserStudent userStudentDaoToUserStudent(UserStudentDao userStudentDao) {
        if(userStudentDao == null) {
            throw new NullPointerException("user student required");
        }
        return UserStudent.builder()
                .id(userStudentDao.getId())
                .numEtu(userStudentDao.getNumEtu())
                .email(userStudentDao.getEmail())
                .password(userStudentDao.getPassword())
                .lastName(userStudentDao.getLastName())
                .firstName(userStudentDao.getFirstName())
                .createdAt(userStudentDao.getCreatedAt())
                .build();
    }

    public List<UserStudent> userStudentDaoListToUserStudentList(List<UserStudentDao> userStudentDaoList) {
        return userStudentDaoList.stream().map(this::userStudentDaoToUserStudent).toList();
    }

    public UserStudentDao userStudentToUserStudentDao(UserStudent userStudent) {
        if(userStudent == null) {
            throw new NullPointerException("user student required");
        }
        return UserStudentDao.builder()
                .id(userStudent.getId())
                .numEtu(userStudent.getNumEtu())
                .email(userStudent.getEmail())
                .password(userStudent.getPassword())
                .lastName(userStudent.getLastName())
                .firstName(userStudent.getFirstName())
                .createdAt(userStudent.getCreatedAt())
                .build();
    }

    public List<UserStudentDao> userStudentListToUserStudentDaoList(List<UserStudent> userStudentList) {
        return userStudentList.stream().map(this::userStudentToUserStudentDao).toList();
    }
}
