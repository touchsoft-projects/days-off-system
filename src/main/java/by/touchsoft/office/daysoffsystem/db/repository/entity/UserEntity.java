package by.touchsoft.office.daysoffsystem.db.repository.entity;

import by.touchsoft.office.daysoffsystem.web.security.authorities.Role;
import org.springframework.lang.NonNull;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;

    @NonNull
    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "Field cannot be empty")
    private String firstName;

    @NonNull
    @Column(name = "second_name", nullable = false)
    @NotBlank(message = "Field cannot be empty")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "passport_id", nullable = false)
    @NotBlank(message = "Field cannot be empty")
    private String passportId;

    @Column(name = "email", nullable = false)
    @Email(message = "Enter valid email")
    @NotBlank(message = "Field cannot be empty")
    private String email;

    @NonNull
    @Column(name = "password")
    @Size(min=6, message = "Password must be longer than 6 symbols")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    @NotBlank(message = "Field cannot be empty")
    private Set<Role> roles = new HashSet<>();

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

    public void setLastName(final String lastName) {
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

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        UserEntity userEntity = (UserEntity) obj;
        return this.id == userEntity.id;
    }

    @Override
    public String toString() {
        String text = "UserEntity [id=%s, firstName=%s, secondName=%s, lastName=%s, passportId=%s, email=%s, enabled=%s]";
        return String.format(
                text,
                id,
                firstName,
                secondName,
                lastName,
                passportId,
                email,
                enabled
        );
    }

}
