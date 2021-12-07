package edu.neu.coe.csye6225.webapp.dao.primary;

import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PrimaryUserMapper {
    @Insert("INSERT INTO user(id,first_name,last_name,password,username,verified)VALUES (#{id},#{firstName},#{lastName}" +
            ",#{password},#{username},#{verified})")
    public void addUser(User user);
    @Update("UPDATE user SET first_name=#{firstName}, last_name=#{lastName}, password=#{password} WHERE username=#{username}")
    public void updateUser(UserVO userVO);

    @Update("UPDATE user SET verified=true, verified_on=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%m:%s') WHERE username=#{username}")
    public void verifyUser(String username);
}
