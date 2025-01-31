package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.UserStudentDto;
import org.mmi.MMIPlatform.domain.mapper.UserStudentDtoMapper;
import org.mmi.MMIPlatform.domain.models.UserStudent;
import org.mmi.MMIPlatform.domain.services.UserStudentDomainService;
import org.mmi.MMIPlatform.infrastructure.db.adapter.UserStudentDBAdapter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserStudentApplicationService {

    private final UserStudentDomainService userStudentDomainService;
    private final UserStudentDtoMapper userStudentDtoMapper;

    public UserStudentDto postUserStudent(UserStudentDto userStudentDto) {
        UserStudent userStudent = this.userStudentDtoMapper.userStudentDtoToUserStudent(userStudentDto);
        return this.userStudentDtoMapper.userStudentToUserStudentDto(this.userStudentDomainService.postUserStudent(userStudent));
    }

    public String authenticateStudentUser(String email, String password) {
        return this.userStudentDomainService.authenticateStudentUser(email, password);
    }
}
