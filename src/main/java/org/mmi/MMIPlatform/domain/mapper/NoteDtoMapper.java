package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.NoteDto;
import org.mmi.MMIPlatform.domain.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteDtoMapper {


    private final ModuleDtoMapper moduleDtoMapper;



    public NoteDto noteToNoteDto(Note note) {
        return NoteDto.builder()
                .coeff(note.getCoeff())
                .name(note.getName())
                .note(note.getNote())
                .status(note.getStatus())
                .module(null == this.moduleDtoMapper.moduleToModuleDto(note.getModule()) ? null : this.moduleDtoMapper.moduleToModuleDto(note.getModule()))
                .build();
    }

    public List<NoteDto> noteListToNoteDtoList(List<Note> noteList){
        return noteList.stream().map(this::noteToNoteDto).toList();
    }

    public Note noteDtoToNote(NoteDto noteDto)  {
        return Note.builder()
                .coeff(noteDto.getCoeff())
                .name(noteDto.getName())
                .note(noteDto.getNote())
                .status(noteDto.getStatus())
                .module( null == this.moduleDtoMapper.moduleDtoToModule(noteDto.getModule()) ? null : this.moduleDtoMapper.moduleDtoToModule(noteDto.getModule()))
                .build();
    }

    public List<Note> noteDtoListToNoteList(List<NoteDto> noteDtoList){
        return noteDtoList.stream().map(this::noteDtoToNote).toList();
    }
}
