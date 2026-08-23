package com.alex;

import com.alex.dto.SoftwareEngineerDTO;
import com.alex.entity.SoftwareEngineer;
import com.alex.repository.SoftwareEngineerRepository;
import com.alex.service.SoftwareEngineerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // ensures each test runs in a transaction rolled back after the test
public class SoftwareEngineerServiceTest {

    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;

    @Autowired
    private SoftwareEngineerService softwareEngineerService;

    private SoftwareEngineer engineer1;
    private SoftwareEngineer engineer2;

    @BeforeEach
    public void setUp() {
        softwareEngineerRepository.deleteAll();

        engineer1 = new SoftwareEngineer();
        engineer1.setName("Alex");
        engineer1.setTechStack("Java");

        engineer2 = new SoftwareEngineer();
        engineer2.setName("Maria");
        engineer2.setTechStack("Python");

        softwareEngineerRepository.saveAll(List.of(engineer1, engineer2));
    }

    @Test
    public void testGetEngineerById() {
        SoftwareEngineerDTO result = softwareEngineerService.getSoftwareEngineerById(engineer1.getId());

        assertEquals("Alex", result.getName());
        assertEquals("Java", result.getTechStack());
    }

    @Test
    public void testGetAllEngineersDTO() {
        List<SoftwareEngineerDTO> engineers = softwareEngineerService.getAllSoftwareEngineersDTO();

        assertEquals(2, engineers.size());
    }

    @Test
    public void testGetEngineersByTechStack() {
        List<SoftwareEngineerDTO> javaEngineers = softwareEngineerService.getEngineersByTechStack("Java");

        assertEquals(1, javaEngineers.size());
        assertEquals("Alex", javaEngineers.getFirst().getName());
    }

    @Test
    public void testFindByNameContainingIgnoreCase() {
        List<SoftwareEngineerDTO> result = softwareEngineerService.findByNameContainingIgnoreCase("maria");

        assertEquals(1, result.size());
        assertEquals("Maria", result.getFirst().getName());
    }
}
