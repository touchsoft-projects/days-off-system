package by.touchsoft.office.daysoffsystem.db.repository.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPasswordDto {

    @Email(message = "Enter valid email")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    @Size(min=6, message = "Password must be longer than 6 symbols")
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
