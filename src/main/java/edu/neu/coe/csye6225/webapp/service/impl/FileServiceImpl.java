package edu.neu.coe.csye6225.webapp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
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
//    private String bucketName="yyh-newtest-bucket";

    @Override
    public String uploadFile(File file,String userId) {
        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,userId+"/"+file.getName(),file);
        amazonS3.putObject(putObjectRequest);
        return bucketName+"/"+userId+"/"+file.getName();
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
