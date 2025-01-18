package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InternshipDto {
    @JsonProperty("title")
    @NonNull
    private String title;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("weekCount")
    private int weekCount;
    @JsonProperty("years")
    private int years;
    @JsonProperty("type")
    @NonNull
    private String type;
}
