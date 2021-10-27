package edu.neu.coe.csye6225.webapp.service;

import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    public String uploadFile(File file,String userID);
    public void deleteFile(String key);
}
