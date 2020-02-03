package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;

import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller interacts with database.
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private Logger logger = Logger.getLogger(getClass());

    private UserService userService;
    private PeriodService periodService;

    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        logger.info("User was added:" + userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        logger.info("User was updated:" + userDto);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getUserByEmail")
    public ResponseEntity<List<UserDto>> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.getUserByEmail(email);
        List<UserDto> result = ImmutableList.of();
        if (userDto != null) {
            result = ImmutableList.of(userDto);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userEntities = userService.getAll();
        return ResponseEntity.ok(userEntities);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(
            @RequestBody UserPasswordDto userPasswordDto
    ) {
        boolean result = userService.updateUserPassword(userPasswordDto);
        if (result) {
            logger.info("Password was updated to " + userPasswordDto.getEmail());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/deleteUserById")
    public ResponseEntity<Void> deleteUserById(@RequestParam String id) {
        userService.deleteById(id);
        logger.info("User was deleted:id=" + id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addPeriod")
    public ResponseEntity<String> addPeriod(@RequestBody PeriodDto periodDto) {
        String id = periodService.addAnyPeriod(periodDto);
        if (id != null) {
            logger.info("Period was added:" + periodDto);
            return ResponseEntity.ok(id);
        } else {
            logger.warn("Bad request:" + periodDto);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updatePeriod")
    public ResponseEntity<Void> updatePeriod(@RequestBody PeriodDto periodDto) {
        boolean result = periodService.updateAnyPeriod(periodDto);
        if (result) {
            logger.info("Period was updated:" + periodDto);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Bad request:" + periodDto);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletePeriodById")
    public ResponseEntity<Void> deletePeriodById(@RequestParam String id) {
        periodService.deleteById(id);
        logger.info("Period was deleted:id=" + id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getPeriodsByUserId")
    public ResponseEntity<List<PeriodDto>> getPeriodsByUserId(@RequestParam String id) {
        List<PeriodDto> periodDtos = null;
        if (id != null) {
        	periodDtos = periodService.getByUserId(id);
        	return ResponseEntity.ok(periodDtos);
        } else {
        	return ResponseEntity.badRequest().build();
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPeriodService(PeriodService periodService) {
        this.periodService = periodService;
    }
}
