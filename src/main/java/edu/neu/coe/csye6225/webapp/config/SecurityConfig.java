package edu.neu.coe.csye6225.webapp.config;

import edu.neu.coe.csye6225.webapp.security.CustomAuthenticationProvider;
import edu.neu.coe.csye6225.webapp.security.JWTAuthenticationFilter;
import edu.neu.coe.csye6225.webapp.security.JWTLoginFilter;
import edu.neu.coe.csye6225.webapp.security.MyUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private MyUserService myUserService;

    @Override
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(new CustomAuthenticationProvider(myUserService,new BCryptPasswordEncoder()));
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/v1/user").permitAll()
                .antMatchers("/notes").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .csrf().disable();
    }
}
