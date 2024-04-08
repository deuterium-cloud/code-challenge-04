package cloud.deuterium.maxbet.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest()
abstract class BaseIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Container
    final static PostgreSQLContainer<?> POSTGRE_SQL = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("world_of_games-test")
            .withUsername("sa")
            .withPassword("sa");

    @DynamicPropertySource
    private static void registerProperties(DynamicPropertyRegistry registry) {
        POSTGRE_SQL.start();
        registry.add("spring.datasource.url", POSTGRE_SQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL::getPassword);
    }
}
