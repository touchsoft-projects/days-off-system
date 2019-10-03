package by.touchsoft.office.daysoffsystem.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller is used to handle request to views
 */
@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/healthCheck")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
