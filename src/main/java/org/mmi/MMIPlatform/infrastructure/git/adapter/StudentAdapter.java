package org.mmi.MMIPlatform.infrastructure.git.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.git.dto.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentAdapter {

    @Value("${github.api.acces.token}")
    private String accessToken;


    @Scheduled(fixedRate = 60000)
    public void getStudent() {

       WebClient webClient = WebClient.builder()
                .baseUrl("https://api.github.com/")
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();

       StudentDto studentDto =  webClient.get()
                .uri("/GuedesAlexandre/MMIPlatform_referential/contents/Student.json")
                .retrieve()
                .bodyToMono(StudentDto.class)
                .block();

         log.info("StudentDto: {}", studentDto);

    }



}
