package cn.edu.zust.mapper;

import cn.edu.zust.entity.Status;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StatusMapper extends BaseMapper<Status> {

    @Select("SELECT COUNT(*) from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status GROUP BY user_num)) as b WHERE state = 2")
    Integer getRiskNum();

    @Select("SELECT COUNT(*) from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status GROUP BY user_num)) as b WHERE state = 1")
    Integer getDangerNum();

    @Select("SELECT COUNT(*) from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status WHERE user_num in (SELECT user_num from user WHERE college_num = #{collegeNum}) GROUP BY user_num)) as b WHERE state = 2")
    Integer getRiskNumByCollegeNum(Integer collegeNum);

    @Select("SELECT COUNT(*) from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status WHERE user_num in (SELECT user_num from user WHERE college_num = #{collegeNum}) GROUP BY user_num)) as b WHERE state = 1")
    Integer getDangerNumByCollegeNum(Integer collegeNum);

    @Select("SELECT * from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status GROUP BY user_num)) as b WHERE state = 2")
    List<Status> getRiskUser();

    @Select("SELECT * from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status GROUP BY user_num)) as b WHERE state = 1")
    List<Status> getDangerUser();

    @Select("SELECT * from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status WHERE user_num in (SELECT user_num from user WHERE college_num = #{collegeNum}) GROUP BY user_num)) as b WHERE state = 2")
    List<Status> getRiskUserByCollegeNum(Integer collegeNum);

    @Select("SELECT * from (SELECT * FROM status WHERE stid in (SELECT max(stid) from status WHERE user_num in (SELECT user_num from user WHERE college_num = #{collegeNum}) GROUP BY user_num)) as b WHERE state = 1")
    List<Status> getDangerUserByCollegeNum(Integer collegeNum);

    @Select("SELECT state FROM status WHERE stid = (SELECT max(stid) from status WHERE user_num = #{userNum})")
    Integer getUserStatus(String userNum);
}
