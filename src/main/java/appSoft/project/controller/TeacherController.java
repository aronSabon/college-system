package appSoft.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import appSoft.project.constant.SalaryStatus;
import appSoft.project.model.Fees;
import appSoft.project.model.FeesType;
import appSoft.project.model.Salary;
import appSoft.project.model.SalarySetting;
import appSoft.project.model.Subject;
import appSoft.project.model.Teacher;
import appSoft.project.service.FacultyService;
import appSoft.project.service.SalaryPaymentService;
import appSoft.project.service.SalaryService;
import appSoft.project.service.SalarySettingService;
import appSoft.project.service.SubjectService;
import appSoft.project.service.TeacherService;
import appSoft.project.utils.StudentExcel;
import appSoft.project.utils.TeacherExcel;


@Controller
public class TeacherController {
	@Autowired
	TeacherService ts;
	@Autowired
	FacultyService fs;
	@Autowired
	SubjectService ss;
	@Autowired
	SalarySettingService salarySettingService;
	@Autowired
	SalaryService salaryService;
	@Autowired
	SalaryPaymentService salaryPaymentService;


	@GetMapping("/addTeacher")
	private String teacherForm(Model model) {
		model.addAttribute("fList", fs.getAllFaculty());
		model.addAttribute("sList", ss.getAllSubject());
		return "AddTeacher";
	}
	@PostMapping("/addTeacher")
	private String addTeacher(@ModelAttribute Teacher teacher,@RequestParam MultipartFile image,RedirectAttributes redirectAttributes) {
		  try {
			  teacher.setImageName(image.getOriginalFilename());
				ts.addTeacher(teacher);

				Salary salary = new Salary();
				salary.setAmount(teacher.getSalary()*teacher.getPeriod());
				salary.setFaculty(teacher.getFaculty());
				salary.setFullName(teacher.getFullName());
				salary.setGender(teacher.getGender());
				salary.setGrade(teacher.getGrade());
				salary.setTeacherId(teacher.getId());
				salary.setSubject(teacher.getSubject());
				salary.setSection(teacher.getSection());
				salary.setPeriod(teacher.getPeriod());
				salary.setPayDate(LocalDate.now().withDayOfMonth(28));
				salary.setStatus(SalaryStatus.UNPAID);
				salary.setMonth(LocalDate.now().getMonth().toString());
				salaryService.addSalary(salary);
				

		        // Add flash attributes
		        redirectAttributes.addFlashAttribute("message", "Teacher added successfully!");
		        redirectAttributes.addFlashAttribute("status", "success");
		    } catch (Exception e) {
		        // Catch any exception that occurs during the save operation
		        redirectAttributes.addFlashAttribute("message", "Teacher add failed!");
		        redirectAttributes.addFlashAttribute("status", "error");
		    }
		
		

		return "redirect:/addTeacher";
	}
	@GetMapping("/teacherList")
	private String teacherList(Model model) {
		model.addAttribute("tList", ts.getAllTeacher());
		return "TeacherList";
	}
	@GetMapping("/deleteTeacher")
	private String deleteTeacher(@RequestParam int id) {
		salaryService.deleteAllByTeacherId(id);
		salaryPaymentService.deleteAllByTeacherId(id);
		ts.deleteTeacherById(id);

		return "redirect:/teacherList";
	}
	@GetMapping("/editTeacher")
	private String editTeacher(@RequestParam int id,Model model) {
		model.addAttribute("tModel",ts.getTeacherById(id));
		model.addAttribute("fList", fs.getAllFaculty());
		model.addAttribute("sList", ss.getAllSubject());

		return "EditTeacher";
	}
	@PostMapping("/updateTeacher")
	private String updateTeacher(@ModelAttribute Teacher teacher,@RequestParam MultipartFile image) {
		teacher.setImageName(image.getOriginalFilename());

		ts.updateTeacher(teacher);
		return "redirect:/teacherList";
	}
	@GetMapping("/teacherExcel")
	public  ModelAndView excel() {
		ModelAndView mv =new ModelAndView();
		mv.addObject("teacherList", ts.getAllTeacher());
		mv.setView(new TeacherExcel());
		return mv;
	}


}
