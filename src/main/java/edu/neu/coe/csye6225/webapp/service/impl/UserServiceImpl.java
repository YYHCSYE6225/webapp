package edu.neu.coe.csye6225.webapp.service.impl;

import edu.neu.coe.csye6225.webapp.dao.FileMapper;
import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import edu.neu.coe.csye6225.webapp.exception.UserExistException;
import edu.neu.coe.csye6225.webapp.dao.UserMapper;
import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.exception.UsernameException;
import edu.neu.coe.csye6225.webapp.security.EncodeUtil;
import edu.neu.coe.csye6225.webapp.service.FileService;
import edu.neu.coe.csye6225.webapp.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private FileService fileService;
    @Resource
    private FileMapper fileMapper;
    @Override
    public User addUser(UserVO userVO) throws UserExistException, UsernameException {
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(userVO.getUsername());
        if(!m.matches()){
            throw new UsernameException();
        }
        User user=userMapper.getUserByUsername(userVO.getUsername());
        if(user!=null)
            throw new UserExistException();
        else {
            user=new User();
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

    @Override
    public User getUserSelf(HttpServletRequest request) {
        String username=getUsernameFromRequest(request);
        User user=userMapper.getUserByUsername(username);
        user.setPassword(null);
        return user;
    }

    @Override
    public boolean verifyUsername(HttpServletRequest request, String username) {
        String requestUsername=getUsernameFromRequest(request);
        return requestUsername.equals(username);
    }

    @Override
    public FileVO addPic(File file, HttpServletRequest request) {
        User user=getUserSelf(request);
        String uuid=UUID.randomUUID().toString();
        //get the current date
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String date=dateFormat.format(new Date());
        //upload file
        String url=fileService.uploadFile(file,user.getId());
        FileVO fileVO=new FileVO(file.getName(),uuid,url,date,user.getId());

        //If the user already have a pic, delete it first and then upload it.
        FileVO verifyFile=fileMapper.getFile(user.getId());
        if(verifyFile==null) {
            fileMapper.uploadFile(fileVO);
        }
        else {
            deletePic(request);
            fileService.uploadFile(file,user.getId());
            fileMapper.uploadFile(fileVO);
        }
        return fileVO;
    }

    private String getUsernameFromRequest(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        String authToken=token.replace("Basic ","");
        String preToken= new String(Base64.getDecoder().decode(authToken));
        String[]info=preToken.split(":");
        return info[0];
    }

    @Override
    public FileVO getPic(HttpServletRequest request){
        User user=getUserSelf(request);
        FileVO fileVO=fileMapper.getFile(user.getId());
        return fileVO;
    }
    @Override
    public void deletePic(HttpServletRequest request){
        User user=getUserSelf(request);
        FileVO fileVO=getPic(request);
        fileMapper.deleteFile(user.getId());
        fileService.deleteFile(user.getId()+"/"+fileVO.getFileName());
    }

}
