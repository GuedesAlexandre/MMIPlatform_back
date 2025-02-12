package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class SignatureSheetDto {
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("moduleName")
    private String moduleName;
    @JsonProperty("createdAt")
    private Date createdAt;
    @JsonProperty("finishAt")
    private Date finishAt;
    @JsonProperty("signatures")
    private List<SignatureDto> signatureDtos;
    @JsonProperty("students")
    private List<StudentDto> studentDtos;
}
