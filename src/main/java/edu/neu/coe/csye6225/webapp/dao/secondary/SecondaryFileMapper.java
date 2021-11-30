package edu.neu.coe.csye6225.webapp.dao.secondary;

import edu.neu.coe.csye6225.webapp.entity.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SecondaryFileMapper {
    @Results({
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "id", column = "id"),
            @Result(property = "url", column = "url"),
            @Result(property = "uploadDate", column = "upload_date"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT * from userPic WHERE user_id=#{userId}")
    public FileVO getFile(String userId);
}
