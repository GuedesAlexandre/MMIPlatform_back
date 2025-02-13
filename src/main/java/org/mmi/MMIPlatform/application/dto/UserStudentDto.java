package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class UserStudentDto {
    @JsonProperty("numEtu")
    @NonNull
    private String numEtu;
    @JsonProperty("email")
    @NonNull
    private String email;
    @JsonProperty("password")
    @NonNull
    private String password;
    @JsonProperty("lastName")
    @NonNull
    private String lastName;
    @JsonProperty("firstName")
    @NonNull
    private String firstName;
    @JsonProperty("promo")
    private String promo;
    @JsonProperty("group")
    private String group;
    @JsonProperty("createdAt")
    private Date createdAt;
}
