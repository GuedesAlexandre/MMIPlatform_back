package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UE {
    private String id;
    private String name;
    private String promo;
    private int semester;
    private double sum_note;
    private float coeff;
    private List<Module> modules;
}
