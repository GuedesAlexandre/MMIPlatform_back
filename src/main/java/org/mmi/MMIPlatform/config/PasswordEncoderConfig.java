package org.mmi.MMIPlatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class PasswordEncoderConfig
{
    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        int saltLength = 16;
        int hashLength = 32;
        int parallelism = 1;
        int memory = 4096;
        int iterations = 3;

        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
}
