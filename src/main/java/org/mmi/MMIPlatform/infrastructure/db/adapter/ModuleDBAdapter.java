package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.UserDaoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleDBAdapter {

    private final ModuleDaoRepository moduleDaoRepository;
    private final UserDaoRepository userDaoRepository;

    public List<ModuleDao> getAllModules() {
        try {
            return this.moduleDaoRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<ModuleDao> getModulesBySemester(String semester) {
        try {
            return this.moduleDaoRepository.findAll().stream()
                    .filter(module -> module.getSemester().equals(semester))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<ModuleDao> getModulesByPromoAndSemester(String promo, String semester) {
        try {
            return this.moduleDaoRepository.findAll().stream()
                    .filter(module -> module.getPromo().equals(String.valueOf(promo)) && module.getSemester().equals(semester))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<ModuleDao> getModulesForOneUser(String email) {
        try {
            if(this.userDaoRepository.findByEmail(email) == null) {
                return Collections.emptyList();
            }
            List<ModuleDao> modules = this.userDaoRepository.findByEmail(email).getModuleDaos();
            return modules.isEmpty() ? Collections.emptyList() : modules;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}