package org.mmi.MMIPlatform.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.UEDto;
import org.mmi.MMIPlatform.domain.models.UE;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UEDtoMapper {

    private final ModuleDtoMapper moduleDtoMapper;

    public UEDto UEToUEDto(UE ue) {
        return UEDto.builder()
                .name(ue.getName())
                .promo(ue.getPromo())
                .semester(ue.getSemester())
                .sum_note(ue.getSum_note())
                .coeff(ue.getCoeff())
                .modules(this.moduleDtoMapper.moduleListToModuleDtoList(ue.getModules()))
                .build();
    }

    public List<UEDto> ueListToUeDtoList(List<UE> ueList){
        return ueList.stream().map(this::UEToUEDto).toList();
    }

    public UE UEDtoToUE(UEDto ueDto) {
        return UE.builder()
                .name(ueDto.getName())
                .promo(ueDto.getPromo())
                .semester(ueDto.getSemester())
                .sum_note(ueDto.getSum_note())
                .coeff(ueDto.getCoeff())
                .modules(this.moduleDtoMapper.moduleDtoListToModuleList(ueDto.getModules()))
                .build();
    }

    public List<UE> ueDtoListToUeList(List<UEDto> ueDtoList){
        return ueDtoList.stream().map(this::UEDtoToUE).toList();
    }
}
