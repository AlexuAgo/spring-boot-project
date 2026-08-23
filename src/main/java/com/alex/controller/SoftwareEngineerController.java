package com.alex.controller;

import com.alex.dto.SoftwareEngineerDTO;
import com.alex.service.SoftwareEngineerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/software_engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;
    private static final Logger logger = LoggerFactory.getLogger(SoftwareEngineerController.class);

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    //get all through dto
    @GetMapping
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Get all engineers",
            description = "Get all engineers"
    )
    public List<SoftwareEngineerDTO> getAllSoftwareEngineersDTO() {
        logger.info("GET http://localhost:8080/api/v1/software_engineers called");
        return softwareEngineerService.getAllSoftwareEngineersDTO();
    }


    @PostMapping
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Add an engineer",
            description = "Add an engineer"
    )
    public void addNewSoftwareEngineer(@Valid @RequestBody SoftwareEngineerDTO softwareEngineer) {
        logger.info("POST http://localhost:8080/api/v1/software_engineers called with payload {}", softwareEngineer);
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }


    @GetMapping("{id}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Get engineer by their ID",
            description = "Get engineer by their ID"
    )
    public SoftwareEngineerDTO getEngineerById(@PathVariable Integer id) {
        logger.info("GET http://localhost:8080/api/v1/software_engineers/{} called",id);
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    //update
    @PutMapping("{id}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Update an engineer",
            description = "Update an engineer"
    )
    public SoftwareEngineerDTO putEngineerById(@PathVariable Integer id,@Valid @RequestBody SoftwareEngineerDTO dto) {
        logger.info("PUT http://localhost:8080/api/v1/software_engineers/{} with payload {}", id, dto);
        return softwareEngineerService.updateEngineer(id, dto);
    }
    //delete
    @DeleteMapping("{id}")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Delete an engineer through their id",
            description = "Delete an engineer through their id"
    )
    public ResponseEntity<Void> deleteSoftwareEngineerById(@PathVariable Integer id) {
        logger.info("DELETE http://localhost:8080/api/v1/software_engineers/{} called",id);
        softwareEngineerService.deleteSoftwareEngineerById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    //flexible search endpoint
    @GetMapping("/search")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Search software engineers",
            description = "Search engineers by either name and/or techStack, with pagination and sorting"
    )
    public Page<SoftwareEngineerDTO> searchEngineers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String techStack,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        logger.info("GET /search called with name='{}', techStack='{}', page={}, size={}, sortBy='{}', direction='{}'",
                name, techStack, page, size, sortBy, direction);
        return softwareEngineerService.searchEngineers(name, techStack, page, size, sortBy, direction);
    }

    //get paginated and sorted data
    @GetMapping("/paginated")
    @io.swagger.v3.oas.annotations.Operation(
            summary = "Get paginated engineers",
            description = "Retrieve all engineers with pagination and sorting options"
    )
    public Page<SoftwareEngineerDTO> getPaginatedEngineers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        logger.info("GET /paginated called with page={}, size={}, sortBy='{}', direction='{}'",
                page, size, sortBy, direction);
        return softwareEngineerService.getPaginatedEngineers(page, size, sortBy, direction);
    }
}
