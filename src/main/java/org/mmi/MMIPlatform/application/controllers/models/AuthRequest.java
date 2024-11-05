package org.mmi.MMIPlatform.application.controllers.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
}
