package org.mmi.MMIPlatform.infrastructure.git.dto;

import lombok.Data;
import org.mmi.MMIPlatform.application.dto.NoteDto;

import java.util.List;


@Data
public class StudentDto {
    private String lastName;
    private String firstName;
    private String group;
    private String promo;
    private String numEtu;
    private List<NoteDto> notes;
}
