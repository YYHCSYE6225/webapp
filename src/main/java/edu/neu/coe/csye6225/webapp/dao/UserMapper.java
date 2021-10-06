package edu.neu.coe.csye6225.webapp.dao;

import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.entity.vo.UserVO;
import edu.neu.coe.csye6225.webapp.security.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Insert("INSERT INTO user(id,first_name,last_name,password,username)VALUES (#{id},#{firstName},#{lastName}" +
            ",#{password},#{username})")
    public void addUser(User user);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "username", column = "username"),
            @Result(property = "accountCreated", column = "account_created"),
            @Result(property = "accountUpdated", column = "account_updated")
    })
    @Select("SELECT * from user WHERE id=#{id}")
    public User getUserById(String uuid);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "username", column = "username"),
            @Result(property = "accountCreated", column = "account_created"),
            @Result(property = "accountUpdated", column = "account_updated")
    })
    @Select("SELECT * from user WHERE username=#{username}")
    public User getUserByUsername(String username);

    @Results({
            @Result(property = "password", column = "password"),
            @Result(property = "Username", column = "username"),
    })
    @Select("SELECT username, password from user WHERE username=#{username}")
    public UserEntity getUserEntityByUsername(String username);

    @Update("UPDATE user SET first_name=#{firstName}, last_name=#{lastName}, password=#{password} WHERE username=#{username}")
    public void updateUser(UserVO userVO);
}
