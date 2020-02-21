package by.touchsoft.office.daysoffsystem.db.repository.dto;

import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import by.touchsoft.office.daysoffsystem.web.security.authorities.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * This class is used to interact with view and hides unwanted data of
 * {@link UserEntity} entity.
 */
public class UserDto {

    private String id;

    @NotBlank(message = "Field cannot be empty")
    private String firstName;

    @NotBlank(message = "Field cannot be empty")
    private String secondName;
    private String lastName;

    @NotBlank(message = "Field cannot be empty")
    private String passportId;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    @Size(min=6, message = "Password must be longer than 6 symbols")
    private String password;
    private boolean enabled;

    @NotBlank(message = "Field cannot be empty")
    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(final String passportId) {
        this.passportId = passportId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        String text = "UserDto [id=%s, firstName=%s, secondName=%s, lastName=%s, passportId=%s, email=%s, enabled=%s, roles=%s]";
        return String.format(
                text,
                getId(),
                getFirstName(),
                getSecondName(),
                getLastName(),
                getPassportId(),
                getEmail(),
                isEnabled(),
                getRoles()
        );
    }
}
