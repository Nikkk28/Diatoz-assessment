package com.diatoz.assesment.repositories;

import com.diatoz.assesment.models.Student;
import com.diatoz.assesment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.user.username = :username")
    Optional<Student> findByUsername(String username);

    Optional<Student> findByUserId(Long userId);
}