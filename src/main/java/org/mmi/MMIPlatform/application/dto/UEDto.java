package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
public class UEDto {

    @JsonProperty("name")
    private String name;
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("semester")
    private int semester;
    @JsonProperty("sum_note")
    private double sum_note;
    @JsonProperty("coeff")
    private double coeff;


}
