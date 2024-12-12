package org.mmi.MMIPlatform.infrastructure.dao.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {

    DONE("DONE"),
    ABS("ABS"),
    DEF("DEF"),
    RATTRAPAGE("RATTRAPAGE");

    private final String StatusEnum;
}
