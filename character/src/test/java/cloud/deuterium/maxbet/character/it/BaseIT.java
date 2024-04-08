package cloud.deuterium.maxbet.character.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
abstract class BaseIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Container
    static final PostgreSQLContainer<?> POSTGRE_SQL = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("world_of_games-test")
            .withUsername("sa")
            .withPassword("sa");

    @Container
    static final RedisContainer REDIS =
            new RedisContainer(DockerImageName.parse("redis:7.0-alpine"))
                    .withExposedPorts(6378);

    @Container
    static final RabbitMQContainer RABBIT_MQ = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13-alpine"))
            .withExposedPorts(5672, 15672);


    @DynamicPropertySource
    private static void registerProperties(DynamicPropertyRegistry registry) {
        POSTGRE_SQL.start();
        registry.add("spring.datasource.url", POSTGRE_SQL::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL::getPassword);

        REDIS.start();
        registry.add("spring.redis.host", REDIS::getHost);
        registry.add("spring.redis.port", () -> REDIS.getMappedPort(6378).toString());

        RABBIT_MQ.start();
        registry.add("spring.rabbitmq.host", RABBIT_MQ::getHost);
        registry.add("spring.rabbitmq.port", RABBIT_MQ::getAmqpPort);
        registry.add("spring.rabbitmq.username", RABBIT_MQ::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBIT_MQ::getAdminPassword);
    }
}
