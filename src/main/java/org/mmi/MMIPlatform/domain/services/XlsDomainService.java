package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.xls.adapter.XlsAdapter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class XlsDomainService {

    private final XlsAdapter xlsAdapter;

    public byte[] exportMatrixByPromo(String promo, String semester) {
        try {
        return xlsAdapter.exportStudentToExcel(promo, semester);
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }


}
