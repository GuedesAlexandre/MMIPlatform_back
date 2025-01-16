package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Internship {

    private String id;
    private String title;
    private String comment;
    private int weekCount;
    private int years;
    private String type;
    private Student student;
}
