package by.touchsoft.office.daysoffsystem.web.exceptions;

/**
 * This class is used to send exception stack trace
 * with custom added details.
 */
public class ExceptionMessage {
    private String message;
    private String details;

    ExceptionMessage(final String message, final String details) {
        super();
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }
}
