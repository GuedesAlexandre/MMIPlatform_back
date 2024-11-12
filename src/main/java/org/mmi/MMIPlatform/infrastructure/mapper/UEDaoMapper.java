package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.UE;
import org.mmi.MMIPlatform.infrastructure.dao.UEDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UEDaoMapper {

    private final ModuleDaoMapper moduleDaoMapper;

    public UE UEDaoToUE(UEDao ueDao) {
        return UE.builder()
                .id(ueDao.getId())
                .name(ueDao.getName().toString())
                .promo(ueDao.getPromo().toString())
                .semester(ueDao.getSemester())
                .sum_note(ueDao.getSum_note())
                .coeff(ueDao.getCoeff())
                .modules(this.moduleDaoMapper.moduleDaoListToModuleList(ueDao.getModule()))
                .build();
    }

    public List<UE> UeDaosListToUeList(List<UEDao> ueDaos){
        return ueDaos.stream().map(this::UEDaoToUE).toList();
    }

    public UEDao UEToUEDao(UE ue) {
        return UEDao.builder()
                .id(ue.getId())
                .name(UEEnum.valueOf(ue.getName()))
                .promo(PromoEnum.valueOf(ue.getPromo()))
                .semester(ue.getSemester())
                .sum_note(ue.getSum_note())
                .coeff(ue.getCoeff())
                .module(this.moduleDaoMapper.moduleListToModuleDaoList(ue.getModules()))
                .build();
    }
    public List<UEDao> UeListtoUeDaoList(List<UE> ueList){
        return ueList.stream().map(this::UEToUEDao).toList();
    }
}
