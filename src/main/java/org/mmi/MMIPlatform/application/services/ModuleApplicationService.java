package org.mmi.MMIPlatform.application.services;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.application.dto.ModuleDto;
import org.mmi.MMIPlatform.application.dto.StudentDto;
import org.mmi.MMIPlatform.domain.mapper.ModuleDtoMapper;
import org.mmi.MMIPlatform.domain.services.ModuleDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleApplicationService {

    private final ModuleDomainService moduleDomainService;
    private final ModuleDtoMapper moduleDtoMapper;

    @Operation(summary = "get all modules", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "modules recover",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModuleDto.class))}),
    })
    public List<ModuleDto> getAllModules() {
        try {
            return moduleDtoMapper.moduleListToModuleDtoList(this.moduleDomainService.getAllModules());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Operation(summary = "get modules by user", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "modules recover",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ModuleDto.class))}),
    })
    public List<ModuleDto> getModulesByUser(String email) {
        try {
            return moduleDtoMapper.moduleListToModuleDtoList(this.moduleDomainService.getModulesByUser(email));
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
