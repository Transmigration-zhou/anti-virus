package cn.edu.zust.service.impl;

import cn.edu.zust.client.PositionClient;
import cn.edu.zust.common.Constants;
import cn.edu.zust.common.Result;
import cn.edu.zust.dto.UserDto;
import cn.edu.zust.entity.Position;
import cn.edu.zust.entity.Record;
import cn.edu.zust.entity.Status;
import cn.edu.zust.exception.ServiceException;
import cn.edu.zust.mapper.StatusMapper;
import cn.edu.zust.service.StatusService;
import cn.edu.zust.client.UserClient;
import cn.edu.zust.util.CommonUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.*;

@Service
public class StatusServiceImpl extends ServiceImpl<StatusMapper, Status> implements StatusService {

    @Autowired
    StatusMapper statusMapper;

    @Autowired
    UserClient userClient;

    @Autowired
    PositionClient positionClient;


    /**
     * 上传用户核酸检测信息
     * @param status
     * @return
     */
    @Override
    public Boolean saveStatus(Status status) {
        Boolean res = null;
        if (!StringUtils.hasText(status.getUserNum())) {
            throw new ServiceException(Constants.CODE_500, "用户名不能为空");
        }
        if (status.getState() == null) {
            throw new ServiceException(Constants.CODE_500, "状态不能为空");
        }
        try {
            status.setStateTime(CommonUtils.DateToStr(new Date()));
            res = save(status);
            //如果是阳性
            if (status.getState() == 1) {
                Date now = new Date();
                List<Record> recordList = positionClient.getRecordByRangeAndUserNum(status.getUserNum(), CommonUtils.datePlus(now, -1), CommonUtils.datePlus(now, 1));
                Map<String, Integer> map = new HashMap<>();
                for(Record record : recordList) {
                    Integer pid = record.getPid();
                    String checkTime = record.getCheckTime();
                    Date tmp = CommonUtils.StrToDate(checkTime);
                    List<Record> list = positionClient.getRecordByRangeAndPositionId(pid, CommonUtils.datePlus(tmp, -1), CommonUtils.datePlus(tmp, 1));
                    for(Record record1 : list) {
                        map.put(record1.getUserNum(), 1);
                    }
                }
                Set<String> set = map.keySet();
                for (String key : set) {
                    if (key.equals(status.getUserNum())) continue;
                    saveRiskUser(key);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "保存失败");
        }
        return res;
    }

    public Boolean saveRiskUser(String userNum) throws ParseException {
        Status status = new Status();
        status.setUserNum(userNum);
        status.setState(2);
        status.setStateTime(CommonUtils.DateToStr(new Date()));
        return save(status);
    }

    @Override
    public Integer getRiskNum() {
        Integer res = null;
        try {
            res = statusMapper.getRiskNum();
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "请求风险人数失败");
        }
        return res;
    }

    @Override
    public Integer getDangerNum() {
        Integer res = null;
        try {
            res = statusMapper.getDangerNum();
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "请求高危人数失败");
        }
        return res;
    }

    @Override
    public Integer getDangerNumByCollegeNum(Integer collegeNum) {
        Integer res = null;
        try {
            res = statusMapper.getDangerNumByCollegeNum(collegeNum);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "请求高危人数失败");
        }
        return res;
    }

    @Override
    public Integer getRiskNumByCollegeNum(Integer collegeNum) {
        Integer res = null;
        try {
            res = statusMapper.getRiskNumByCollegeNum(collegeNum);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "请求高危人数失败");
        }
        return res;
    }

    @Override
    public List<UserDto> getAllDangerUser() {
        List<UserDto> list = new ArrayList<>();
        try {
            list = getUserListByStatusList(statusMapper.getDangerUser());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "请求高危人员失败");
        }
        return list;
    }

    @Override
    public List<UserDto> getAllDangerUserByCollegeNum(Integer collegeNum) {
        List<UserDto> list = new ArrayList<>();
        try {
            list = getUserListByStatusList(statusMapper.getDangerUserByCollegeNum(collegeNum));
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "请求高危人员失败");
        }
        return list;
    }

    @Override
    public List<UserDto> getAllRiskUser() {
        List<UserDto> list = new ArrayList<>();
        try {
            list = getUserListByStatusList(statusMapper.getRiskUser());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "请求高危人员失败");
        }
        return list;
    }

    @Override
    public List<UserDto> getAllRiskUserByCollegeNum(Integer collegeNum) {
        List<UserDto> list = new ArrayList<>();
        try {
            list = getUserListByStatusList(statusMapper.getRiskUserByCollegeNum(collegeNum));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "请求高危人员失败");
        }
        return list;
    }

    @Override
    public Integer getUserStatus(String userNum) {
        Integer res = statusMapper.getUserStatus(userNum);
        if (res == null) res = 0;
        return res;
    }

    protected List<UserDto> getUserListByStatusList(List<Status> statusList) {
        List<UserDto> list = new ArrayList<>();
        for(Status status : statusList) {
            Result result = userClient.getUserByUserId(status.getUserNum());
            UserDto user = ConvertResultDataToUserDto((LinkedHashMap) result.getData());
//                System.out.println(result.getCode());
//                System.out.println("_---------------_" + result.getData().toString());
            list.add(user);
        }
        return list;
    }


    protected UserDto ConvertResultDataToUserDto(LinkedHashMap Data) {
        UserDto userDto = new UserDto();
        userDto.setUserNum((String) Data.get("userNum"));
        userDto.setCollegeNum((Integer) Data.get("collegeNum"));
        userDto.setUserName((String) Data.get("userName"));
        userDto.setSex((Boolean) Data.get("sex"));
        userDto.setTelephone((String) Data.get("telephone"));
        return userDto;
    }
}
