package cn.edu.zust.service.impl;

import cn.edu.zust.common.Constants;
import cn.edu.zust.dto.UserDto;
import cn.edu.zust.entity.User;
import cn.edu.zust.exception.ServiceException;
import cn.edu.zust.mapper.UserMapper;
import cn.edu.zust.service.UserService;
import cn.edu.zust.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public UserDto login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_num", userDto.getUserNum());
        queryWrapper.eq("password", userDto.getPassword());
        User user;
        try {
            user = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (user != null) {
            BeanUtils.copyProperties(user, userDto);
            String token = TokenUtils.genToken(user.getUserNum().toString(), user.getPassword());
            userDto.setToken(token);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_500, "用户名密码错误");
        }
    }

    @Override
    public UserDto getUserById(String userNum) {
        UserDto userDto = new UserDto();
        try {
            User user = getById(userNum);
            BeanUtils.copyProperties(user, userDto);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "查询失败");
        }
        if (!StringUtils.hasText(userDto.getUserNum()))
            throw new ServiceException(Constants.CODE_500, "查询失败");
        return userDto;
    }

    @Override
    public IPage<User> getPage(Integer pageNum, Integer pageSize) {
        IPage<User> res;
        try {
            IPage<User> page = new Page<>(pageNum, pageSize);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            res = page(page, queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "分页数据错误");
        }
        if (res == null) {
            throw new ServiceException(Constants.CODE_500, "分页数据错误");
        }

//        queryWrapper.orderByDesc("id");

        return res;
    }
}
