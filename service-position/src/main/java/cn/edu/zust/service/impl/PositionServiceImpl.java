package cn.edu.zust.service.impl;

import cn.edu.zust.common.Constants;
import cn.edu.zust.entity.Position;
import cn.edu.zust.exception.ServiceException;
import cn.edu.zust.mapper.PositionMapper;
import cn.edu.zust.service.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
    @Override
    public Boolean savePosition(Position position) {
        Boolean res = null;
        try {
            if (position.getLongitude() == null || position.getLatitude() == null) {
                throw new ServiceException(Constants.CODE_500, "坐标不能为空");
            } else if (!StringUtils.hasText(position.getName())) {
                throw new ServiceException(Constants.CODE_500, "打卡名称不能为空");
            }
            res = save(position);
        } catch (Exception ignored) {
        }
        return res;
    }

    @Override
    public Boolean deletePoint(Integer pid) {
        Boolean res = null;
        try {
            Position position = getById(pid);
            res = removeById(position);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "删除失败");
        }
        return res;
    }

    @Override
    public Boolean updatePosition(Position position) {
        Boolean res = null;
        try {
            res = updateById(position);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "更新失败");
        }
        return res;
    }
}
