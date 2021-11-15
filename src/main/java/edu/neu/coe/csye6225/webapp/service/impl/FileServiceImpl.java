package edu.neu.coe.csye6225.webapp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
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
    private static final StatsDClient statsd = new NonBlockingStatsDClient("", "127.0.0.1", 8125);
    @Resource
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
//    private String bucketName="yyh-newtest-bucket";

    @Override
    public String uploadFile(File file,String userId) {
        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,userId+"/"+file.getName(),file);
        long startTime=System.currentTimeMillis();
        amazonS3.putObject(putObjectRequest);
        statsd.recordExecutionTime("S3UploadFile",System.currentTimeMillis()-startTime);
        return bucketName+"/"+userId+"/"+file.getName();
    }

    @Override
    public void deleteFile(String key) {
        long startTime=System.currentTimeMillis();
        amazonS3.deleteObject(bucketName,key);
        statsd.recordExecutionTime("S3DeleteFile",System.currentTimeMillis()-startTime);
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
