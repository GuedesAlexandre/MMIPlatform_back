package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
public class MatrixDto {

    @JsonProperty("promo")
    private String promo;
    @JsonProperty("semester")
    private int semester;
}
