package org.mmi.MMIPlatform.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.InternshipDto;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.application.services.InternshipApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internship")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipApplicationService internshipApplicationService;

    @Operation(summary = "get Internships by promo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Internships recover",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))}),
    })
    @GetMapping("/{promo}")
    public ResponseEntity<List<StudentDto>> getInternshipsByPromo(@PathVariable String promo) {
        return ResponseEntity.ok(internshipApplicationService.getInternshipsByPromo(promo));
    }

    @Operation(summary = "Post internship for student", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "student add internship",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternshipDto.class))}),
    })
    @PostMapping("/{num_etu}")
    public ResponseEntity<InternshipDto> postInternshipForAStudent(@PathVariable(name="num_etu") String numEtu, @RequestBody InternshipDto internshipDto) {
        return ResponseEntity.ok(this.internshipApplicationService.postInternshipForAStudent(numEtu, internshipDto));
    }

    @Operation(summary = "Delete internship for student", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "student deleted succesfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternshipDto.class))}),
    })
    @DeleteMapping("/{num_etu}/{years}/{title}")
    public ResponseEntity<String> deleteInternshipByNumEtuYearsAndTitle(@PathVariable(name="num_etu") String numEtu, @PathVariable(name="years") int years, @PathVariable(name="title") String title) {
        return ResponseEntity.ok(this.internshipApplicationService.deleteInternshipByNumEtuYearsAndTitle(numEtu, years, title));
    }

    @Operation(summary = "Update internship for student", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "student updated succesfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternshipDto.class))}),
    })
    @PutMapping("/{num_etu}/{years}/{title}")
    public ResponseEntity<InternshipDto> putInternshipByNumEtuYearsAndTitle(@PathVariable(name="num_etu") String numEtu, @PathVariable(name="years") int years, @PathVariable(name="title") String title, @RequestBody InternshipDto internshipDto) {
        return ResponseEntity.ok(this.internshipApplicationService.putInternshipByNumEtuYearsAndTitle(numEtu, years, title, internshipDto));
    }
}
