package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class UserStudent {
    private String id;
    @NonNull
    private String numEtu;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String lastName;
    @NonNull
    private String firstName;
    private String promo;
    private String group;
    private Date createdAt;
}
