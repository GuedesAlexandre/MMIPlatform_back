package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ModuleDto {

    @JsonProperty("name")
    private String name;
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("semester")
    private int semester;
    @JsonProperty("coeff")
    private float coeff;
    @JsonProperty("ueName")
    private String ueName;
}
