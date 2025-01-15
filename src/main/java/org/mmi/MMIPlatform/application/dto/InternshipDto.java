package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InternshipDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("weekNumber")
    private int weekNumber;
    @JsonProperty("type")
    private String type;
    @JsonProperty("student")
    private StudentDto student;
}
