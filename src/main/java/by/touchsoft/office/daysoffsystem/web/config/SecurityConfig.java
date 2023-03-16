package by.touchsoft.office.daysoffsystem.web.config;

import by.touchsoft.office.daysoffsystem.web.security.authorities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * This class is used to configure endpoints and authentication.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] LIST_ENDPOINTS_FOR_ADMIN = {"/api/v1/admin/*", "/home"};
    private static final String[] LIST_ENDPOINTS_FOR_USER = {"/api/v1/user/*", "/home"};
    private static final String[] LIST_ENDPOINTS_FOR_ANONYM = {
            "/",
            "/healthCheck"
    };
    private static final String USER_BY_EMAIL_QUERY = "SELECT email, passport_id as password, enabled FROM users WHERE email = ?";
    private static final String USER_ROLES_BY_EMAIL_QUERY = "SELECT email, role_name FROM users u"
            + " INNER JOIN user_roles ur ON u.id = ur.user_id"
            + " WHERE email = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_BY_EMAIL_QUERY)
                .authoritiesByUsernameQuery(USER_ROLES_BY_EMAIL_QUERY)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LIST_ENDPOINTS_FOR_ANONYM)
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .authorizeRequests()
                .antMatchers(LIST_ENDPOINTS_FOR_USER)
                .hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_EMPLOYEE.name())
                .and()
                .authorizeRequests()
                .antMatchers(LIST_ENDPOINTS_FOR_ADMIN)
                .hasAnyAuthority(Role.ROLE_ADMIN.name())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/appLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * TODO use {@link BCryptPasswordEncoder} if passwords will be managed by this application
         * TODO specify strength and secureRandom for {@link BCryptPasswordEncoder}
         */
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
                return passwordEncoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null || encodedPassword.length() == 0) {
                    return false;
                }
                return Objects.equals(rawPassword, encodedPassword);
            }
        };
    }
}
