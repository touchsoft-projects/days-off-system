package by.touchsoft.office.daysoffsystem.db.repository.dto;

import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;

/**
 * This class is used to interact with view and hides unwanted data of
 * {@link UserEntity} entity to work safely with password.
 */
public class UserPasswordDto {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
