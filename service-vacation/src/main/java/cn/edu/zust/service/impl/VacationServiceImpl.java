package cn.edu.zust.service.impl;

import cn.edu.zust.common.Constants;
import cn.edu.zust.entity.Vacation;
import cn.edu.zust.exception.ServiceException;
import cn.edu.zust.mapper.VacationMapper;
import cn.edu.zust.service.VacationService;
import cn.edu.zust.util.CommonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VacationServiceImpl extends ServiceImpl<VacationMapper, Vacation> implements VacationService {

    @Autowired
    VacationMapper vacationMapper;

    @Override
    public Boolean saveVacation(Vacation vacation) {
        if (!StringUtils.hasText(vacation.getReason())) {
            throw new ServiceException(Constants.CODE_500, "离校理由不能为空");
        }
        if (!StringUtils.hasText(vacation.getWay())) {
            throw new ServiceException(Constants.CODE_500, "出行方式不能为空");
        }
        if (!StringUtils.hasText(vacation.getStartTime()) || !StringUtils.hasText(vacation.getEndTime())) {
            throw new ServiceException(Constants.CODE_500, "时间不能为空");
        }
        try {
            vacation.setStartTime(CommonUtils.DateStrToStr(vacation.getStartTime()));
            vacation.setEndTime(CommonUtils.DateStrToStr(vacation.getEndTime()));
            vacation.setState(Vacation.STATE_PENDING);
            vacation.setRequestTime(CommonUtils.DateToStr(new Date()));
            save(vacation);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "申请失败");
        }
        return true;
    }

    @Override
    public List<Vacation> getVacationsByCollegeNum(Integer collegeNum) {
        return vacationMapper.getVacationsByCollegeNum(collegeNum);
    }

    @Override
    public List<Vacation> getVacationsByUserNum(String userNum) {
        List<Vacation> list = new ArrayList<>();
        try {
            QueryWrapper<Vacation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_num", userNum);
            queryWrapper.orderByDesc("vacation_num");
            list = list(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "请求失败");
        }
        return list;
    }

    @Override
    public Boolean revokeVacation(Integer vacationNum) {
        Vacation vacation = getById(vacationNum);
        Boolean res = null;
        if (vacation.getState() == Vacation.STATE_PENDING) {
            res = removeById(vacation);
        } else {
            throw new ServiceException(Constants.CODE_500, "请求已经完成");
        }
        return res;
    }

    @Override
    public Boolean refuse(Integer vacationNum) {
        Vacation vacation = getById(vacationNum);
        Boolean res = null;
        if (vacation.getState() == Vacation.STATE_PENDING) {
            vacation.setState(Vacation.STATE_REJECT);
            res = updateById(vacation);
        } else {
            throw new ServiceException(Constants.CODE_500, "请求已经完成");
        }
        return res;
    }

    @Override
    public Boolean accept(Integer vacationNum) {
        Vacation vacation = getById(vacationNum);
        Boolean res = null;
        if (vacation.getState() == Vacation.STATE_PENDING) {
            vacation.setState(Vacation.STATE_ACCEPT);
            res = updateById(vacation);
        } else {
            throw new ServiceException(Constants.CODE_500, "请求已经完成");
        }
        return res;
    }
}
