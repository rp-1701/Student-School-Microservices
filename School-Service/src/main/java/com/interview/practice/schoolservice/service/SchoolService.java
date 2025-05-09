package com.interview.practice.schoolservice.service;

import com.interview.practice.schoolservice.dto.SchoolDto;
import java.util.List;
import java.util.Optional;

public interface SchoolService {

    SchoolDto addSchool(SchoolDto schoolDto);

    Optional<SchoolDto> updateSchool(SchoolDto schoolDto, Long id);

    boolean deleteSchool(Long schoolId);

    Optional<SchoolDto> getSchoolById(Long schoolId);

    List<SchoolDto> getAllSchools();

}
