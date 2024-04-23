package cloud.deuterium.maxbet.character.controllers;

import cloud.deuterium.maxbet.character.config.SecurityConfig;
import cloud.deuterium.maxbet.character.dtos.request.CreateItemDto;
import cloud.deuterium.maxbet.character.dtos.response.ItemDto;
import cloud.deuterium.maxbet.character.services.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static cloud.deuterium.maxbet.character.TestUtils.createItemDto;
import static cloud.deuterium.maxbet.character.TestUtils.getCreateItemDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */

@TestPropertySource(properties = "spring.cache.type=none")
@WebMvcTest(ItemController.class)
@Import(SecurityConfig.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private ItemService service;

    @Test
    @DisplayName("Should return 401 Unauthorized")
    void get_items_unauthorized() throws Exception {
        this.mockMvc.perform(get("/api/items"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    @DisplayName("Should return 403 Forbidden")
    void get_items_forbidden() throws Exception {
        this.mockMvc.perform(get("/api/items"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = {"GAME_MASTER"})
    @Test
    @DisplayName("Should return Items")
    void get_items_ok() throws Exception {

        Page<ItemDto> page = new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0);

        when(service.getAll(any(Pageable.class))).thenReturn(page);

        this.mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 401 Unauthorized")
    void get_item_unauthorized() throws Exception {
        this.mockMvc.perform(get("/api/items/8d1134c7-57e8-43ec-965d-abb7efd31047"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    @DisplayName("Should return Item with given ID")
    void get_item_ok() throws Exception {
        ItemDto itemDto = createItemDto();
        when(service.getById(any(String.class))).thenReturn(itemDto);

        this.mockMvc.perform(get("/api/items/8d1134c7-57e8-43ec-965d-abb7efd31047"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8d1134c7-57e8-43ec-965d-abb7efd31047"))
                .andExpect(jsonPath("$.name").value("Sword"));
    }

    @Test
    @DisplayName("Should return 401 Unauthorized")
    void create_item_unauthorized() throws Exception {
        this.mockMvc.perform(post("/api/items"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = {"GAME_MASTER"})
    @Test
    @DisplayName("Should create new Item")
    void create_item_ok() throws Exception {

        CreateItemDto createItemDto = getCreateItemDto();
        ItemDto itemDto = createItemDto();
        when(service.create(any(CreateItemDto.class))).thenReturn(itemDto);

        this.mockMvc.perform(post("/api/items")
                        .content(objectMapper.writeValueAsString(createItemDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("8d1134c7-57e8-43ec-965d-abb7efd31047"))
                .andExpect(jsonPath("$.name").value("Sword"));
    }

    @Test
    @DisplayName("Should return 401 Unauthorized")
    void grant_item_unauthorized() throws Exception {
        this.mockMvc.perform(post("/api/items/grant")
                        .with(csrf().asHeader()))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    @DisplayName("Should grant Item to the Character")
    void grant_item_ok() throws Exception {

        String grantJson = """
                {
                    "itemId": "8d1134c7-57e8-43ec-965d-abb7efd31047",
                    "characterId": "b71aaddc-75e0-49ee-b497-fb9cd51fbe58"
                }
                """;

        this.mockMvc.perform(post("/api/items/grant")
                        .content(grantJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 401 Unauthorized")
    void gift_item_unauthorized() throws Exception {
        this.mockMvc.perform(post("/api/items/grant"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    @DisplayName("Should gift Item from one Character to one to another")
    void gift_item_ok() throws Exception {

        String giftJson = """
                {
                    "characterItemId": "26076172-2015-4d8c-8cb9-2452485f9091",
                    "characterId": "b71aaddc-75e0-49ee-b497-fb9cd51fbe58"
                }
                """;

        this.mockMvc.perform(post("/api/items/gift")
                        .content(giftJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
