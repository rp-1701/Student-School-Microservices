package com.interview.practice.schoolservice.controller;

import com.interview.practice.schoolservice.dto.SchoolDto;
import com.interview.practice.schoolservice.service.SchoolService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/create")
    public ResponseEntity<SchoolDto> createSchool(@RequestBody SchoolDto schoolDto) {
        SchoolDto createdSchool = schoolService.addSchool(schoolDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchool);
    }

    @GetMapping("/get/{schoolId}")
    public ResponseEntity<SchoolDto> getById(@PathVariable Long schoolId) {
        return schoolService.getSchoolById(schoolId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getall")
    public ResponseEntity<List<SchoolDto>> getAllSchools() {
        List<SchoolDto> schools = schoolService.getAllSchools();
        return schools.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(schools);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SchoolDto> updateSchool(@RequestBody SchoolDto schoolDto, @PathVariable Long id) {
        Optional<SchoolDto> schoolDto1 = schoolService.updateSchool(schoolDto, id);
        return schoolDto1.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchoolById(@PathVariable Long id) {
        boolean deleted = schoolService.deleteSchool(id);
        if (deleted) {
            return ResponseEntity.ok("School deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

}
