package com.example.tutorial3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;

@Controller
public class StudentController {
	private final StudentService studentService;

	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(	@RequestParam(value="npm", required=true) String npm,
						@RequestParam(value="name", required=true) String name,
						@RequestParam(value="gpa", required=true) double gpa
			
			) {
		StudentModel student = new StudentModel(name, npm, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping("/student/view")
	public String view(	Model model,
						@RequestParam(value="npm", required=true) String npm
			) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}
	
	@RequestMapping("/student/viewall")
	public String viewall(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
	@RequestMapping("/student/view/{npm}")
	public String viewByPath(	Model model,
						@PathVariable String npm
			) {
		StudentModel student = studentService.selectStudent(npm);
		if(student == null) {
			model.addAttribute("msgTitle","Gagal Menampilkan Student");
			model.addAttribute("msgBody","NPM tidak ditemukan. Gagal Menampilkan Student");
			return "errorview";	
		}else {
			model.addAttribute("student", student);
			return "view";
		}
	}
	
	@RequestMapping("/student/delete/{npm}")
	public String delete(	Model model,
						@PathVariable String npm
			) {
		StudentModel student = studentService.selectStudent(npm);
		if(student == null) {
			model.addAttribute("msgTitle","Gagal Menghapus Student");
			model.addAttribute("msgBody","NPM tidak ditemukan. Gagal Menghapus Student");
			return "errorview";	
		}else {
			StudentModel stud = new StudentModel(student.getName(), student.getNpm(), student.getGpa());
			studentService.deleteStudent(npm);
			model.addAttribute("student", stud);
			return "delete";
		}
	}
}
