package org.mmi.MMIPlatform.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.InternshipDBAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.InternshipDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternshipDomainService {

    private final InternshipDBAdapter internshipDBAdapter;
    private final InternshipDaoMapper internshipDaoMapper;

    public List<Internship> getInternshipsByPromo(String promo) {
        try {
            List<InternshipDao> internshipsDaoByPromo = this.internshipDBAdapter.getInternshipsByPromo(promo);
            return this.internshipDaoMapper.internshipDaoListToInternshipList(internshipsDaoByPromo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
