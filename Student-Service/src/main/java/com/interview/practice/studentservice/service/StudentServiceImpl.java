package com.interview.practice.studentservice.service;

import com.interview.practice.studentservice.dto.SchoolDto;
import com.interview.practice.studentservice.dto.StudentRequestDto;
import com.interview.practice.studentservice.dto.StudentResponseDto;
import com.interview.practice.studentservice.entity.Student;
import com.interview.practice.studentservice.exception.SchoolNotFoundException;
import com.interview.practice.studentservice.exception.ServiceCommunicationException;
import com.interview.practice.studentservice.exception.StudentNotFoundException;
import com.interview.practice.studentservice.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;

    @Value("${school.service.url}")
    private String schoolServiceUrl;

    public StudentServiceImpl(StudentRepository studentRepository, RestTemplate restTemplate) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        // Verify if school exists
        verifySchoolExists(requestDto.getSchoolId());

        // Create student
        Student student = new Student();
        student.setFirstName(requestDto.getFirstName());
        student.setLastName(requestDto.getLastName());
        student.setSchoolId(requestDto.getSchoolId());

        Student savedStudent = studentRepository.save(student);
        return convertToResponseDto(savedStudent, getSchoolDetails(requestDto.getSchoolId()));
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));

        SchoolDto schoolDto = getSchoolDetails(student.getSchoolId());
        return convertToResponseDto(student, schoolDto);
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> convertToResponseDto(student, getSchoolDetails(student.getSchoolId())))
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        // Verify if school exists
        verifySchoolExists(requestDto.getSchoolId());

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));

        student.setFirstName(requestDto.getFirstName());
        student.setLastName(requestDto.getLastName());
        student.setSchoolId(requestDto.getSchoolId());

        Student updatedStudent = studentRepository.save(student);
        return convertToResponseDto(updatedStudent, getSchoolDetails(requestDto.getSchoolId()));
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    private SchoolDto getSchoolDetails(Long schoolId) {
        try {
            ResponseEntity<SchoolDto> response = restTemplate.getForEntity(
                    schoolServiceUrl + "/school/get/" + schoolId,
                    SchoolDto.class
            );
            return response.getBody();
        } catch (Exception ex) {
            return createPlaceholderSchoolDto(schoolId);
        }
    }

    private void verifySchoolExists(Long schoolId) {
        try {
            ResponseEntity<SchoolDto> response = restTemplate.getForEntity(
                    schoolServiceUrl + "/school/get/" + schoolId,
                    SchoolDto.class
            );
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new SchoolNotFoundException("School not found with ID: " + schoolId);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new SchoolNotFoundException("School not found with ID: " + schoolId);
        } catch (Exception ex) {
            throw new ServiceCommunicationException("Error communicating with School Service");
        }
    }

    private SchoolDto createPlaceholderSchoolDto(Long schoolId) {
        SchoolDto dto = new SchoolDto();
        dto.setId(schoolId);
        dto.setSchoolName("School information unavailable");
        dto.setLocation("Unknown");
        return dto;
    }

    private StudentResponseDto convertToResponseDto(Student student, SchoolDto schoolDto) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setFirstName(student.getFirstName());
        responseDto.setLastName(student.getLastName());
        responseDto.setSchool(schoolDto);
        return responseDto;
    }
}
