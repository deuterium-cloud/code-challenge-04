package cloud.deuterium.maxbet.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.client")
public class SecurityProperties {
    private String name;
    private String redirectUri;
    private String logoutRedirectUri;
}
