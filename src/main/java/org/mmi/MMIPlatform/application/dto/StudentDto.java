package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
public class StudentDto {

    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("group")
    private String group;
    @JsonProperty("num_etu")
    private String num_etu;
}
