package edu.neu.coe.csye6225.webapp.dao;

import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FileMapper {

    @Insert("INSERT INTO userPic(file_name,id,url,upload_date,user_id)VALUES (#{fileName},#{id},#{url},#{uploadDate},#{userId})")
    public void uploadFile(FileVO fileVO);

    @Results({
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "id", column = "id"),
            @Result(property = "url", column = "url"),
            @Result(property = "uploadDate", column = "upload_date"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT * from userPic WHERE user_id=#{userId}")
    public FileVO getFile(String userId);

    @Delete("DELETE from userPic WHERE user_id=#{userId}")
    public void deleteFile(String userId);
}
