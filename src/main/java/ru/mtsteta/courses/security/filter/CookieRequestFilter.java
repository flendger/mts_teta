package ru.mtsteta.courses.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mtsteta.courses.security.cookie.CookieHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieRequestFilter extends OncePerRequestFilter {
    private final CookieHandler cookieHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            //Проверяет, не протухла ли куки
            if (request.getCookies() != null) {
                Optional<Cookie> requestCookieOptional = Arrays.stream(request.getCookies())
                        .filter(cookie -> cookie.getName().equals("jwt"))
                        .findFirst();

                if (requestCookieOptional.isPresent() && !cookieHandler.cookieValid(requestCookieOptional.get(), authentication)) {
                    response.addCookie(cookieHandler.removeCookie());
                    response.sendRedirect(request.getContextPath() + "/logout");
                    authentication = null;
                }
            }
        }

        //Если куки не протухла, отдаем новую с новым сроком
        if (authentication != null && authentication.isAuthenticated()) {
            response.addCookie(cookieHandler.getNewUserCookie(authentication));
        }

        filterChain.doFilter(request, response);
    }
}
