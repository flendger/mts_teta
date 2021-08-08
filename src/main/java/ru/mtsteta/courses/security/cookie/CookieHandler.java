package ru.mtsteta.courses.security.cookie;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.mtsteta.courses.utils.JwtTokenUtil;

import javax.servlet.http.Cookie;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CookieHandler {
    private final JwtTokenUtil jwtTokenUtil;

    public Cookie getNewUserCookie(Authentication authentication) {
        Cookie cookie = new Cookie("jwt", jwtTokenUtil.generateToken(authentication.getName()));
        cookie.setPath("/");
        return cookie;
    }

    public boolean cookieValid(Cookie cookie, Authentication authentication) {
        if (cookie == null || !cookie.getName().equals("jwt")) {
            return false;
        }

        String jwtToken = cookie.getValue();
        try {
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            Date expDate = jwtTokenUtil.getExpirationFromToken(jwtToken);
            return authentication.getName().equals(username) && expDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Cookie removeCookie() {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
