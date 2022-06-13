package cn.edu.zust.service;

import cn.edu.zust.dto.UserDto;
import cn.edu.zust.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    UserDto login(UserDto userDto);

    UserDto getUserById(String userNum);

    IPage<User> getPage(Integer pageNum, Integer pageSize);
}
