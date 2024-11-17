package org.mmi.MMIPlatform.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.application.services.StudentApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentApplicationService studentApplicationService;

    @Operation(summary = "get Students by promo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "students recover",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))}),
    })
    @GetMapping("/{promo}")
    public ResponseEntity<List<StudentDto>> getStudentsByPromo(@PathVariable String promo) {
        return ResponseEntity.ok(studentApplicationService.getStudentsByPromo(promo));
    }

    @Operation(summary = "get all Students", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "students recover",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))}),
    })
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentApplicationService.getAllStudents());
    }
}
