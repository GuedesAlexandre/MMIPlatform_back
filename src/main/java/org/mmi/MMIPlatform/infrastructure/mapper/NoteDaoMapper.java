package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Note;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteDaoMapper {

    private final ModuleDaoMapper moduleDaoMapper;


    public Note noteDaoToNote(NoteDao noteDao) {
        return Note.builder()
                .id(noteDao.getId())
                .coeff(noteDao.getNote())
                .name(noteDao.getName())
                .note(noteDao.getNote())
                .module(moduleDaoMapper.moduleDaoToModule(noteDao.getModule()))
                .build();
    }

    public List<Note> noteDaoListToNoteList(List<NoteDao> noteDaoList){
        return noteDaoList.stream().map(this::noteDaoToNote).toList();
    }

    public NoteDao noteToNoteDao(Note note) {
        return NoteDao.builder()
                .id(note.getId())
                .coeff(note.getCoeff())
                .name(note.getName())
                .note(note.getNote())
                .module(moduleDaoMapper.moduleToModuleDao(note.getModule()))
                .build();
    }

    public List<NoteDao> noteListToNoteDaoList(List<Note> noteList){
        return noteList.stream().map(this::noteToNoteDao).toList();
    }
}
