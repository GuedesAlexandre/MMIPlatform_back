package org.mmi.MMIPlatform.application.controllers;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.controllers.models.AuthRequest;
import org.mmi.MMIPlatform.application.dto.UserDto;
import org.mmi.MMIPlatform.application.services.AuthenticationApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class  AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @PostMapping
    public String createUser(@RequestBody UserDto userDto){
        return this.authenticationApplicationService.initUser(userDto);
    }

    @PutMapping
    public String updateUser(UserDto userDto){
        return this.authenticationApplicationService.updateUser(userDto);
    }

    @PostMapping("/users/me")
    public String authenticateUser(@RequestBody()AuthRequest authRequest){
        return this.authenticationApplicationService.authenticateUser(authRequest.getEmail(), authRequest.getPassword());
    }

    @PostMapping("/valid")
    public Boolean validateToken(@RequestBody String token){
        return this.authenticationApplicationService.validateToken(token);
    }

}
