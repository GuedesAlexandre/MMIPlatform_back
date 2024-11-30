package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.UserDto;
import org.mmi.MMIPlatform.domain.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDtoMapper {

    private final ModuleDtoMapper moduleDtoMapper;

    public UserDto userToUserDto(User user){
        return UserDto.builder()
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
                .access(user.getAccess())
                .modules(this.moduleDtoMapper.moduleListToModuleDtoList(user.getModules()))
                .build();
    }

    public List<UserDto> userListToUserDtoList(List<User> users){
        return users.stream().map(this::userToUserDto).toList();
    }

    public User userDtoToUser(UserDto userDto){
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .name(userDto.getName())
                .firstName(userDto.getFirstName())
                .phone(userDto.getPhone())
                .address(userDto.getAddress())
                .city(userDto.getCity())
                .country(userDto.getCountry())
                .establishment(userDto.getEstablishment())
                .access(userDto.getAccess())
                .modules(this.moduleDtoMapper.moduleDtoListToModuleList(userDto.getModules()))
                .build();
    }

    public List<User> userDtoListToUserList(List<UserDto> userDtos){
        return userDtos.stream().map(this::userDtoToUser).toList();
    }

}
