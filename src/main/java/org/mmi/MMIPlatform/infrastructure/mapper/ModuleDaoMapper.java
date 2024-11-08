package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Module;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleDaoMapper {

    public Module moduleDaoToModule(ModuleDao moduleDao) {
        return Module.builder()
                .id(moduleDao.getId())
                .name(moduleDao.getName())
                .promo(moduleDao.getPromo().toString())
                .semester(moduleDao.getSemester())
                .coeff(moduleDao.getCoeff())
                .ueName(moduleDao.getUeName().toString())
                .build();
    }

    public ModuleDao moduleToModuleDao(Module module) {
        return ModuleDao.builder()
                .id(module.getId())
                .name(module.getName())
                .promo(PromoEnum.valueOf(module.getPromo()))
                .semester(module.getSemester())
                .coeff(module.getCoeff())
                .ueName(UEEnum.valueOf(module.getUeName()))
                .build();
    }
}
