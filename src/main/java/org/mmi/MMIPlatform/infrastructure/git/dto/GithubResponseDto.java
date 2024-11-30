package org.mmi.MMIPlatform.infrastructure.git.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GithubResponseDto {
    private String content;
}