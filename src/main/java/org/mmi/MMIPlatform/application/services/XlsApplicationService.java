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

    public byte[] exportMatrixByPromo(String promo) {
        try {
            return xlsDomainService.exportMatrixByPromo(promo);
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

}
