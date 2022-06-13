package cn.edu.zust.service.impl;

import cn.edu.zust.common.Constants;
import cn.edu.zust.entity.Position;
import cn.edu.zust.entity.Record;
import cn.edu.zust.exception.ServiceException;
import cn.edu.zust.mapper.RecordMapper;
import cn.edu.zust.service.RecordService;
import cn.edu.zust.util.CommonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    RecordMapper recordMapper;

    @Override
    public Boolean saveRecord(Record record) {
        Boolean res = null;
        try {
            record.setCheckTime(CommonUtils.DateToStr(new Date()));
            res = save(record);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "添加失败");
        }
        return res;
    }

    @Override
    public Boolean deleteRecord(Integer pid) {
        Boolean res = null;
        try {
            QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid", pid);
            List<Record> list = list(queryWrapper);
            res = removeByIds(list);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "删除失败");
        }
        return res;
    }

    @Override
    public List<Record> getRecordsByPid(Integer pid) {
        List<Record> list = null;
        try {
            QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid", pid);
            list = list(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "获取信息失败");
        }
        return list;
    }

    @Override
    public Boolean getIsChecked(String userNum) {
        Record record = recordMapper.getFirstRecordById(userNum);
        if (record == null) return false;
        Date checkTime = CommonUtils.StrToDate(record.getCheckTime());
        Date now = new Date();
        return (now.getTime() - checkTime.getTime()) / 1000 <= 100;
    }

    @Override
    public Record getLastRecordById(String userNum) {
        return recordMapper.getFirstRecordById(userNum);
    }
}
