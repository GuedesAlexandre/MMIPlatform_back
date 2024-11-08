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

    public UE UEDtoToUE(UEDto ueDto) {
        return UE.builder()
                .name(ueDto.getName())
                .promo(ueDto.getPromo())
                .semester(ueDto.getSemester())
                .sum_note(ueDto.getSum_note())
                .coeff(ueDto.getCoeff())
                .build();
    }
}
