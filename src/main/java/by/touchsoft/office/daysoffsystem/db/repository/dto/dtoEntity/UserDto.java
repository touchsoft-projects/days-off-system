package by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity;

import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;

import java.util.List;

/**
 * This class is used to interact with view and hides unwanted data of {@link UserEntity} entity.
 */
public class UserDto {

    private int id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String passportId;
    private String email;
    private List<PeriodDto> periods;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
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

    public List<PeriodDto> getPeriods() {
        return periods;
    }

    public void setPeriods(final List<PeriodDto> periods) {
        this.periods = periods;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}
