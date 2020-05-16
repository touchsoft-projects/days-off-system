package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import by.touchsoft.office.daysoffsystem.web.security.SecurityHelper;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * This controller interacts with database.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private Logger logger = Logger.getLogger(getClass());

    private UserService userService;
    private PeriodService periodService;

    @GetMapping("/getUser")
    public ResponseEntity<List<UserDto>> getUser() {
        String principalUserName = SecurityHelper.getUserName();
        List<UserDto> result = ImmutableList.of();
        if (principalUserName != null) {
            UserDto userDto = userService.getUserByEmail(principalUserName);
            if (userDto != null) {
                result = ImmutableList.of(userDto);
            }
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(
            @RequestBody UserPasswordDto userPasswordDto
    ) {
        boolean result = false;
        String principalUserName = SecurityHelper.getUserName();
        if (Objects.equals(principalUserName, userPasswordDto.getEmail())) {
            result = userService.updateUserPassword(userPasswordDto);
        }
        if (result) {
            logger.info("Password was updated to " + userPasswordDto.getEmail());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/addPeriod")
    public ResponseEntity<String> addPeriod(@RequestBody PeriodDto periodDto) {
        String id = periodService.addPeriodByEmail(periodDto, SecurityHelper.getUserName());
        if (id != null) {
            logger.info("Period was added:" + periodDto);
            return ResponseEntity.ok(id);
        } else {
            logger.warn("Bad request:" + periodDto);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updatePeriod")
    public ResponseEntity<PeriodDto> updatePeriod(@RequestBody PeriodDto periodDto) {
        boolean result = periodService.updatePeriodByEmail(periodDto, SecurityHelper.getUserName());
        if (result) {
            logger.info("Period was updated:" + periodDto);
            return ResponseEntity.ok(periodDto);
        } else {
            logger.warn("Bad request:" + periodDto);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletePeriodById")
    public ResponseEntity<PeriodDto> deletePeriodById(@RequestParam String id) {
        logger.info("Period was deleted:id=" + id);
        periodService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/getPeriods")
    public ResponseEntity<List<PeriodDto>> getPeriods() {
        String id = userService.getIdByEmail(SecurityHelper.getUserName());
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
