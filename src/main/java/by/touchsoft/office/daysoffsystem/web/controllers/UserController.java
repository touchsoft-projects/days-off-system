package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import by.touchsoft.office.daysoffsystem.web.controllers.utils.validation.PeriodValidation;
import by.touchsoft.office.daysoffsystem.web.controllers.utils.validation.UserValidation;
import by.touchsoft.office.daysoffsystem.web.exceptions.custom.IncorrectInputException;
import by.touchsoft.office.daysoffsystem.web.security.SecurityHelper;
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

import java.util.ArrayList;
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
    public ResponseEntity<Void> updatePassword(@RequestBody UserPasswordDto userPasswordDto) throws IncorrectInputException {
        ArrayList<String> errors = UserValidation.getErrors(userPasswordDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            String principalUserName = SecurityHelper.getUserName();
            if (Objects.equals(principalUserName, userPasswordDto.getEmail())) {
                userService.updateUserPassword(userPasswordDto);
                logger.info("Password was updated to " + userPasswordDto.getEmail());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PostMapping("/addPeriod")
    public ResponseEntity<String> addPeriod(@RequestBody PeriodDto periodDto) {
        ArrayList<String> errors = PeriodValidation.getErrors(periodDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            String id = periodService.addPeriodByEmail(periodDto, SecurityHelper.getUserName());
            if (id != null) {
                logger.info("Period was added:" + periodDto);
                return ResponseEntity.ok(id);
            } else {
                logger.warn("Bad request:" + periodDto);
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PutMapping("/updatePeriod")
    public ResponseEntity<PeriodDto> updatePeriod(@RequestBody PeriodDto periodDto) {
        ArrayList<String> errors = PeriodValidation.getErrors(periodDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            if (periodService.updatePeriodByEmail(periodDto, SecurityHelper.getUserName())) {
                logger.info("Period was updated:" + periodDto);
                return ResponseEntity.ok(periodDto);
            } else {
                logger.warn("Bad request:" + periodDto);
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @DeleteMapping("/deletePeriodById")
    public ResponseEntity<PeriodDto> deletePeriodById(@RequestParam String id) {
        String principalUserName = SecurityHelper.getUserName();
        if (principalUserName != null) {
            UserDto userDto = userService.getUserByEmail(principalUserName);
            if (userDto != null) {
                periodService.deleteById(id);
                logger.info("Period was deleted:id=" + id);
            }
        }
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
