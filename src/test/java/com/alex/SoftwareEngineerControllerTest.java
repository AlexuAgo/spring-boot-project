package com.alex;

import com.alex.dto.SoftwareEngineerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // ensures each test rolls back changes
public class SoftwareEngineerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetEngineer() throws Exception {
        SoftwareEngineerDTO engineer = new SoftwareEngineerDTO(null, "Matt", "SQL");

        // Create engineer
        mockMvc.perform(post("/api/v1/software_engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(engineer)))
                .andExpect(status().isOk());

        // Fetch all engineers to check creation
        mockMvc.perform(get("/api/v1/software_engineers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[0].techStack").value("java"));
    }

    @Test
    void testGetEngineerById() throws Exception {
        // First, create an engineer
        SoftwareEngineerDTO engineer = new SoftwareEngineerDTO(null, "Alex", "Java");
        mockMvc.perform(post("/api/v1/software_engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(engineer)))
                .andExpect(status().isOk());

        // Fetch engineer by ID 1
        mockMvc.perform(get("/api/v1/software_engineers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.techStack").value("java"));
    }

    @Test
    void testUpdateEngineer() throws Exception {
        // Create engineer
        SoftwareEngineerDTO engineer = new SoftwareEngineerDTO(null, "Alex", "Java");
        mockMvc.perform(post("/api/v1/software_engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(engineer)))
                .andExpect(status().isOk());

        // Update engineer
        SoftwareEngineerDTO updated = new SoftwareEngineerDTO(null, "Alex Updated", "Spring Boot");
        mockMvc.perform(put("/api/v1/software_engineers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());

        // Verify update
        mockMvc.perform(get("/api/v1/software_engineers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex Updated"))
                .andExpect(jsonPath("$.techStack").value("Spring Boot"));
    }

    @Test
    void testDeleteEngineer() throws Exception {
        // Create engineer
        SoftwareEngineerDTO engineer = new SoftwareEngineerDTO(null, "Alex", "java");
        mockMvc.perform(post("/api/v1/software_engineers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(engineer)))
                .andExpect(status().isOk());

        // Delete engineer (should return 204)
        mockMvc.perform(delete("/api/v1/software_engineers/1"))
                .andExpect(status().isNoContent());

        // Check it no longer exists
        mockMvc.perform(get("/api/v1/software_engineers/1"))
                .andExpect(status().isNotFound());
    }

}
