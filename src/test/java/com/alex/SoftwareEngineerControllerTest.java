package com.alex;

import com.alex.dto.SoftwareEngineerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SoftwareEngineerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // The create endpoint returns an empty body, so we fetch the list
    // afterward and match by name to recover the generated ID.
    private Integer createEngineerAndGetId(String name, String techStack) throws Exception {
        SoftwareEngineerDTO engineer = new SoftwareEngineerDTO(null, name, techStack);

        mockMvc.perform(post("/api/v1/software_engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(engineer)))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/api/v1/software_engineers"))
                .andExpect(status().isOk())
                .andReturn();

        var list = objectMapper.readTree(result.getResponse().getContentAsString());
        for (var node : list) {
            if (node.get("name").asText().equals(name)) {
                return node.get("id").asInt();
            }
        }
        throw new IllegalStateException("Created engineer not found in list response");
    }

    @Test
    void testCreateAndGetEngineer() throws Exception {
        createEngineerAndGetId("Matt", "SQL");

        mockMvc.perform(get("/api/v1/software_engineers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Matt"))
                .andExpect(jsonPath("$[0].techStack").value("SQL"));
    }

    @Test
    void testGetEngineerById() throws Exception {
        Integer id = createEngineerAndGetId("Alex", "Java");

        mockMvc.perform(get("/api/v1/software_engineers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.techStack").value("Java"));
    }

    @Test
    void testUpdateEngineer() throws Exception {
        Integer id = createEngineerAndGetId("Alex", "Java");

        SoftwareEngineerDTO updated = new SoftwareEngineerDTO(null, "Alex Updated", "Spring Boot");
        mockMvc.perform(put("/api/v1/software_engineers/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/software_engineers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex Updated"))
                .andExpect(jsonPath("$.techStack").value("Spring Boot"));
    }

    @Test
    void testDeleteEngineer() throws Exception {
        Integer id = createEngineerAndGetId("Alex", "java");

        mockMvc.perform(delete("/api/v1/software_engineers/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/software_engineers/" + id))
                .andExpect(status().isNotFound());
    }
}