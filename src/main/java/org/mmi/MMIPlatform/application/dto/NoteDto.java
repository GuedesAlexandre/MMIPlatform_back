package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NoteDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("coeff")
    private float coeff;
    @JsonProperty("name")
    private String name;
    @JsonProperty("note")
    private float note;
    @JsonProperty("status")
    private String status;
    @JsonProperty("module")
    private ModuleDto module;
    @JsonProperty("student")
    private StudentDto student;
}