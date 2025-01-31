package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignatureDto {
    @JsonProperty("sign")
    private byte @NonNull [] sign;
    @JsonProperty("email")
    @NonNull
    private String email;
    @JsonProperty("UserStudent")
    @NonNull
    private UserStudentDto userStudentDto;
}
