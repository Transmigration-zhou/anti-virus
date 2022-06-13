package cn.edu.zust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Status {
    @TableId(type = IdType.AUTO)
    Integer stid;
    String userNum;
    @DateTimeFormat(pattern = "yyyy-MMMM-dd")
    String stateTime;
    Integer state;
}
