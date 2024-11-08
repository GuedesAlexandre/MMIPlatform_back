package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Matrix {
    private String id;
    private String promo;
    private int semester;
}
