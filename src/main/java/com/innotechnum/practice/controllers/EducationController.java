package com.innotechnum.practice.controllers;

import com.innotechnum.practice.domain.Education;
import com.innotechnum.practice.repos.EducationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/educations")
public class EducationController {
    @Autowired
    EducationRepo educationRepo;

    @GetMapping
    public String main(Model model) {
        Iterable<Education> educations = educationRepo.findAll();
        model.addAttribute("educations", educations);
        return "educations";
    }

    @PostMapping
    public String add(@RequestParam String name, Model model) {
        Education education = new Education(name);
        educationRepo.save(education);

        Iterable<Education> educations = educationRepo.findAll();
        model.addAttribute("educations", educations);
        return "educations";
    }

    @PostMapping("remove")
    public String remove(@RequestParam Long id, Model model) {
        educationRepo.deleteById(id);

        Iterable<Education> educations = educationRepo.findAll();
        model.addAttribute("educations", educations);
        return "educations";
    }
}
