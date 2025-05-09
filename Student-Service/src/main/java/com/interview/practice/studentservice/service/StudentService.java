package com.interview.practice.studentservice.service;

import com.interview.practice.studentservice.dto.StudentRequestDto;
import com.interview.practice.studentservice.dto.StudentResponseDto;
import java.util.List;

public interface StudentService {
    
    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);
    
    StudentResponseDto getStudentById(Long id);
    
    List<StudentResponseDto> getAllStudents();

    StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto);
    
    void deleteStudent(Long id);
}
