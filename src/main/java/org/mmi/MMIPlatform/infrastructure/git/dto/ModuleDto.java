package org.mmi.MMIPlatform.infrastructure.git.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;

@Data
@Getter
@Setter
@Builder
public class ModuleDto {
    String name;
    PromoEnum promo;
    String semester;
    Float coeff;
    UEEnum ueName;
}
