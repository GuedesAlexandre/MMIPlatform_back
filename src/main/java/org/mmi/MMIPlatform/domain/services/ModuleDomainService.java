package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Module;
import org.mmi.MMIPlatform.infrastructure.db.adapter.ModuleDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.ModuleDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleDomainService {
    private final ModuleDaoMapper moduleDaoMapper;
    private final ModuleDBAdapter moduleDBAdapter;

    public List<Module> getAllModules() {
        return this.moduleDaoMapper.moduleDaoListToModuleList(this.moduleDBAdapter.getAllModules());
    }

}
