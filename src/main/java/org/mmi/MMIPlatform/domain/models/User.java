package org.mmi.MMIPlatform.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class User {
    private String id;
    private String email;
    private String password;
    private String username;
    private String name;
    private String firstName;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String establishment;
    private String access;
    private List<Module> modules;
}