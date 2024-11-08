package org.mmi.MMIPlatform.domain.mapper;

import org.mmi.MMIPlatform.application.dto.ModuleDto;
import org.mmi.MMIPlatform.domain.models.Module;
import org.springframework.stereotype.Service;

@Service
public class ModuleDtoMapper {

    public ModuleDto moduleToModuleDto(Module module) {
        return ModuleDto.builder()
                .name(module.getName())
                .promo(module.getPromo())
                .semester(module.getSemester())
                .coeff(module.getCoeff())
                .ueName(module.getUeName())
                .build();
    }

    public Module moduleDtoToModule(ModuleDto moduleDto) {
        return Module.builder()
                .name(moduleDto.getName())
                .promo(moduleDto.getPromo())
                .semester(moduleDto.getSemester())
                .coeff(moduleDto.getCoeff())
                .ueName(moduleDto.getUeName())
                .build();
    }
}
