package ru.mtsteta.courses.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.mtsteta.courses.security.filter.CookieRequestFilter;
import ru.mtsteta.courses.security.filter.OAuth2RequestFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Configuration
    @RequiredArgsConstructor
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final OAuth2RequestFilter oAuth2RequestFilter;
        private final CookieRequestFilter cookieRequestFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/course")
                    .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/course")
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("jwt");
            http
                    .addFilterBefore(oAuth2RequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(cookieRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}
