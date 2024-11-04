package org.mmi.MMIPlatform.infrastructure.dao.enums;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PermissionsEnum {
    ADMIN("ADMIN"),
    SCOLARITY("SCOLARITY"),
    TEACHER("TEACHER"),
    SUPPORT("SUPPORT");

    private final String PermissionsEnum;
}
