package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.UserDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * This controller interacts with database.
 */
@RestController
@RequestMapping("/daysoff/debug")
public class Controller {

    private Logger logger = Logger.getLogger(getClass());
    private UserService userService;
    private PeriodService periodService;

    @Autowired
    public void init(final UserService userService, final PeriodService periodService) {
        this.userService = userService;
        this.periodService = periodService;
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDto> getUser(@RequestParam int id) {
        try {
            UserDto userDto = userService.getById(id);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("user with id " + id + " is missing");
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = userService.getAll();
        if (!userDtos.isEmpty()) {
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        } else {
            logger.warn("Database is empty. Returned null");
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addPeriod")
    public ResponseEntity<String> addPeriod(@RequestBody PeriodDto periodDto) {
        int userId = periodDto.getUserId();
        try {
            periodService.save(periodDto);
            logger.warn("Custom period added to user with id: " + userId);
            return new ResponseEntity<>("Custom period added to user with id " + userId, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Please, type date correctly. Example: 2017-12-30", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.warn("Can't find user with id: " + userId);
            return new ResponseEntity<>("Can't find user with id: " + userId, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam int id) {
        try {
            periodService.deleteAllByUserId(id);
            userService.deleteById(id);
            logger.info("user with id " + id + " removed.");
            return new ResponseEntity<>("user with id " + id + " deleted", HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("user with id " + id + " is missing.");
            return new ResponseEntity<>("can't find user with id " + id, HttpStatus.BAD_REQUEST);
        }
    }

}
