package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Matrix {
    private String id;
    private String promo;
    private int semester;
    private User user;
    private List<Student> students;
    private List<UE> ueList;
}
