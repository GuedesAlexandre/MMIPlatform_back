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
                            schema = @Schema(implementation = InternshipDto.class))}),
    })
    @GetMapping("/{promo}")
    public ResponseEntity<List<InternshipDto>> getInternshipsByPromo(@PathVariable String promo) {
        return ResponseEntity.ok(internshipApplicationService.getInternshipsByPromo(promo));
    }
}
