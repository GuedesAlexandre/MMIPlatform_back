package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Internship {

    private String id;
    @NonNull
    private String title;
    private String comment;
    private int weekCount;
    private int years;
    @NonNull
    private String type;
    private Student student;
}
