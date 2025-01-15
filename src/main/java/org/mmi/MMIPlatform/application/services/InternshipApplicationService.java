package org.mmi.MMIPlatform.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.application.dto.InternshipDto;
import org.mmi.MMIPlatform.domain.mapper.InternshipDtoMapper;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.domain.services.InternshipDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternshipApplicationService {

    private final InternshipDomainService internshipDomainService;
    private final InternshipDtoMapper internshipDtoMapper;

    public List<InternshipDto> getInternshipsByPromo(String promo) {
        try {
            List<Internship> internshipsByPromo = this.internshipDomainService.getInternshipsByPromo(promo);
            return internshipDtoMapper.internshipsToInternshipDto(internshipsByPromo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
