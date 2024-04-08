package cloud.deuterium.maxbet.character.it;

import cloud.deuterium.maxbet.character.dtos.response.CharacterDto;
import cloud.deuterium.maxbet.character.entities.WoGCharacter;
import cloud.deuterium.maxbet.character.repositories.CharacterRepository;
import cloud.deuterium.maxbet.character.repositories.ClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static cloud.deuterium.maxbet.character.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */
class CharacterIT extends BaseIT {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ClassRepository classRepository;

    @AfterEach
    void clearDB() {
        characterRepository.deleteAll();
        classRepository.deleteAll();
    }

    @DisplayName("Should return 401")
    @Test
    void get_characters_failed() throws Exception {
        mockMvc.perform(get("/api/characters"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Should return 403 because caller has no GAME_MASTER role")
    @Test
    void get_characters_failed_2() throws Exception {
        mockMvc.perform(get("/characters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_USER_JWT))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Should return characters")
    @Test
    void get_characters_ok() throws Exception {
        mockMvc.perform(get("/characters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_GAME_MASTER_JWT))
                .andExpect(status().isOk());
    }

    @Sql("/sql/import_data.sql")
    @DisplayName("Should return 401")
    @Test
    void get_character_failed() throws Exception {
        mockMvc.perform(get("/characters/1a70816c-1939-4417-97b1-fe349dd7442a"))
                .andExpect(status().isUnauthorized());
    }

    @Sql("/sql/import_data.sql")
    @DisplayName("Should return 403 because caller has USER role and is not resource owner")
    @Test
    void get_character_failed_2() throws Exception {
        mockMvc.perform(get("/characters/1a70816c-1939-4417-97b1-fe349dd7442a")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_USER_2_JWT))
                .andExpect(status().isForbidden());
    }

    @Sql(value = "/sql/import_data.sql")
    @DisplayName("Should return character with given ID because caller is GAME_MASTER")
    @Test
    void get_character_ok() throws Exception {
        mockMvc.perform(get("/characters/1a70816c-1939-4417-97b1-fe349dd7442a")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_GAME_MASTER_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1a70816c-1939-4417-97b1-fe349dd7442a"))
                .andExpect(jsonPath("$.name").value("Marcus"))
                .andExpect(jsonPath("$.createdBy").value("2425512d-ccc9-4c22-b4d1-92b852c87cf6"));
    }

    @Sql("/sql/import_data.sql")
    @DisplayName("Should return character with given ID because caller is resource owner")
    @Test
    void get_character_ok_2() throws Exception {
        mockMvc.perform(get("/characters/1a70816c-1939-4417-97b1-fe349dd7442a")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_USER_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1a70816c-1939-4417-97b1-fe349dd7442a"))
                .andExpect(jsonPath("$.name").value("Marcus"))
                .andExpect(jsonPath("$.createdBy").value("2425512d-ccc9-4c22-b4d1-92b852c87cf6"));
    }

    @DisplayName("Should return 401")
    @Test
    void create_character_failed() throws Exception {
        mockMvc.perform(post("/characters"))
                .andExpect(status().isUnauthorized());
    }

    @Sql("/sql/import_data.sql")
    @DisplayName("Should create new Mage Character")
    @Test
    void create_character_ok() throws Exception {

        String mageJson = """
                {
                    "name": "Ice wizard",
                    "health": 100,
                    "mane": 100,
                    "strength": 100,
                    "agility": 100,
                    "intelligence": 100,
                    "faith": 100,
                    "classId": "dacb4ff0-8d9d-4b8c-a2f5-68ade2341cb0"
                }
                """;

        ResultActions resultActions = mockMvc.perform(post("/characters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_USER_JWT)
                        .content(mageJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.characterClass.id").value("dacb4ff0-8d9d-4b8c-a2f5-68ade2341cb0"))
                .andExpect(jsonPath("$.createdBy").value("2425512d-ccc9-4c22-b4d1-92b852c87cf6"));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        CharacterDto dto = objectMapper.readValue(contentAsString, CharacterDto.class);

        Optional<WoGCharacter> optional = characterRepository.findById(dto.getId());
        assertThat(optional.isPresent()).isTrue();
    }

    @Sql("/sql/import_data.sql")
    @DisplayName("Should return 409 because Character with same name already exists")
    @Test
    void create_character_failed_2() throws Exception {

        String mageJson = """
                {
                    "name": "Marcus",
                    "health": 100,
                    "mane": 100,
                    "strength": 100,
                    "agility": 100,
                    "intelligence": 100,
                    "faith": 100,
                    "classId": "dacb4ff0-8d9d-4b8c-a2f5-68ade2341cb0"
                }
                """;

        mockMvc.perform(post("/characters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_USER_JWT)
                        .content(mageJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(409))
                .andExpect(jsonPath("$.description").value("Character with name: Marcus already exists"));

    }

    @Sql("/sql/import_data.sql")
    @DisplayName("Should return 404 because there is no Class with given id")
    @Test
    void create_character_failed_3() throws Exception {

        String mageJson = """
                {
                    "name": "Ice wizard",
                    "health": 100,
                    "mane": 100,
                    "strength": 100,
                    "agility": 100,
                    "intelligence": 100,
                    "faith": 100,
                    "classId": "41b1b3ff-5baa-4b7f-aab1-1939fe77ac82"
                }
                """;

        mockMvc.perform(post("/characters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_USER_JWT)
                        .content(mageJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.description").value("No Class with id=41b1b3ff-5baa-4b7f-aab1-1939fe77ac82"));

    }
}
