package edu.neu.coe.csye6225.webapp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Resource
    private AuthenticationManager authenticationManager;


    public JWTLoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        if(username==null){
            username="";
        }
        if(password==null){
            password="";
        }
        username=username.trim();
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String preToken=username+":"+password;
        String token= Base64.getEncoder().encodeToString(preToken.getBytes());
        response.addHeader("token","Yuhan "+token);
    }
}
