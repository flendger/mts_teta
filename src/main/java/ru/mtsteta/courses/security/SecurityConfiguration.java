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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Configuration
    @RequiredArgsConstructor
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final OAuth2RequestFilter oAuth2RequestFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/**").permitAll()
//                    .and()
//                    .formLogin()
//                    .defaultSuccessUrl("/course")
//                    .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/access_denied");
            http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/course")
                    .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/course");
            http.addFilterBefore(oAuth2RequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
}
