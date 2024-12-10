package org.mmi.MMIPlatform.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.controllers.models.AuthRequest;
import org.mmi.MMIPlatform.application.dto.UserDto;
import org.mmi.MMIPlatform.application.services.AuthenticationApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @Operation(summary = "Create a User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
    })
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(this.authenticationApplicationService.initUser(userDto));
    }

    @Operation(summary = "Update a User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
    })
    @PutMapping
    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
        return ResponseEntity.ok(this.authenticationApplicationService.updateUser(userDto));
    }

    @Operation(summary = "Delete a User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted")
    })
    @DeleteMapping("/user/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable(name="email") String email) {
        return ResponseEntity.ok(this.authenticationApplicationService.deleteUser(email));
    }

    @Operation(summary = "User authentication", security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User token")
    })
    @PostMapping("/users/me")
    public ResponseEntity<String> authenticateUser(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(this.authenticationApplicationService.authenticateUser(authRequest.getEmail(), authRequest.getPassword()));
    }

    @Operation(summary = "Token validation", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authorization")
    })
    @PostMapping("/valid")
    public ResponseEntity<HttpStatus> validateToken(@Parameter(description = "user token") @RequestBody String token) {
        if (this.authenticationApplicationService.validateToken(token)) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @Operation(summary = "Token validation", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all uses",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
    })
    @GetMapping("/user/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.authenticationApplicationService.getAllUsers());
    }

}
