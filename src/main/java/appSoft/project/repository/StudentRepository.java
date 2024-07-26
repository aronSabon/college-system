package appSoft.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import appSoft.project.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

Student findByRollNo(int rollNo);
}
