package appSoft.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import appSoft.project.model.Student;
import appSoft.project.service.FacultyService;
import appSoft.project.service.StudentService;


@Controller
public class StudentController {
	@Autowired
	StudentService ss;
	@Autowired
	FacultyService fs;
	
	@GetMapping("/addStudent")
	private String studentForm(Model model) {
		model.addAttribute("fList", fs.getAllFaculty());
		return "AddStudent";
	}
	@PostMapping("/addStudent")
	private String addStudent(@ModelAttribute Student student,@RequestParam MultipartFile image) {
		student.setImageName(image.getOriginalFilename());
		ss.addStudent(student);
		return "redirect:/addStudent";
	}
	@GetMapping("/studentList")
	private String studentList(Model model) {
		model.addAttribute("sList", ss.getAllStudent());
		return "StudentList";
	}
	@GetMapping("/deleteStudent")
	private String deleteStudent(@RequestParam int id) {
		ss.deleteStudentById(id);
		return "redirect:/studentList";
	}
	@GetMapping("/editStudent")
	private String editStudent(@RequestParam int id,Model model) {
		model.addAttribute("sModel",ss.getStudentById(id));
		model.addAttribute("fList", fs.getAllFaculty());
		return "EditStudent";
	}
	@PostMapping("/updateStudent")
	private String updateStudent(@ModelAttribute Student student,@RequestParam MultipartFile image) {
		student.setImageName(image.getOriginalFilename());
		ss.updateStudent(student);
		return "redirect:/studentList";
	}
}
