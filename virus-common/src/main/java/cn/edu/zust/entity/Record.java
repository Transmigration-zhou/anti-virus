package cn.edu.zust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Data
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer sid;
    private Integer pid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String checkTime;
    private String userNum;
}
