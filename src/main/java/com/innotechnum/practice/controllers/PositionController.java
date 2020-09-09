package com.innotechnum.practice.controllers;

import com.innotechnum.practice.domain.Position;
import com.innotechnum.practice.repos.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    PositionRepo positionRepo;

    @GetMapping
    public String main(Model model) {
        Iterable<Position> positions = positionRepo.findAll();
        model.addAttribute("positions", positions);
        return "positions";
    }

    @PostMapping
    public String add(@RequestParam String name, Model model) {
        Position position = new Position(name);
        positionRepo.save(position);

        Iterable<Position> positions = positionRepo.findAll();
        model.addAttribute("positions", positions);

        return "positions";
    }

    @PostMapping("remove")
    public String remove(@RequestParam Long id, Model model) {
        positionRepo.deleteById(id);

        Iterable<Position> positions = positionRepo.findAll();
        model.addAttribute("positions", positions);
        return "positions";
    }

}
