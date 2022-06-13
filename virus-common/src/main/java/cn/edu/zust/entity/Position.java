package cn.edu.zust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Position {
    @TableId(type = IdType.AUTO)
    private Integer pid;
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
}
