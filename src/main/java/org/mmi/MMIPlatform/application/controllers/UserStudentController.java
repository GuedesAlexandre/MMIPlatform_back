package org.mmi.MMIPlatform.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.controllers.models.AuthRequest;
import org.mmi.MMIPlatform.application.dto.UserDto;
import org.mmi.MMIPlatform.application.dto.UserStudentDto;
import org.mmi.MMIPlatform.application.services.UserStudentApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/student")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserStudentController {

    private final UserStudentApplicationService userStudentApplicationService;

    @Operation(summary = "Create a User Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Student created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserStudentDto.class))}),
    })
    @PostMapping
    public ResponseEntity<UserStudentDto> createUserStudent(@RequestBody UserStudentDto userStudentDto) {
        return ResponseEntity.ok(this.userStudentApplicationService.postUserStudent(userStudentDto));
    }

    @Operation(summary = "User Student authentication", security = @SecurityRequirement(name = ""))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Student token")
    })
    @PostMapping("/users/me")
    public ResponseEntity<String> authenticateUserStudent(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(this.userStudentApplicationService.authenticateStudentUser(authRequest.getEmail(), authRequest.getPassword()));
    }
}
