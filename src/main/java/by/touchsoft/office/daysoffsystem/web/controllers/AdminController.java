package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import by.touchsoft.office.daysoffsystem.web.controllers.utils.validation.PeriodValidation;
import by.touchsoft.office.daysoffsystem.web.controllers.utils.validation.UserValidation;
import by.touchsoft.office.daysoffsystem.web.exceptions.custom.IncorrectInputException;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @PutMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto) throws IncorrectInputException {
        ArrayList<String> errors = UserValidation.getErrors(userDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            userService.addUser(userDto);
            logger.info("User was added:" + userDto);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/addUsers")
    public ResponseEntity<Void> addUsers(@RequestBody ArrayList<UserDto> userDtos) throws IncorrectInputException {
        ArrayList<String> errors = UserValidation.getErrors(userDtos);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            for (UserDto userDto : userDtos) {
                userService.addUser(userDto);
                logger.info("User was added:" + userDto);
            }
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userEntities = userService.getAll();
        return ResponseEntity.ok(userEntities);
    }

    @GetMapping("/getUsersById")
    public ResponseEntity<List<UserDto>> getUsersById(@RequestParam String[] idArray) {
        List<UserDto> result = ImmutableList.of();
        for (String id : idArray) {
            UserDto userDto = userService.getUserById(id);
            if (userDto != null) {
                result.add(userDto);
            }
        }
        return ResponseEntity.ok(result);
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

    @PutMapping("/updateUser")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) throws IncorrectInputException {
        ArrayList<String> errors = UserValidation.getErrors(userDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            userService.updateUser(userDto);
            logger.info("User was updated:" + userDto);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/updateUsers")
    public ResponseEntity<Void> updateUsers(@RequestBody ArrayList<UserDto> userDtos) {
        for (UserDto userDto : userDtos) {
            userService.updateUser(userDto);
            logger.info("User was updated: " + userDto);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(@RequestBody UserPasswordDto userPasswordDto) throws IncorrectInputException {
        ArrayList<String> errors = UserValidation.getErrors(userPasswordDto);
        if (errors != null) {
            throw new IncorrectInputException("Validesfation error", errors);
        } else {
            boolean result = userService.updateUserPassword(userPasswordDto);
            if (result) {
                logger.info("Password was updated to " + userPasswordDto.getEmail());
                return ResponseEntity.ok().build();
            } else {
                logger.info("Unable to update password of user with email " + userPasswordDto.getEmail());
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<Void> deleteUsers(@RequestParam String[] idArray) {
        for (String id : idArray) {
            userService.deleteById(id);
            logger.info("User was deleted:id=" + id);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteUserById")
    public ResponseEntity<Void> deleteUser(@RequestParam String id) {
        userService.deleteById(id);
        logger.info("User was deleted:id=" + id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/addPeriod")
    public ResponseEntity<Void> addPeriod(@RequestBody PeriodDto periodDto) throws IncorrectInputException {
        ArrayList<String> errors = PeriodValidation.getErrors(periodDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        }
        String id = periodService.addAnyPeriod(periodDto);
        if (id != null) {
            logger.info("Period was added:" + periodDto.toString());
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Bad request:" + periodDto);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/addPeriods")
    public ResponseEntity<Void> addPeriods(@RequestBody ArrayList<PeriodDto> periodDtos) throws IncorrectInputException {
        ArrayList<String> errors = PeriodValidation.getErrors(periodDtos);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            for (PeriodDto periodDto : periodDtos) {
                periodService.addAnyPeriod(periodDto);
                logger.info("Period was added:" + periodDto.toString());
            }
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/getPeriodsByUserId")
    public ResponseEntity<List<PeriodDto>> getPeriodsByUserId(@RequestParam String id) {
        List<PeriodDto> periodDtos;
        if (id != null) {
            periodDtos = periodService.getByUserId(id);
            return ResponseEntity.ok(periodDtos);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updatePeriod")
    public ResponseEntity<Void> updatePeriod(@RequestBody PeriodDto periodDto) throws IncorrectInputException {
        ArrayList<String> errors = PeriodValidation.getErrors(periodDto);
        if (errors != null) {
            throw new IncorrectInputException("Validation error", errors);
        } else {
            if (periodService.updateAnyPeriod(periodDto)) {
                logger.info("Period was updated:" + periodDto);
                return ResponseEntity.ok().build();
            } else {
                logger.warn("Bad request:" + periodDto);
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @DeleteMapping("/deletePeriodByIds")
    public ResponseEntity<Void> deletePeriodByIds(@RequestParam String[] arrayId) {
        for (String id : arrayId) {
            periodService.deleteById(id);
            logger.info("Period was deleted:id=" + id);
        }
        return ResponseEntity.ok().build();
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
