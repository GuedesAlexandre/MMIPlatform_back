package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.UE;
import org.mmi.MMIPlatform.infrastructure.dao.UEDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UEDaoMapper {

    public UE UEDaoToUE(UEDao ueDao) {
        return UE.builder()
                .id(ueDao.getId())
                .name(ueDao.getName().toString())
                .promo(ueDao.getPromo().toString())
                .semester(ueDao.getSemester())
                .sum_note(ueDao.getSum_note())
                .coeff(ueDao.getCoeff())
                .build();
    }

    public UEDao UEToUEDao(UE ue) {
        return UEDao.builder()
                .id(ue.getId())
                .name(UEEnum.valueOf(ue.getName()))
                .promo(PromoEnum.valueOf(ue.getPromo()))
                .semester(ue.getSemester())
                .sum_note(ue.getSum_note())
                .coeff(ue.getCoeff())
                .build();
    }
}
