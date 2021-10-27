package edu.neu.coe.csye6225.webapp.entity.vo;

import lombok.Data;

@Data
public class FileVO {
    String fileName;
    String id;
    String url;
    String uploadDate;
    String userId;

    public FileVO(String name, String id, String url, String uploadDate, String userId){
        this.fileName=name;
        this.id=id;
        this.url=url;
        this.uploadDate=uploadDate;
        this.userId=userId;
    }
}
