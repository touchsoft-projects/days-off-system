package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import by.touchsoft.office.daysoffsystem.web.controllers.utils.validation.BindingResultUtil;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Void> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> bindingErrors = BindingResultUtil.getErrors(bindingResult);
            logger.error("Validation error:" + bindingErrors.toString());
            return ResponseEntity.badRequest().build();
        } else {
            userService.addUser(userDto);
            logger.info("User was added:" + userDto);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/addUsers")
    public ResponseEntity<Void> addUsers(@RequestBody @Valid ArrayList<UserDto> userDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> bindingErrors = BindingResultUtil.getErrors(bindingResult);
            logger.error("Validation error:" + bindingErrors.toString());
            return ResponseEntity.badRequest().build();
        } else {
            for (UserDto userDto : userDtos) {
                userService.addUser(userDto);
                logger.info("User was added:" + userDto);
            }
            return ResponseEntity.ok().build();
        }
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

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userEntities = userService.getAll();
        return ResponseEntity.ok(userEntities);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> bindingErrors = BindingResultUtil.getErrors(bindingResult);
            logger.error("Validation error:" + bindingErrors.toString());
            return ResponseEntity.badRequest().build();
        } else {
            userService.updateUser(userDto);
            logger.info("User was updated:" + userDto);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/updateUsers")
    public ResponseEntity<Void> updateUsers(@RequestBody ArrayList<UserDto> userDtos) {
        for (UserDto userDto : userDtos) {
            userService.updateUser(userDto);
            logger.info("User was updated:" + userDto);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UserPasswordDto userPasswordDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> bindingErrors = BindingResultUtil.getErrors(bindingResult);
            logger.error("Validation error:" + bindingErrors.toString());
            return ResponseEntity.badRequest().build();
        } else {
            boolean result = userService.updateUserPassword(userPasswordDto);
            if (result) {
                logger.info("Password was updated to " + userPasswordDto.getEmail());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @GetMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam String id) {
        userService.deleteById(id);
        logger.info("user with id " + id + " removed.");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteUsers")
    public ResponseEntity<Void> deleteUsers(@RequestParam String[] idArray) {
        for (String id : idArray) {
            userService.deleteById(id);
            logger.info("user with id " + id + " removed.");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addPeriod")
    public ResponseEntity<Void> addPeriod(@RequestBody PeriodDto periodDto) {
        periodService.addAnyPeriod(periodDto);
        logger.info("Period was added:" + periodDto.toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addPeriods")
    public ResponseEntity<Void> addPeriods(@RequestBody ArrayList<PeriodDto> periodDtos) {
        for (PeriodDto periodDto : periodDtos) {
            periodService.addAnyPeriod(periodDto);
            logger.info("Period was added:" + periodDto.toString());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updatePeriod")
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

    @PostMapping("/deletePeriodById")
    public ResponseEntity<Void> deletePeriodById(@RequestParam String id) {
        periodService.deleteById(id);
        logger.info("Period was deleted:id=" + id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deletePeriodByIds")
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
