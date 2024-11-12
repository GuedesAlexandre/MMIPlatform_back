package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Module {
    private String id;
    private String name;
    private String promo;
    private String semester;
    private float coeff;
    private String ueName;
    private List<Note> notes;
    private Double sum;
}
