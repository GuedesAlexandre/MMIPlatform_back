package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InternshipDto {
    @JsonProperty("title")
    private String title;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("weekNumber")
    private int weekNumber;
    @JsonProperty("years")
    private int years;
    @JsonProperty("type")
    private String type;
}
