package edu.neu.coe.csye6225.webapp.controller;

import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import edu.neu.coe.csye6225.webapp.exception.UserExistException;
import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.exception.UsernameException;
import edu.neu.coe.csye6225.webapp.service.FileService;
import edu.neu.coe.csye6225.webapp.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yuhan Yang
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    FileService fileService;
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
        } catch (UsernameException e) {
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
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/self")
    public ResponseEntity<User> getUser(HttpServletRequest request){
        User user=userService.getUserSelf(request);
        return new ResponseEntity(user,HttpStatus.OK);
    }

    @PostMapping(value = "/self/pic")
    public ResponseEntity<FileVO> addPic(HttpServletRequest request) {
        File file=new File("Pic.jpg");
        try {
            InputStream inputStream=request.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(file==null)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        FileVO fileVO=userService.addPic(file,request);
        return new ResponseEntity<FileVO>(fileVO,HttpStatus.CREATED);
    }

    @GetMapping(value = "/self/pic")
    public ResponseEntity<FileVO> getPic(HttpServletRequest request){
        FileVO fileVO=userService.getPic(request);
        if(fileVO==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<FileVO>(fileVO,HttpStatus.OK);
    }

    @DeleteMapping(value = "/self/pic")
    public ResponseEntity deletePic(HttpServletRequest request){
        FileVO fileVO=userService.getPic(request);
        if (fileVO==null){
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        userService.deletePic(request);
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

}
