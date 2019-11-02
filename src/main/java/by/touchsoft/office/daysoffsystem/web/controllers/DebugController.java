package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.converters.UserConverter;
import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.PeriodEntity;
import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import by.touchsoft.office.daysoffsystem.enumerations.PeriodType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This debugging controller is used to test features and interact with database.
 */
@RestController
@RequestMapping("/daysoff")
public class DebugController {

    private Logger logger = Logger.getLogger(getClass());
    private UserService userService;
    private PeriodService periodService;
    private UserConverter userConverter;

    @Autowired
    public void init(final UserService userService, final PeriodService periodService,
                     final UserConverter userConverter) {
        this.userService = userService;
        this.periodService = periodService;
        this.userConverter = userConverter;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userConverter.convertToEntity(userDto);
        userService.save(userEntity);
        logger.warn("Custom user added");
        return new ResponseEntity<>("Custom user added", HttpStatus.OK);
    }

    @PostMapping("/addRandomUser")
    public ResponseEntity<String> addRandomUser() {
        UserEntity userEntity = new UserEntity();
        String randomValue = String.valueOf(Math.random()).substring(2, 5);

        userEntity.setFirstName("first" + randomValue);
        userEntity.setSecondName("second" + randomValue);
        userEntity.setLastName("last" + randomValue);
        userEntity.setPassportId("PID" + randomValue + "000");
        userEntity.setEmail("user" + randomValue + "@email.com");
        userService.save(userEntity);

        logger.info("Random user added");
        return new ResponseEntity<>("random user " + randomValue + " added", HttpStatus.OK);
    }

    @PostMapping("/addRandomPeriod")
    public ResponseEntity<String> addRandomPeriod(@RequestParam int id) {
        try {
            PeriodEntity periodEntity = new PeriodEntity();
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2019, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

            periodEntity.setUserId(id);
            periodEntity.setStartDate(randomDate);
            periodEntity.setEndDate(randomDate.plusDays(1).plusMonths(1).plusYears(1));
            periodEntity.setPeriodType(
                    Math.random() < 0.5 ?
                            PeriodType.DAY_OFF : PeriodType.VACATION);
            periodService.save(periodEntity);

            logger.warn("Random period added to user with id: " + id);
            return new ResponseEntity<>("Random period added to user with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("Can't find user with id: " + id);
            return new ResponseEntity<>("Can't find user with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }

}
