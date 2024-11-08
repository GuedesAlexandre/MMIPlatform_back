package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Module {
    private String id;
    private String name;
    private String promo;
    private int semester;
    private float coeff;
    private String ueName;
}
