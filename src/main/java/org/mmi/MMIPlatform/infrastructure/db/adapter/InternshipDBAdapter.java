package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.InternshipDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternshipDBAdapter {

    private final InternshipDaoRepository internshipDaoRepository;
    private final StudentDaoRepository studentDaoRepository;

    public List<InternshipDao> getInternshipsByPromo(String promo) {
        return this.internshipDaoRepository.findAll().stream().filter(internship -> internship.getStudent().getPromo().equals(PromoEnum.valueOf(promo))).toList();
    }


}
