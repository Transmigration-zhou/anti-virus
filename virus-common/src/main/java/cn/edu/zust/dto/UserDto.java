package cn.edu.zust.dto;

import lombok.Data;

@Data
public class UserDto {
    private String userNum;
    private String password;
    private Integer collegeNum;
    private Integer majorNum;
    private Integer classNum;
    private Integer userType;
    private String userName;
    private Boolean sex;
    private String telephone;
    private String token;      //登录验证
}
