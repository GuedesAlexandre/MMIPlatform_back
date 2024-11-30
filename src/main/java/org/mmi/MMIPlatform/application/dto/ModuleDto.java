package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ModuleDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("semester")
    private String semester;
    @JsonProperty("coeff")
    private float coeff;
    @JsonProperty("ueName")
    private String ueName;
    @JsonProperty("notes")
    private List<NoteDto> notes;
    @JsonProperty("sumNote")
    private Double sumNote;
}