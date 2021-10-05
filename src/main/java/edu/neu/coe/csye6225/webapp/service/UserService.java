package edu.neu.coe.csye6225.webapp.service;

import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;

public interface UserService {
    public User addUser(UserVO uservo);
}
