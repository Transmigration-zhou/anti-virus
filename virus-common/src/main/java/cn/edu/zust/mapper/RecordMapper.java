package cn.edu.zust.mapper;

import cn.edu.zust.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface RecordMapper extends BaseMapper<Record> {

    @Select("SELECT * from record WHERE user_num = #{userNum} ORDER BY sid DESC LIMIT 1")
    Record getFirstRecordById(String userNum);
}
