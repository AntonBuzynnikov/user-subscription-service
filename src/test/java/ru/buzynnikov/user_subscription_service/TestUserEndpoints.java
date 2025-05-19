package ru.buzynnikov.user_subscription_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserEndpoints {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Transactional
    void testGetUserAndStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("alexander"));
    }
    @Test
    @Transactional
    void testCreateUserAndStatusOk() throws Exception {
        String request = """
                {
                    "name": "Иван"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Иван"));
    }
    @Test
    @Transactional
    void testUpdateUserAndStatusNoContent() throws Exception {
        String request = """
                {
                    "name": "Иван"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Иван"));
    }
    @Test
    @Transactional
    void testDeleteUserAndStatusNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional
    void testAddUserSubscriptionAndStatusNoContent() throws Exception {
        String request = """
                {
                    "id":6
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/6/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/6/subscriptions"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(6));
    }

    @Test
    @Transactional
    void testDeleteUserSubscriptionAndStatusNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/4/subscriptions/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/4/subscriptions"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
    @Test
    void testGetSubscriptionsAndStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/4/subscriptions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
    @Test
    void testGetTop3SubscriptionsAndStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/subscriptions/top"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3));

    }
}
