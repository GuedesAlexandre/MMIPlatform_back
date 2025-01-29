package org.mmi.MMIPlatform.infrastructure.xls.adapter.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StudentsRank {
    private String numEtu;
    private Double average;
}