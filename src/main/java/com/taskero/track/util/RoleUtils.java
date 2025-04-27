package com.taskero.track.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public final class RoleUtils {
    private RoleUtils() {} // Prevent instantiation

    public static boolean isAdmin(Authentication auth) {
        return hasRole(auth, "ADMIN");
    }

    public static boolean isManager(Authentication auth) {
        return hasRole(auth, "MANAGER");
    }

    public static boolean isContributor(Authentication auth) {
        return hasRole(auth, "CONTRIBUTOR");
    }

    private static boolean hasRole(Authentication auth, String role) {
        if (auth == null) return false;
        return auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(authRole -> authRole.equals("ROLE_" + role));
    }
}
