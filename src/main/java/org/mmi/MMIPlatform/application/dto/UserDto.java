package org.mmi.MMIPlatform.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.mmi.MMIPlatform.domain.models.Module;

import java.util.List;

@Builder
@Getter
@Setter
public class UserDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("establishment")
    private String establishment;
    @JsonProperty("access")
    private String access;
    @JsonProperty("modules")
    private List<ModuleDto> modules;
}
