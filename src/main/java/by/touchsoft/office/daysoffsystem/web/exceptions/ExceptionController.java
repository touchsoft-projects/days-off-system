package by.touchsoft.office.daysoffsystem.web.exceptions;

import by.touchsoft.office.daysoffsystem.web.exceptions.custom.IncorrectInputException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is used to catch and handle
 * {@link IncorrectInputException} exception.
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IncorrectInputException.class)
    public ResponseEntity<ExceptionMessage> handleError(Exception ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(ex.getMessage(), "Some fields are entered incorrectly");
        return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
