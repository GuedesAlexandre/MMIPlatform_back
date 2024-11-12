package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
    @JsonProperty("numEtu")
    private String numEtu;
    @JsonProperty("modules")
    private List<ModuleDto> modules;
}
