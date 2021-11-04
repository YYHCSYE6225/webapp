package edu.neu.coe.csye6225.webapp.controller;

import edu.neu.coe.csye6225.webapp.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/notes")
public class NotesController {
    @GetMapping(value = "")
    public String getUser(){
        return "cicd testing";
    }
}
