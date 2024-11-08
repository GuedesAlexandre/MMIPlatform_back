package org.mmi.MMIPlatform.domain.models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Student  {
    private String id;
    private String lastName;
    private String firstName;
    private String promo;
    private String group;
    private String num_etu;
}
