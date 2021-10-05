package edu.neu.coe.csye6225.webapp.security;

public class JWTPayLoad {
    private String username;
    private String password;

    public JWTPayLoad(String username,String password){
        this.password=password;
        this.username=username;
    }

    public UserEntity toUser()
    {
        UserEntity user=new UserEntity();
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }
}
