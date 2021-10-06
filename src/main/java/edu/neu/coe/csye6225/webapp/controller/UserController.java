package edu.neu.coe.csye6225.webapp.controller;

import edu.neu.coe.csye6225.webapp.exception.UserExistException;
import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuhan Yang
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Resource
    UserService userService;
    @PostMapping(value = "")
    public ResponseEntity<User> addUser(@RequestBody UserVO userVO){
        if(userVO.getUsername()==null||userVO.getUsername().isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        User user= null;
        try {
            user = userService.addUser(userVO);
        } catch (UserExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/self")
    public ResponseEntity updateUser(@RequestBody UserVO user,HttpServletRequest request){
        if(!userService.verifyUsername(request,user.getUsername()))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "self")
    public ResponseEntity<User> getUser(HttpServletRequest request){
        User user=userService.getUserSelf(request);
        return new ResponseEntity(user,HttpStatus.OK);
    }
}
