package edu.neu.coe.csye6225.webapp.service;

import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public boolean verifyFileAsImage(MultipartFile file);
    public String uploadFile(MultipartFile file,String userID);
    public void deleteFile(String key);
}
