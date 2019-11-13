package by.touchsoft.office.daysoffsystem.web.controllers;

import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.UserDto;
import by.touchsoft.office.daysoffsystem.db.service.PeriodService;
import by.touchsoft.office.daysoffsystem.db.service.UserService;
import by.touchsoft.office.daysoffsystem.enumerations.PeriodType;
import by.touchsoft.office.daysoffsystem.web.WebInitializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
 * <p>
 * To enable, uncomment in {@link WebInitializer#onStartup}
 */
@Profile("dev")
@RestController
@RequestMapping("/daysoff/debug")
public class DebugController {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private PeriodService periodService;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
        logger.warn("Custom user added");
        return new ResponseEntity<>("Custom user added", HttpStatus.OK);
    }

    @PostMapping("/addRandomUser")
    public ResponseEntity<String> addRandomUser() {
        UserDto userDto = new UserDto();
        String randomValue = String.valueOf(Math.random()).substring(2, 5);

        userDto.setFirstName("first" + randomValue);
        userDto.setSecondName("second" + randomValue);
        userDto.setLastName("last" + randomValue);
        userDto.setPassportId("PID" + randomValue + "000");
        userDto.setEmail("user" + randomValue + "@email.com");

        userService.save(userDto);
        logger.info("Random user added");
        return new ResponseEntity<>("random user " + randomValue + " added", HttpStatus.OK);
    }

    @PostMapping("/addRandomPeriod")
    public ResponseEntity<String> addRandomPeriod(@RequestParam int id) {
        try {
            PeriodDto periodDto = new PeriodDto();
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2019, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

            periodDto.setUserId(id);
            periodDto.setStartDate(randomDate.toString());
            periodDto.setEndDate(randomDate.plusDays(1).plusMonths(1).plusYears(1).toString());
            periodDto.setPeriodType(
                    Math.random() < 0.5 ?
                            PeriodType.DAY_OFF : PeriodType.VACATION);
            periodService.save(periodDto);

            logger.warn("Random period added to user with id: " + id);
            return new ResponseEntity<>("Random period added to user with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("Can't find user with id: " + id);
            return new ResponseEntity<>("Can't find user with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }
}
