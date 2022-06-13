package cn.edu.zust.service;

import cn.edu.zust.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PositionService extends IService<Position> {

    Boolean savePosition(Position position);

    Boolean deletePoint(Integer pid);

    Boolean updatePosition(Position position);
}