package gr.app.springbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import gr.app.springbackend.service.UserServiceJWT;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
    
    @Autowired
    UserServiceJWT userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.PUT, "/auth").permitAll()
            .antMatchers(HttpMethod.GET, "/auth").hasAuthority("superuser")
            .anyRequest().authenticated()
            .and().apply(new JwtConfigurer(userService));
    }
}