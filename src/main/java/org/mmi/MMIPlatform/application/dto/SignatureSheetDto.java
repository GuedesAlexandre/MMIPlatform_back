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
    @NonNull
    private String promo;
    @JsonProperty("moduleName")
    @NonNull
    private String moduleName;
    @JsonProperty("createdAt")
    @NonNull
    private Date createdAt;
    @JsonProperty("finishAt")
    @NonNull
    private Date finishAt;
    @JsonProperty("Signatures")
    @NonNull
    private List<SignatureDto> signatureDtos;
    @JsonProperty("UserStudents")
    @NonNull
    private List<UserStudentDto> userStudentDtos;
}
