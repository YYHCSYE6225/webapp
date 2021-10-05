package edu.neu.coe.csye6225.webapp.controller;

import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
        User user=userService.addUser(userVO);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/self")
    public ResponseEntity updateUser(@RequestBody UserVO user){

        return new ResponseEntity(HttpStatus.OK);
    }
//
//    @GetMapping(value = "self")
//    public ResponseEntity getUser(){
//
//    }
}
