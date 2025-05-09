package com.university.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Controller
public class CourseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/courses")
    public String listCourses(Model model, HttpSession session) {
        if (session.getAttribute("studentId") == null) {
            return "redirect:/login";
        }

        List<Map<String, Object>> courses = jdbcTemplate.queryForList("SELECT * FROM courses");
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable int courseId, HttpSession session, Model model) {
        Integer studentId = (Integer) session.getAttribute("studentId");

        if (studentId == null) {
            return "redirect:/login";
        }

        // Check if already registered
        String checkSql = "SELECT COUNT(*) FROM registrations WHERE student_id=? AND course_id=?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, studentId, courseId);
        if (count != null && count > 0) {
            model.addAttribute("message", "You are already registered for this course.");
            return "success";
        }

        // Insert registration
        String insertSql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, studentId, courseId, LocalDate.now());

        model.addAttribute("message", "You have successfully registered for the course!");
        return "success";
    }
}
