package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.mmi.MMIPlatform.domain.models.Module;

import java.util.List;

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
    private float coeff;
    @JsonProperty("modules")
    private List<ModuleDto> modules;


}
