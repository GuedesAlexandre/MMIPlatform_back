package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.mmi.MMIPlatform.domain.models.UE;
import org.mmi.MMIPlatform.domain.models.User;

import java.util.List;

@Builder
@Getter
@Setter
public class MatrixDto {

    @JsonProperty("promo")
    private String promo;
    @JsonProperty("semester")
    private int semester;
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("students")
    private List<StudentDto> students;
    @JsonProperty("ue")
    private List<UEDto> ueDtoList;
}
