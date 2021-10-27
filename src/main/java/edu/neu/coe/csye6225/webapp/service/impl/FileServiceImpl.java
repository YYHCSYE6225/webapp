package edu.neu.coe.csye6225.webapp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import edu.neu.coe.csye6225.webapp.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
//    private String bucketName="yyh-test-bucket";
    @Override
    public boolean verifyFileAsImage(MultipartFile file) {
        if(file.isEmpty()) {
            System.out.println("File is null");
            return false;
        }
        String fileName=file.getOriginalFilename();
        if(!(fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".jpeg"))) {
           System.out.println("Wrong format");
            return false;
        }
        return true;
    }

    @Override
    public String uploadFile(MultipartFile file,String userId) {
        File uploadFile=convertMultiPartFileToFile(file);
        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,userId+"/"+file.getOriginalFilename(),uploadFile);
        amazonS3.putObject(putObjectRequest);
        uploadFile.delete();
        return bucketName+"/"+userId+"/"+file.getOriginalFilename();
    }

    @Override
    public void deleteFile(String key) {
        amazonS3.deleteObject(bucketName,key);
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

}
