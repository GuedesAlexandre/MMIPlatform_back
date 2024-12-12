package org.mmi.MMIPlatform.application.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.ModuleDto;
import org.mmi.MMIPlatform.application.services.ModuleApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/module")
public class ModuleController {

    private final ModuleApplicationService moduleApplicationService;


    @Operation(summary = "get all modules", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "modules recover",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModuleDto.class))}),
    })
    @GetMapping("/all")
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        return ResponseEntity.ok(moduleApplicationService.getAllModules());
    }


    @Operation(summary = "get modules by user", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "modules recover by user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModuleDto.class))}),
    })
    @GetMapping("/user/{email}")
    public ResponseEntity<List<ModuleDto>> getModulesByUser(@PathVariable String email) {
        return ResponseEntity.ok(moduleApplicationService.getModulesByUser(email));
    }


}
