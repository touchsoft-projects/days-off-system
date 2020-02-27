package by.touchsoft.office.daysoffsystem.web.controllers.utils.validation;

import by.touchsoft.office.daysoffsystem.db.repository.dto.PeriodDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PeriodValidation {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ArrayList<String> getErrors(PeriodDto periodDto) {
        ArrayList<String> errors = new ArrayList<>();
        String errorMessage = validatePeriodDto(periodDto);
        if (errorMessage != null) {
            errors.add(errorMessage);
        }
        return errors.size() != 0 ? errors : null;
    }

    public static ArrayList<String> getErrors(List<PeriodDto> periodDtos) {
        ArrayList<String> errors = new ArrayList<>();
        for (PeriodDto periodDto : periodDtos) {
            String errorMessage = validatePeriodDto(periodDto);
            if (errorMessage != null) {
                errors.add(errorMessage);
            }
        }
        return errors.size() != 0 ? errors : null;
    }

    private static String validatePeriodDto(PeriodDto periodDto) {
        String errorMessages = "";

        LocalDate endDate = LocalDate.parse(periodDto.getEndDate(), FORMATTER);
        LocalDate startDate = LocalDate.parse(periodDto.getStartDate(), FORMATTER);
        if (startDate.isAfter(endDate)) {
            errorMessages += "Start date is after End date";
        }
        if (!errorMessages.isEmpty()) {
            return "Period with id " + periodDto.getId() + "contains this errors: " + errorMessages + "\r\n";
        }
        return null;
    }

}
