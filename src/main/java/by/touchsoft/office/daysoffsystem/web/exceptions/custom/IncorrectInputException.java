package by.touchsoft.office.daysoffsystem.web.exceptions.custom;

import org.apache.log4j.Logger;

import java.util.ArrayList;

public class IncorrectInputException extends Throwable {

    private Logger logger = Logger.getLogger(getClass());

    public IncorrectInputException(String errorMessage) {
        super(errorMessage);
    }

    public IncorrectInputException(final String errorMessage, final ArrayList<String> errors) {
        super(errorMessage);
        for (String error : errors) {
            logger.error("Validation error:" + error);
        }
    }
}
