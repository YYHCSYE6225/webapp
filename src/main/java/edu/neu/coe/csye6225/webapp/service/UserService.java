package edu.neu.coe.csye6225.webapp.service;

import edu.neu.coe.csye6225.webapp.exception.UserExistException;
import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.exception.UsernameException;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public User addUser(UserVO uservo) throws UserExistException, UsernameException;
    public void updateUser(UserVO userVO);
    public User getUserSelf(HttpServletRequest request);
    public boolean verifyUsername(HttpServletRequest request,String username);
}
