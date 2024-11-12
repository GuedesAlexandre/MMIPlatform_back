package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Module;
import org.mmi.MMIPlatform.domain.models.Note;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.mmi.MMIPlatform.infrastructure.git.dto.ModuleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleDaoMapper {

    private final NoteDaoMapper noteDaoMapper;

    public Module moduleDaoToModule(ModuleDao moduleDao) {
        return Module.builder()
                .id(moduleDao.getId())
                .name(moduleDao.getName())
                .promo(moduleDao.getPromo().toString())
                .semester(moduleDao.getSemester())
                .coeff(moduleDao.getCoeff())
                .ueName(moduleDao.getUeName().toString())
                .notes(this.noteDaoMapper.noteDaoListToNoteList(moduleDao.getNotes()))
                .sum(this.calculateAverageNoteFromListDao(moduleDao.getNotes()))
                .build();
    }

    public List<Module> moduleDaoListToModuleList(List<ModuleDao> moduleDaos){
        return moduleDaos.stream().map(this::moduleDaoToModule).toList();
    }

    public ModuleDao moduleDtoToModuleDao(ModuleDto moduleDto) {
        return ModuleDao.builder()
                .name(moduleDto.getName())
                .promo(moduleDto.getPromo())
                .semester(moduleDto.getSemester())
                .coeff(moduleDto.getCoeff())
                .ueName(moduleDto.getUeName())
                .build();
    }

    public ModuleDao moduleToModuleDao(Module module) {
        return ModuleDao.builder()
                .id(module.getId())
                .name(module.getName())
                .promo(PromoEnum.valueOf(module.getPromo()))
                .semester(module.getSemester())
                .coeff(module.getCoeff())
                .ueName(UEEnum.valueOf(module.getUeName()))
                .notes(this.noteDaoMapper.noteListToNoteDaoList(module.getNotes()))
                .sumNote(this.calculteAverageNoteFromList(module.getNotes()))
                .build();
    }

    public List<ModuleDao> moduleListToModuleDaoList(List<Module> moduleList){
        return moduleList.stream().map(this::moduleToModuleDao).toList();
    }


    public double calculateAverageNoteFromListDao(List<NoteDao> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0.0;
        }
        return notes.stream()
                .mapToDouble(NoteDao::getNote)
                .average()
                .orElse(0.0);
    }

    public double calculteAverageNoteFromList(List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            return 0.0;
        }
        return notes.stream()
                .mapToDouble(Note::getNote)
                .average()
                .orElse(0.0);
    }





}
