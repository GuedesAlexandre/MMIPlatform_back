package org.mmi.MMIPlatform.infrastructure.git.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;

@Data
@Getter
@Setter
@Builder
public class StudentDto {
    String lastName;
    String firstName;
    PromoEnum promo;
    String group;
    String num_etu;
}
