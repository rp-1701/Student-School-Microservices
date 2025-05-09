package com.interview.practice.schoolservice.repository;

import com.interview.practice.schoolservice.entity.School;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
