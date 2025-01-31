package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.UserStudentDto;
import org.mmi.MMIPlatform.domain.models.UserStudent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStudentDtoMapper {

    public UserStudentDto userStudentToUserStudentDto(UserStudent userStudent) {
        return UserStudentDto.builder()
                .numEtu(userStudent.getNumEtu())
                .email(userStudent.getEmail())
                .password(userStudent.getPassword())
                .lastName(userStudent.getLastName())
                .firstName(userStudent.getFirstName())
                .createdAt(userStudent.getCreatedAt())
                .build();
    }

    public List<UserStudentDto> userStudentListToUserStudentDtoList(List<UserStudent> userStudentList) {
        return userStudentList.stream().map(this::userStudentToUserStudentDto).toList();
    }

    public UserStudent userStudentDtoToUserStudent(UserStudentDto userStudentDto) {
        return UserStudent.builder()
                .numEtu((userStudentDto.getNumEtu()))
                .email(userStudentDto.getEmail())
                .password(userStudentDto.getPassword())
                .lastName(userStudentDto.getLastName())
                .firstName(userStudentDto.getFirstName())
                .createdAt(userStudentDto.getCreatedAt())
                .build();
    }

    public List<UserStudent> userStudentDtoListToUserStudentList(List<UserStudentDto> userStudentDtoList) {
        return userStudentDtoList.stream().map(this::userStudentDtoToUserStudent).toList();
    }
}
