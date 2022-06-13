package cn.edu.zust.service;

import cn.edu.zust.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RecordService extends IService<Record> {
    Boolean saveRecord(Record record);

    Boolean deleteRecord(Integer pid);

    List<Record> getRecordsByPid(Integer pid);

    Boolean getIsChecked(String userNum);

    Record getLastRecordById(String userNum);
}
