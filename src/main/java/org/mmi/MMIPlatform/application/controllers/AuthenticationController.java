package org.mmi.MMIPlatform.application.controllers;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.controllers.models.AuthRequest;
import org.mmi.MMIPlatform.application.dto.UserDto;
import org.mmi.MMIPlatform.application.services.AuthenticationApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class  AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(this.authenticationApplicationService.initUser(userDto));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(UserDto userDto){
        return ResponseEntity.ok(this.authenticationApplicationService.updateUser(userDto));
    }

    @PostMapping("/users/me")
    public ResponseEntity<String> authenticateUser(@RequestBody()AuthRequest authRequest){
        return ResponseEntity.ok(this.authenticationApplicationService.authenticateUser(authRequest.getEmail(), authRequest.getPassword()));
    }

    @PostMapping("/valid")
    public ResponseEntity<HttpStatus> validateToken(@RequestBody String token){
        if(this.authenticationApplicationService.validateToken(token)){
            return ResponseEntity.ok(HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
