package org.mmi.MMIPlatform.infrastructure.git.dto;

import lombok.Data;


@Data
public class StudentDto {
    private String lastName;
    private String firstName;
    private String group;
    private String promo;
    private String numEtu;
}
