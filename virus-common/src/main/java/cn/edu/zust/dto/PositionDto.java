package cn.edu.zust.dto;

import lombok.Data;

@Data
public class PositionDto {
    private Integer pid;
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
}
