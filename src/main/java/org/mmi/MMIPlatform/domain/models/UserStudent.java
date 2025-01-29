package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class UserStudent {
    private String id;
    private String numEtu;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private Date createdAt;
}
