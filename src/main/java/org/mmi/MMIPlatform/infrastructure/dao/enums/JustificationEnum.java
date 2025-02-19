package org.mmi.MMIPlatform.infrastructure.dao.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JustificationEnum {
    NOT_JUSTIFIED("NOT_JUSTIFIED"),
    JUSTIFIED("JUSTIFIED"),
    JUSTSIGNED("JUSTSIGNED");


    private final String JustificationEnum;
}
