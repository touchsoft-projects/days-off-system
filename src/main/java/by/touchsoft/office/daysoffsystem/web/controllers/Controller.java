package by.touchsoft.office.daysoffsystem.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/daysoff")
public class Controller {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
