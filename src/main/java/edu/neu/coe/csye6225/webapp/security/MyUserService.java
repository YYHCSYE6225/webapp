package edu.neu.coe.csye6225.webapp.security;

import edu.neu.coe.csye6225.webapp.dao.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyUserService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity=userMapper.getUserEntityByUsername(username);
        return userEntity;
    }
}
