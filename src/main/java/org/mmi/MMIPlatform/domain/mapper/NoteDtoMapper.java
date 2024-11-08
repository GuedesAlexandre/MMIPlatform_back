package org.mmi.MMIPlatform.domain.mapper;

import org.mmi.MMIPlatform.application.dto.NoteDto;
import org.mmi.MMIPlatform.domain.models.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteDtoMapper {

    public NoteDto noteToNoteDto(Note note) {
        return NoteDto.builder()
                .coeff(note.getCoeff())
                .name(note.getName())
                .note(note.getNote())
                .build();
    }

    public Note noteDtoToNote(NoteDto noteDto)  {
        return Note.builder()
                .coeff(noteDto.getCoeff())
                .name(noteDto.getName())
                .note(noteDto.getNote())
                .build();
    }
}
