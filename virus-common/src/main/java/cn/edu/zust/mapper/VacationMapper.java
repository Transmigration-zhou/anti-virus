package cn.edu.zust.mapper;

import cn.edu.zust.entity.Vacation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VacationMapper extends BaseMapper<Vacation> {

    @Select("SELECT * FROM vacation WHERE user_num IN (SELECT user_num FROM user WHERE college_num = #{collegeNum}) ORDER BY vacation_num DESC")
    List<Vacation> getVacationsByCollegeNum(Integer collegeNum);
}
