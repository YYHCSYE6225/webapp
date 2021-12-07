package edu.neu.coe.csye6225.webapp.dao.primary;

import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PrimaryFileMapper {
    @Insert("INSERT INTO userPic(file_name,id,url,upload_date,user_id)VALUES (#{fileName},#{id},#{url},#{uploadDate},#{userId})")
    public void uploadFile(FileVO fileVO);
    @Delete("DELETE from userPic WHERE user_id=#{userId}")
    public void deleteFile(String userId);
}
