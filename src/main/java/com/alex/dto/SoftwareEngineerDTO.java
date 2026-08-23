package com.alex.dto;

public class SoftwareEngineerDTO {

    private final Integer id;
    private final String name;
    private final String techStack;

    //constructor
    public SoftwareEngineerDTO(Integer id, String name, String techStack) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
    }

    //read only
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTechStack() {
        return techStack;
    }
}
