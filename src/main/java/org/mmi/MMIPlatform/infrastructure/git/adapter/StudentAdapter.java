package org.mmi.MMIPlatform.infrastructure.git.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.mmi.MMIPlatform.infrastructure.git.dto.GithubResponseDto;
import org.mmi.MMIPlatform.infrastructure.git.dto.StudentDto;
import org.mmi.MMIPlatform.infrastructure.mapper.StudentDaoMapper;
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
public class StudentAdapter {

    private static final String STUDENT_JSON_PATH = "https://api.github.com/repos/GuedesAlexandre/MMIPlatform_referential/contents/student.json";
    @Value("${github.api.acces.token}")
    private String accessToken;
    private final StudentDaoMapper studentDaoMapper;
    private final StudentDaoRepository studentDaoRepository;

    public List<StudentDto> getStudent() {
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
                return objectMapper.<List<StudentDto>>readValue(
                        jsonContent,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, StudentDto.class)
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

    @Scheduled(fixedRate = 60000000, initialDelay = 10000)
    public void mapStudentToSaveInDatabase() {
        List<StudentDto> studentDtoList = getStudent();
        if (studentDtoList != null) {
            studentDtoList.stream()
                    .map(studentDto -> this.studentDaoMapper.studentDtoToStudentDao(studentDto))
                    .forEach(studentDao -> {
                        if (this.studentDaoRepository.findByNumEtu(studentDao.getNumEtu()) == null) {
                            this.studentDaoRepository.save(studentDao);
                            log.info("Student with num_etu {} saved in database", studentDao.getNumEtu());
                        }else{
                            log.info("Student with num_etu {} already exists in database", studentDao.getNumEtu());
                        }
                    });
        }

    }



}
