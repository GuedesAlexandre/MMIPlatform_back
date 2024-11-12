package org.mmi.MMIPlatform.infrastructure.git.dto;

import lombok.*;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;

@Data
public class ModuleDto {
    String name;
    PromoEnum promo;
    String semester;
    Float coeff;
    UEEnum ueName;
}
