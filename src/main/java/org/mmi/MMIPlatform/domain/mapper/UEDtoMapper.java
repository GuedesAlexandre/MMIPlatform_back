package org.mmi.MMIPlatform.domain.mapper;

import org.mmi.MMIPlatform.application.dto.UEDto;
import org.mmi.MMIPlatform.domain.models.UE;
import org.springframework.stereotype.Service;

@Service
public class UEDtoMapper {

    public UEDto UEToUEDto(UE ue) {
        return UEDto.builder()
                .name(ue.getName())
                .promo(ue.getPromo())
                .semester(ue.getSemester())
                .sum_note(ue.getSum_note())
                .coeff(ue.getCoeff())
                .build();
    }
}
