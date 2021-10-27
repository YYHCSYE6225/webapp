package edu.neu.coe.csye6225.webapp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){super(authenticationManager);}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token=request.getHeader("Authorization");
        if(token!=null&&token.startsWith("Basic ")){
            String authToken=token.replace("Basic ","");
            String preToken= new String(Base64.getDecoder().decode(authToken));
            if(preToken==null)
                System.out.println("token is null");
            else {
                String[]info=preToken.split(":");
                UserEntity user=new UserEntity();
                user.setUsername(info[0]);
                user.setPassword(info[1]);
                UsernamePasswordAuthenticationToken authentication;
                authentication=new UsernamePasswordAuthenticationToken(user.getUsername(),null);
                authentication.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request,response);
    }
}
