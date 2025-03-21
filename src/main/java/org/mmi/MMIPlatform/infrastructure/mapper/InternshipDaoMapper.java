package org.mmi.MMIPlatform.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.TypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipDaoMapper {

    public Internship internshipDaoToInternShip(InternshipDao internshipDao) {
        if(internshipDao == null) {
            throw new NullPointerException("internship required");
        }
        return Internship.builder()
                .id(internshipDao.getId())
                .title(internshipDao.getTitle())
                .comment(internshipDao.getComment())
                .weekCount(internshipDao.getWeekCount())
                .type(String.valueOf(internshipDao.getType()))
                .years(internshipDao.getYears())
                .build();
    }

    public List<Internship> internshipDaoListToInternshipList(List<InternshipDao> internshipDaoList) {
        return internshipDaoList.stream().map(this::internshipDaoToInternShip).toList();
    }

    public InternshipDao internshipToInternshipDao(Internship internship) {
        if(internship == null) {
            throw new NullPointerException("internship required");
        }
        return InternshipDao.builder()
                .id(internship.getId())
                .title(internship.getTitle())
                .comment(internship.getComment())
                .weekCount(internship.getWeekCount())
                .type(TypeEnum.valueOf(internship.getType()))
                .years(internship.getYears())
                .build();
    }

    public List<InternshipDao> internshipListToInternshipDaoList(List<Internship> internshipList) {
        return internshipList.stream().map(this::internshipToInternshipDao).toList();
    }
}
