package cloud.deuterium.maxbet.combat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Created by Milan Stojkovic 06-Apr-2024
 */

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${app.character-api.url}") String url) {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}
