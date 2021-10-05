package edu.neu.coe.csye6225.webapp.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Collection;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService=userDetailsService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username;
        String inputPassword;
        if(authentication.getDetails()==null){
         username=authentication.getPrincipal().toString();
         inputPassword=authentication.getCredentials().toString();
        }
        else{
            UserEntity user=(UserEntity) authentication.getDetails();
            username=user.getUsername();
            inputPassword=user.getPassword();
        }

        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

        //需要改成加密对比
        String databasePassword=userDetails.getPassword();
        if(!EncodeUtil.matches(inputPassword,databasePassword))
            throw new BadCredentialsException("Wrong password");

        Collection<?extends GrantedAuthority>authorities;
        authorities=userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(username,inputPassword,authorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
