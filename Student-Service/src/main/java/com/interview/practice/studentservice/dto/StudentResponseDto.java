package com.interview.practice.studentservice.dto;

public class StudentResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private SchoolDto school;

    public StudentResponseDto(StudentRequestDto studentRequestDto) {
        this.id = studentRequestDto.getId();
        this.firstName = studentRequestDto.getFirstName();
        this.lastName = studentRequestDto.getLastName();
    }

    public StudentResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SchoolDto getSchool() {
        return school;
    }

    public void setSchool(SchoolDto school) {
        this.school = school;
    }
} 