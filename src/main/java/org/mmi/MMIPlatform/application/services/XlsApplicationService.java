package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.services.XlsDomainService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class XlsApplicationService {

    private final XlsDomainService xlsDomainService;

    public byte[] exportMatrixByPromo(String promo, String semester) {
        try {
            return xlsDomainService.exportMatrixByPromo(promo, semester);
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public byte[] exportModuleGradesToExcel(String promo, String semester,  String ueName, String moduleName) {
        try {
            return xlsDomainService.exportModuleGradesToExcel(promo, semester,  ueName, moduleName);
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

}
