package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class StudentDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("group")
    private String group;
    @JsonProperty("numEtu")
    private String numEtu;
    @JsonProperty("notes")
    private List<NoteDto> notes;
}