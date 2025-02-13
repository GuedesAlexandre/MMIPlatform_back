package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.mmi.MMIPlatform.infrastructure.dao.enums.SignatureStatusEnum;

@Builder
@Getter
@Setter
public class SignatureDto {
    @JsonProperty("sign")
    private SignatureStatusEnum sign;

    @JsonProperty("studentWhoSign")
    private StudentDto studentDto;
}
