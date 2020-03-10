package by.touchsoft.office.daysoffsystem.web.controllers.utils.validation;

import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to validate entered by user data of
 * {@link UserEntity} entity.
 */
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
        if (!errorMessages.isBlank()) {
            return "User with email '" + userPasswordDto.getEmail() + "' contains this errors: " + errorMessages + "\r\n";
        }
        return null;
    }

    private static String validateUserDto(UserDto userDto) {
        String errorMessages = "";
        String userId = userDto.getId();
        //Checking user's password
        if (userDto.getPassword().length() < 6) {
            errorMessages += "Password length less than 6; ";
        }
        //Checking user's passport id
        if (userDto.getPassportId().isBlank()) {
            errorMessages += "PassportId is missing; ";
        }
        //Checking user's first name
        if (userDto.getFirstName().isBlank()) {
            errorMessages += "First name is missing; ";
        }
        //Checking user's last name
        if (userDto.getLastName().isBlank()) {
            errorMessages += "Last name is missing; ";
        }
        //Checking user's email
        if (userDto.getEmail().isBlank()) {
            errorMessages += "Email is missing; ";
        } else {
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userDto.getEmail());
            if (!matcher.matches()) {
                errorMessages += "Email address " + userDto.getEmail() + " is invalid.";
            }
        }
        //Checking user's role
        if (userDto.getRoles().isEmpty()) {
            errorMessages += "Role is empty; ";
        }
        //Return value
        if (!errorMessages.isBlank()) {
            return "User with id '" +
                    (userId.isBlank() ? "UNREGISTERED" : userId)
                    + "' contains this errors: " + errorMessages + "\r\n";
        }
        return null;
    }

}
