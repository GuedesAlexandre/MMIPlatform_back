package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.UserDto;
import org.mmi.MMIPlatform.domain.mapper.UserDtoMapper;
import org.mmi.MMIPlatform.domain.models.User;
import org.mmi.MMIPlatform.domain.services.AuthenticationDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationApplicationService {

    private final AuthenticationDomainService authenticationDomainService;
    private final UserDtoMapper userDtoMapper;

    public String initUser(UserDto userDto) {
        User user = this.userDtoMapper.userDtoToUser(userDto);
        return this.authenticationDomainService.initUser(user);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = this.userDtoMapper.userDtoToUser(userDto);
        return this.userDtoMapper.userToUserDto(this.authenticationDomainService.updateUser(user));
    }

    public String deleteUser(UserDto userDto) {
        User user = this.userDtoMapper.userDtoToUser(userDto);
        return this.authenticationDomainService.deleteUser(user);
    }

    public List<UserDto> getAllUsers() {
        return this.userDtoMapper.userListToUserDtoList(this.authenticationDomainService.getAllUsers());
    }

    public String authenticateUser(String email, String password) {
        return this.authenticationDomainService.authenticateUser(email, password);
    }

    public Boolean validateToken(String token) {
        return this.authenticationDomainService.validateToken(token);
    }

}
