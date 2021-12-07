package edu.neu.coe.csye6225.webapp.controller;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import edu.neu.coe.csye6225.webapp.exception.UserExistException;
import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.exception.UsernameException;
import edu.neu.coe.csye6225.webapp.service.FileService;
import edu.neu.coe.csye6225.webapp.service.UserService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger= LoggerFactory.getLogger(UserController.class);
    private static final StatsDClient statsd = new NonBlockingStatsDClient("", "127.0.0.1", 8125);
    @Resource
    UserService userService;
    @PostMapping(value = "")
    public ResponseEntity<User> addUser(@RequestBody UserVO userVO){
        statsd.incrementCounter("TotalCreateUserCount");
        long startTime = System.currentTimeMillis();
        if(userVO.getUsername()==null||userVO.getUsername().isEmpty()){
            statsd.recordExecutionTime("CreateUser",System.currentTimeMillis()-startTime);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        User user= null;
        try {
            user = userService.addUser(userVO);
        } catch (UserExistException e) {
            e.printStackTrace();
            logger.warn(new ResponseEntity<>(null,HttpStatus.BAD_REQUEST).toString());
            statsd.recordExecutionTime("CreateUser",System.currentTimeMillis()-startTime);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        } catch (UsernameException e) {
            e.printStackTrace();
            logger.warn(new ResponseEntity<>(null,HttpStatus.BAD_REQUEST).toString());
            statsd.recordExecutionTime("CreateUser",System.currentTimeMillis()-startTime);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        user.setPassword(null);
        statsd.recordExecutionTime("CreateUser",System.currentTimeMillis()-startTime);
        logger.info(new ResponseEntity<>(user, HttpStatus.OK).toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/self")
    public ResponseEntity updateUser(@RequestBody UserVO user,HttpServletRequest request){
        if(!userService.checkVerify(request))
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        statsd.incrementCounter("TotalUpdateUserCount");
        long startTime=System.currentTimeMillis();
        if(!userService.verifyUsername(request,user.getUsername())){
            statsd.recordExecutionTime("UpdateUser",System.currentTimeMillis()-startTime);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(user);
        logger.info(new ResponseEntity(HttpStatus.NO_CONTENT).toString());
        statsd.recordExecutionTime("UpdateUser",System.currentTimeMillis()-startTime);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/self")
    public ResponseEntity<User> getUser(HttpServletRequest request){
        if(!userService.checkVerify(request))
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        statsd.incrementCounter("TotalGetUserCount");
        long startTime=System.currentTimeMillis();
        User user=userService.getUserSelf(request);
        logger.info(new ResponseEntity(user,HttpStatus.OK).toString());
        statsd.recordExecutionTime("GetUser",System.currentTimeMillis()-startTime);
        return new ResponseEntity(user,HttpStatus.OK);
    }

    @PostMapping(value = "/self/pic")
    public ResponseEntity<FileVO> addPic(HttpServletRequest request) {
        if(!userService.checkVerify(request))
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        statsd.incrementCounter("TotalUploadPicCount");
        long startTime=System.currentTimeMillis();
        File file=new File("Pic.jpg");
        try {
            InputStream inputStream=request.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(file==null) {
            statsd.recordExecutionTime("UploadUserPic",System.currentTimeMillis()-startTime);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        FileVO fileVO=userService.addPic(file,request);
        logger.info(new ResponseEntity<FileVO>(fileVO,HttpStatus.CREATED).toString());
        statsd.recordExecutionTime("UploadUserPic",System.currentTimeMillis()-startTime);
        return new ResponseEntity<FileVO>(fileVO,HttpStatus.CREATED);
    }

    @GetMapping(value = "/self/pic")
    public ResponseEntity<FileVO> getPic(HttpServletRequest request){
        if(!userService.checkVerify(request))
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        statsd.incrementCounter("TotalGetPicCount");
        long startTime=System.currentTimeMillis();
        FileVO fileVO=userService.getPic(request);
        if(fileVO==null){
            logger.warn(new ResponseEntity<>(null,HttpStatus.NOT_FOUND).toString());
            statsd.recordExecutionTime("GetUserPic",System.currentTimeMillis()-startTime);
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        logger.info(new ResponseEntity<FileVO>(fileVO,HttpStatus.OK).toString());
        statsd.recordExecutionTime("GetUserPic",System.currentTimeMillis()-startTime);
        return new ResponseEntity<FileVO>(fileVO,HttpStatus.OK);
    }

    @DeleteMapping(value = "/self/pic")
    public ResponseEntity deletePic(HttpServletRequest request){
        if(!userService.checkVerify(request))
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);
        statsd.incrementCounter("TotalDeletePicCount");
        long startTime=System.currentTimeMillis();
        FileVO fileVO=userService.getPic(request);
        if (fileVO==null){
            logger.warn(new ResponseEntity(null,HttpStatus.NOT_FOUND).toString());
            statsd.recordExecutionTime("DeleteUserPic",System.currentTimeMillis()-startTime);
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
        userService.deletePic(request);
        logger.info(new ResponseEntity(null,HttpStatus.NO_CONTENT).toString());
        statsd.recordExecutionTime("DeleteUserPic",System.currentTimeMillis()-startTime);
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/verifyUserEmail/{email}/{token}")
    public ResponseEntity verifyUserEmail(@PathVariable String email,@PathVariable String token){
        if(userService.verifyUserEmail(email,token)){
            return new ResponseEntity(null,HttpStatus.OK);
        }
        return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
    }

}
