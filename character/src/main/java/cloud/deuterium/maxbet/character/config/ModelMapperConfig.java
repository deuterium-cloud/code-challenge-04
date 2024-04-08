package cloud.deuterium.maxbet.character.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Milan Stojkovic 01-Feb-2024
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper objectMapper() {
        return new ModelMapper();
    }
}
