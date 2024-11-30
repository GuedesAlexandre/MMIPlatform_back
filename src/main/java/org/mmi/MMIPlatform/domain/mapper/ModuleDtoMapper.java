
package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.ModuleDto;
import org.mmi.MMIPlatform.domain.models.Module;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleDtoMapper {

    public ModuleDto moduleToModuleDto(Module module) {
        return ModuleDto.builder()
                .name(module.getName())
                .promo(module.getPromo())
                .semester(module.getSemester())
                .coeff(module.getCoeff())
                .ueName(module.getUeName())
                .sumNote(module.getSumNote())
                .build();
    }

    public List<ModuleDto> moduleListToModuleDtoList(List<Module> moduleList) {
        return moduleList.stream().map(this::moduleToModuleDto).toList();
    }

    public Module moduleDtoToModule(ModuleDto moduleDto) {
        if(moduleDto == null) {
            return null;
        }
        return Module.builder()
                .name(null == moduleDto.getName() ? "" : moduleDto.getName())
                .promo(moduleDto.getPromo())
                .semester(moduleDto.getSemester())
                .coeff(moduleDto.getCoeff())
                .ueName(moduleDto.getUeName())
                .sumNote(0.0)
                .build();
    }

    public List<Module> moduleDtoListToModuleList(List<ModuleDto> moduleDtos) {
        if (moduleDtos == null || moduleDtos.isEmpty()) {
            return List.of();
        }
        return moduleDtos.stream().map(this::moduleDtoToModule).toList();
    }
}