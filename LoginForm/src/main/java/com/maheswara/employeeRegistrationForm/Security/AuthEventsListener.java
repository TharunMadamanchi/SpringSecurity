package com.maheswara.employeeRegistrationForm.Security;
import com.maheswara.employeeRegistrationForm.Service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthEventsListener {

    private final UserService userService;

    public AuthEventsListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent e) {
        String username = e.getAuthentication().getName();
        HttpServletRequest req = currentRequest();
        String ip = (req != null) ? req.getRemoteAddr() : "N/A";
        String ua = (req != null) ? req.getHeader("User-Agent") : "N/A";

        userService.loginSucceeded(username);

        System.out.printf("AUDIT: LOGIN SUCCESS user=%s ip=%s agent=%s%n", username, ip, ua);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent e) {
        String username = (e.getAuthentication() != null) ? e.getAuthentication().getName() : "UNKNOWN";
        HttpServletRequest req = currentRequest();
        String ip = (req != null) ? req.getRemoteAddr() : "N/A";
        String ua = (req != null) ? req.getHeader("User-Agent") : "N/A";

        userService.loginFailed(username);

        System.out.printf("AUDIT: LOGIN FAILURE user=%s ip=%s agent=%s reason=%s%n",
                username, ip, ua, e.getException().getClass().getSimpleName());
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }
}

