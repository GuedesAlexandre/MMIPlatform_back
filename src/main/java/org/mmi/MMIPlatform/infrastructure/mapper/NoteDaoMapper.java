package org.mmi.MMIPlatform.infrastructure.mapper;

import org.mmi.MMIPlatform.domain.models.Note;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;

public class NoteDaoMapper {

    public Note noteDaoToNote(NoteDao noteDao) {
        return Note.builder()
                .id(noteDao.getId())
                .coeff(noteDao.getNote())
                .name(noteDao.getName())
                .note(noteDao.getNote())
                .build();
    }

    public NoteDao noteToNoteDao(Note note) {
        return NoteDao.builder()
                .id(note.getId())
                .coeff(note.getCoeff())
                .name(note.getName())
                .note(note.getNote())
                .build();
    }
}
