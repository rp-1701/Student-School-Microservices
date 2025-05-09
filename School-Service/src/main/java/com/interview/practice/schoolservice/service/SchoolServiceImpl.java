package com.interview.practice.schoolservice.service;

import com.interview.practice.schoolservice.dto.SchoolDto;
import com.interview.practice.schoolservice.entity.School;
import com.interview.practice.schoolservice.repository.SchoolRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

    private SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public SchoolDto addSchool(SchoolDto schoolDto) {
        School school = new School(schoolDto.getSchoolName(), schoolDto.getLocation());
        School savedSchool = schoolRepository.save(school);
        
        SchoolDto createdSchoolDto = new SchoolDto();
        createdSchoolDto.setId(savedSchool.getId());
        createdSchoolDto.setSchoolName(savedSchool.getSchoolName());
        createdSchoolDto.setLocation(savedSchool.getLocation());
        
        return createdSchoolDto;
    }

    @Override
    public Optional<SchoolDto> updateSchool(SchoolDto schoolDto, Long schoolId) {
        return schoolRepository.findById(schoolId)
                .map(existingSchool -> {
                    existingSchool.setSchoolName(schoolDto.getSchoolName());
                    existingSchool.setLocation(schoolDto.getLocation());
                    schoolRepository.save(existingSchool);

                    SchoolDto updatedSchoolDto = new SchoolDto();
                    updatedSchoolDto.setId(existingSchool.getId());
                    updatedSchoolDto.setSchoolName(existingSchool.getSchoolName());
                    updatedSchoolDto.setLocation(existingSchool.getLocation());

                    return updatedSchoolDto;
                });
    }

    @Override
    public boolean deleteSchool(Long schoolId) {
        return schoolRepository.findById(schoolId)
                .map(school -> {
                    schoolRepository.delete(school);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<SchoolDto> getSchoolById(Long schoolId) {
        return schoolRepository.findById(schoolId)
            .map(existingSchool -> {
                SchoolDto schoolDto = new SchoolDto();
                schoolDto.setId(existingSchool.getId());
                schoolDto.setSchoolName(existingSchool.getSchoolName());
                schoolDto.setLocation(existingSchool.getLocation());
                return schoolDto;
            });
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        return schoolRepository.findAll().stream()
                .map(school -> {
                    SchoolDto dto = new SchoolDto();
                    dto.setId(school.getId());
                    dto.setSchoolName(school.getSchoolName());
                    dto.setLocation(school.getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
