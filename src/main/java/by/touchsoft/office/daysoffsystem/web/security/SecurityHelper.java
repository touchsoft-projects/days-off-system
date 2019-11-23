package by.touchsoft.office.daysoffsystem.web.security;

import by.touchsoft.office.daysoffsystem.web.security.authorities.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityHelper {

    public static Authentication getUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserName() {
        Authentication userAuth = getUserAuthentication();
        if (userAuth != null) {
            return userAuth.getName();
        }
        return null;
    }

    public static boolean hasRoles(Role role) {
        Authentication userAuth = getUserAuthentication();
        if (userAuth != null) {
            Collection<? extends GrantedAuthority> authorities = userAuth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (role.name().equals(authority.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }
}
