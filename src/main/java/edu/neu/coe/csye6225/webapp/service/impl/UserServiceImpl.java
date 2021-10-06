package edu.neu.coe.csye6225.webapp.service.impl;

import edu.neu.coe.csye6225.webapp.exception.UserExistException;
import edu.neu.coe.csye6225.webapp.dao.UserMapper;
import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.security.EncodeUtil;
import edu.neu.coe.csye6225.webapp.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User addUser(UserVO userVO) throws UserExistException {
        User user=userMapper.getUserByUsername(userVO.getUsername());
        if(user!=null)
            throw new UserExistException();
        else {
        String uuid=UUID.randomUUID().toString();
        user.setId(uuid);
        user.setPassword(EncodeUtil.encode(userVO.getPassword()));
        user.setUsername(userVO.getUsername());
        user.setFirstName(userVO.getFirstName());
        user.setLastName(userVO.getLastName());
        userMapper.addUser(user);
        user=userMapper.getUserById(uuid);
        return user;
        }
    }

    @Override
    public void updateUser(UserVO userVO) {
        userVO.setPassword(EncodeUtil.encode(userVO.getPassword()));
        userMapper.updateUser(userVO);
    }

}
