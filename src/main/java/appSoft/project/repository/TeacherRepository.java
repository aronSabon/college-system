package appSoft.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import appSoft.project.model.Student;
import appSoft.project.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {


}
