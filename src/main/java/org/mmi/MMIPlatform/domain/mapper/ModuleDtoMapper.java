package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.ModuleDto;
import org.mmi.MMIPlatform.application.dto.NoteDto;
import org.mmi.MMIPlatform.domain.models.Module;
import org.mmi.MMIPlatform.domain.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleDtoMapper {

    private final NoteDtoMapper noteDtoMapper;

    public ModuleDto moduleToModuleDto(Module module) {
        return ModuleDto.builder()
                .name(module.getName())
                .promo(module.getPromo())
                .semester(module.getSemester())
                .coeff(module.getCoeff())
                .ueName(module.getUeName())
                .sum(module.getSum())
                .noteDtos(this.noteDtoMapper.noteListToNoteDtoList(module.getNotes()))
                .build();
    }

    public List<ModuleDto> moduleListToModuleDtoList(List<Module> moduleList){
        return moduleList.stream().map(this::moduleToModuleDto).toList();
    }

    public Module moduleDtoToModule(ModuleDto moduleDto) {
        return Module.builder()
                .name(moduleDto.getName())
                .promo(moduleDto.getPromo())
                .semester(moduleDto.getSemester())
                .coeff(moduleDto.getCoeff())
                .ueName(moduleDto.getUeName())
                .sum(this.calculteAverageNoteFromList(moduleDto.getNoteDtos()))
                .notes(this.noteDtoMapper.noteDtoListToNoteList(moduleDto.getNoteDtos()))
                .build();
    }

    public List<Module> moduleDtoListToModuleList(List<ModuleDto> moduleDtos){
        if(moduleDtos == null || moduleDtos.isEmpty()){
            return List.of();
        }
        return moduleDtos.stream().map(this::moduleDtoToModule).toList();
    }

    public double calculteAverageNoteFromList(List<NoteDto> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0.0;
        }
        return notes.stream()
                .mapToDouble(NoteDto::getNote)
                .average()
                .orElse(0.0);
    }
}
