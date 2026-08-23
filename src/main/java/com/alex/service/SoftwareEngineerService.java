package com.alex.service;


import com.alex.entity.SoftwareEngineer;
import com.alex.dto.SoftwareEngineerDTO;
import com.alex.repository.SoftwareEngineerRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final Logger logger = LoggerFactory.getLogger(SoftwareEngineerService.class);

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    /* Get all software engineers
    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }*/

    /*Get all software engineers but through the dto */
    public List<SoftwareEngineerDTO> getAllSoftwareEngineersDTO() {
        return softwareEngineerRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    /*Insert one software engineer*/
    public void insertSoftwareEngineer(@Valid SoftwareEngineerDTO dto) {
        logger.info("Create software engineer with name '{}' and techStack '{}'", dto.getName(), dto.getTechStack());
        SoftwareEngineer engineer = new SoftwareEngineer();
        engineer.setName(dto.getName());
        engineer.setTechStack(dto.getTechStack());
        softwareEngineerRepository.save(engineer);
        logger.info("Engineer '{}' saved successfully", engineer.getName());
    }

    /*Get one software engineer by id*/
    public SoftwareEngineerDTO getSoftwareEngineerById(Integer id) {
        logger.info("Get software engineer with id '{}'", id);
        SoftwareEngineer engineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Engineer " + id + " not found"));
        logger.info("Engineer with id '{}' fetched successfully",id);

        return mapToDTO(engineer);
    }


    /*Update a software engineer */
    public SoftwareEngineerDTO updateEngineer(Integer id, @Valid SoftwareEngineerDTO dto) {
        logger.info("Update software engineer with id '{}' and name '{}' ", id, dto.getName());
        SoftwareEngineer engineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Engineer" + id + " not found"));

        engineer.setName(dto.getName());
        engineer.setTechStack(dto.getTechStack());

        softwareEngineerRepository.save(engineer);
        logger.info("Engineer '{}' updated successfully", engineer.getName());
        return mapToDTO(engineer);


    }

    /*Delete a software engineer */
    public void deleteSoftwareEngineerById(Integer id) {
        logger.info("Delete software engineer with id '{}'", id);
        SoftwareEngineer engineer = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Engineer" + id + " not found"));

        softwareEngineerRepository.deleteById(id);
        logger.info("Engineer with id '{}' deleted successfully", id);

    }



    //method to find by techStack
    public List<SoftwareEngineerDTO> getEngineersByTechStack(String techStack) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("techStack").descending());
        return softwareEngineerRepository.findByTechStackContainingIgnoreCase(techStack,pageable).map(this::mapToDTO).toList();
    }

    //method to find by name
    public List<SoftwareEngineerDTO> findByNameContainingIgnoreCase(String name) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());
        return softwareEngineerRepository.findByNameContainingIgnoreCase(name,pageable).map(this::mapToDTO).toList();
    }

    //method to paginate and sort
    public Page<SoftwareEngineerDTO> getPaginatedEngineers(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return softwareEngineerRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    //pagination, sorting and optional filtering
    public Page<SoftwareEngineerDTO> searchEngineers(String name, String techStack, int page, int size, String sortBy, String direction) {
        logger.info("Searching engineers with name='{}', techStack='{}', page={}, size={}, sortBy='{}', direction='{}'",
                name, techStack, page, size, sortBy, direction);
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<SoftwareEngineer> engineers;

        if (name != null && !name.isEmpty() && techStack != null && !techStack.isEmpty()) {
            engineers = softwareEngineerRepository
                    .findByNameContainingIgnoreCaseAndTechStackContainingIgnoreCase(name, techStack, pageable);
        } else if (name != null && !name.isEmpty()) {
            engineers = softwareEngineerRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (techStack != null && !techStack.isEmpty()) {
            engineers = softwareEngineerRepository.findByTechStackContainingIgnoreCase(techStack, pageable);
        } else {
            engineers = softwareEngineerRepository.findAll(pageable);
        }
        logger.info("Found {} engineers matching criteria", engineers.getTotalElements());
        return engineers.map(this::mapToDTO);

    }

    //maps an engineer to the dto
    public SoftwareEngineerDTO mapToDTO(SoftwareEngineer engineer) {
        return new SoftwareEngineerDTO(
                engineer.getId(),
                engineer.getName(),
                engineer.getTechStack()
        );
    }

}
