package cn.edu.zust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Vacation {
    @TableId(type = IdType.AUTO)
    private Integer vacationNum;
    private String reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String requestTime;
    private String way;
    private Integer state;
    private String userNum;
    public static final int STATE_PENDING = 0;
    public static final int STATE_REJECT = 1;
    public static final int STATE_ACCEPT = 2;
}
