package org.mmi.MMIPlatform.infrastructure.git.adapter;

import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.mmi.MMIPlatform.infrastructure.git.dto.GithubResponseDto;
import org.mmi.MMIPlatform.infrastructure.git.dto.ModuleDto;
import org.mmi.MMIPlatform.infrastructure.git.dto.StudentDto;
import org.mmi.MMIPlatform.infrastructure.mapper.ModuleDaoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ModuleAdapter {
    private static final String STUDENT_JSON_PATH = "https://api.github.com/repos/GuedesAlexandre/MMIPlatform_referential/contents/modules.json";
    @Value("${github.api.acces.token}")
    private String accessToken;
    private final ModuleDaoMapper moduleDaoMapper;
    private final ModuleDaoRepository moduleDaoRepository;
    private final StudentDaoRepository studentDaoRepository;

    public List<ModuleDto> getModule() {
        log.info("Scheduled task started");
        WebClient webClient = WebClient.builder()
                .baseUrl(STUDENT_JSON_PATH)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();

        try {
            GithubResponseDto gitHubResponse = webClient.get()
                    .retrieve()
                    .bodyToMono(GithubResponseDto.class)
                    .block();

            if (gitHubResponse != null && gitHubResponse.getContent() != null) {
                String cleanedBase64 = gitHubResponse.getContent().replaceAll("\\s+", "");
                byte[] decodedBytes = Base64.getDecoder().decode(cleanedBase64);
                String jsonContent = new String(decodedBytes, StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.<List<ModuleDto>>readValue(
                        jsonContent,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ModuleDto.class)
                );
            } else {
                log.warn("No content found in GitHub response.");
            }
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getLocalizedMessage());
        }
        log.info("Scheduled task ended");
        return null;
    }


    @Scheduled(fixedRate = 60000000, initialDelay = 30000)
    @Transactional
    public void mapModuleToSaveInDatabase() {
        List<ModuleDto> moduleDtoList = getModule();
        if (moduleDtoList != null) {
            moduleDtoList.stream()
                    .map(this.moduleDaoMapper::moduleDtoToModuleDao)
                    .forEach(moduleDao -> {
                        this.moduleDaoRepository.save(moduleDao);
                        log.info("Module with name {} saved in database", moduleDao.getName());

                        switch (moduleDao.getPromo()) {
                            case MMI01:
                                this.studentDaoRepository.findAll().stream()
                                        .filter(student -> student.getPromo().equals(PromoEnum.MMI01))
                                        .forEach(student -> addModuleToStudent(student, moduleDao));
                                break;
                            case MMI02:
                                this.studentDaoRepository.findAll().stream()
                                        .filter(student -> student.getPromo().equals(PromoEnum.MMI02))
                                        .forEach(student -> addModuleToStudent(student, moduleDao));
                                break;
                            case MMI03:
                                this.studentDaoRepository.findAll().stream()
                                        .filter(student -> student.getPromo().equals(PromoEnum.MMI03))
                                        .forEach(student -> addModuleToStudent(student, moduleDao));
                                break;
                            default:
                                log.warn("Unknown promo: {}", moduleDao.getPromo());
                                break;
                        }
                    });
        }
    }

    private void addModuleToStudent(StudentDao student, ModuleDao module) {
        if (!student.getModules().contains(module)) {
            student.getModules().add(module);
            this.studentDaoRepository.save(student);
            log.info("Module with name {} added to student with num_etu {}", module.getName(), student.getNumEtu());
        } else {
            log.info("Module with name {} already exists for student with num_etu {}", module.getName(), student.getNumEtu());
        }
    }


}
