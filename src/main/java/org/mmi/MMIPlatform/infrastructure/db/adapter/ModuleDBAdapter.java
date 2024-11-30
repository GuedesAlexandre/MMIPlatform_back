package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleDBAdapter {

    private final ModuleDaoRepository moduleDaoRepository;

    public List<ModuleDao> getAllModules() {
        return this.moduleDaoRepository.findAll();
    }

    public List<ModuleDao> getModulesBySemester(String semester) {
        return this.moduleDaoRepository.findAll().stream().filter(module -> module.getSemester().equals(semester)).toList();
    }

    public List<ModuleDao> getModulesByPromoAndSemester(String promo, String semester) {
        return this.moduleDaoRepository.findAll().stream().filter(module -> module.getPromo().equals(PromoEnum.valueOf(promo)) && module.getSemester().equals(semester)).toList();
    }

}
