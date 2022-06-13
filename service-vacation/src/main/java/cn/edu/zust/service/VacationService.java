package cn.edu.zust.service;

import cn.edu.zust.entity.Vacation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface VacationService extends IService<Vacation> {
    Boolean saveVacation(Vacation vacation);

    List<Vacation> getVacationsByCollegeNum(Integer collegeNum);

    List<Vacation> getVacationsByUserNum(String userNum);

    Boolean revokeVacation(Integer vacationNum);

    Boolean refuse(Integer vacationNum);

    Boolean accept(Integer vacationNum);
}
