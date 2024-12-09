package org.mmi.MMIPlatform.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.NoteDto;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.application.services.StudentApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @Operation(summary = "Post note for student", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "student add notes",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoteDto.class))}),
    })
    @PostMapping("/notes/{num_etu}/{module}")
    public ResponseEntity<String> postNotesForAStudent(@PathVariable(name="num_etu") String numEtu, @RequestBody NoteDto noteDto, @PathVariable(name = "module") String module) {
        return ResponseEntity.ok(this.studentApplicationService.postNotesForAStudent(numEtu , URLDecoder.decode(module, StandardCharsets.UTF_8), noteDto));
    }



    @Operation(summary = "Put note for student", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "student modify notes",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoteDto.class))}),
    })
    @PutMapping("/notes/{num_etu}/{module}/{name}")
    public ResponseEntity<String> putNotesForAStudent(@PathVariable(name="num_etu") String numEtu, @PathVariable(name = "module") String module, @PathVariable(name = "name") String name, @RequestBody NoteDto noteDto) {
        return ResponseEntity.ok(this.studentApplicationService.putNotesForAStudent(numEtu , URLDecoder.decode(module, StandardCharsets.UTF_8), name, noteDto));
    }
}
