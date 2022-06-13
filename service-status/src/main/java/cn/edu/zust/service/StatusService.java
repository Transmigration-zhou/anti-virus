package cn.edu.zust.service;

import cn.edu.zust.dto.UserDto;
import cn.edu.zust.entity.Status;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface StatusService extends IService<Status> {
    Boolean saveStatus(Status status);

    Integer getRiskNum();

    Integer getDangerNum();

    Integer getDangerNumByCollegeNum(Integer collegeNum);

    Integer getRiskNumByCollegeNum(Integer collegeNum);

    List<UserDto> getAllDangerUser();

    List<UserDto> getAllDangerUserByCollegeNum(Integer collegeNum);

    List<UserDto> getAllRiskUser();

    List<UserDto> getAllRiskUserByCollegeNum(Integer collegeNum);

    Integer getUserStatus(String userNum);
}
