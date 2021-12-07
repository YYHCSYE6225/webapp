package edu.neu.coe.csye6225.webapp.dao.secondary;

import edu.neu.coe.csye6225.webapp.entity.User;
import edu.neu.coe.csye6225.webapp.security.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SecondaryUserMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "username", column = "username"),
            @Result(property = "accountCreated", column = "account_created"),
            @Result(property = "accountUpdated", column = "account_updated"),
            @Result(property = "verified", column = "verified"),
            @Result(property = "verifiedOn", column = "verified_on")
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
            @Result(property = "accountUpdated", column = "account_updated"),
            @Result(property = "verified", column = "verified"),
            @Result(property = "verifiedOn", column = "verified_on")
    })
    @Select("SELECT * from user WHERE username=#{username}")
    public User getUserByUsername(String username);

    @Results({
            @Result(property = "password", column = "password"),
            @Result(property = "Username", column = "username"),
    })
    @Select("SELECT username, password from user WHERE username=#{username}")
    public UserEntity getUserEntityByUsername(String username);
}
