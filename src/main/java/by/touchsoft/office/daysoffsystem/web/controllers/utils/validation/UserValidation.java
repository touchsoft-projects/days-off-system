package by.touchsoft.office.daysoffsystem.web.controllers.utils.validation;

import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;

import java.util.ArrayList;
import java.util.List;

public class UserValidation {

    public static ArrayList<String> getErrors(UserDto userDto) {
        ArrayList<String> errors = new ArrayList<>();
        String errorMessage = validateUserDto(userDto);
        if (errorMessage != null) {
            errors.add(errorMessage);
        }
        return errors.size() != 0 ? errors : null;
    }

    public static ArrayList<String> getErrors(List<UserDto> userDtos) {
        ArrayList<String> errors = new ArrayList<>();
        for (UserDto userDto : userDtos) {
            String errorMessage = validateUserDto(userDto);
            if (errorMessage != null) {
                errors.add(errorMessage);
            }
        }
        return errors.size() != 0 ? errors : null;
    }

    public static ArrayList<String> getErrors(final UserPasswordDto userPasswordDto) {
        ArrayList<String> errors = new ArrayList<>();
        String errorMessage = validateUserPasswordDto(userPasswordDto);
        if (errorMessage != null) {
            errors.add(errorMessage);
        }
        return errors.size() != 0 ? errors : null;
    }

    private static String validateUserPasswordDto(final UserPasswordDto userPasswordDto) {
        String errorMessages = "";
        if (userPasswordDto.getPassword().length() < 6) {
            errorMessages += "Password length less than 6; ";
        }
        if (!errorMessages.isEmpty()) {
            return "User with email '" + userPasswordDto.getEmail() + "' contains this errors: " + errorMessages + "\r\n";
        }
        return null;
    }

    private static String validateUserDto(UserDto userDto) {
        String errorMessages = "";
        if (userDto.getPassword().length() < 6) {
            errorMessages += "Password length less than 6; ";
        }
        if (userDto.getPassportId().isBlank()) {
            errorMessages += "PassportId is missing; ";
        }
        if (userDto.getFirstName().isBlank()) {
            errorMessages += "First name is missing; ";
        }
        if (userDto.getLastName().isBlank()) {
            errorMessages += "Last name is missing; ";
        }
        if (userDto.getEmail().isBlank()) {
            errorMessages += "Email is missing; ";
        }
        if (!errorMessages.isEmpty()) {
            return "User with id '" + userDto.getId() + "' contains this errors: " + errorMessages + "\r\n";
        }
        return null;
    }

}
