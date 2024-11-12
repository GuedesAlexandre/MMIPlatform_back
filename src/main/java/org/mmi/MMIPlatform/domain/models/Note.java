package org.mmi.MMIPlatform.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Note {
    private String id;
    private float coeff;
    private String name;
    private float note;
}
