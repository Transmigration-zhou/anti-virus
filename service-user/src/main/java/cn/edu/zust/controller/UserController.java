package cn.edu.zust.controller;

import cn.edu.zust.common.Constants;
import cn.edu.zust.common.Result;
import cn.edu.zust.dto.UserDto;
import cn.edu.zust.entity.User;
import cn.edu.zust.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto) {
        String userNum = userDto.getUserNum();
        String password = userDto.getPassword();
        if (StringUtils.isEmpty(userNum) || StringUtils.isEmpty(password)) {
            return Result.error(Constants.CODE_500, "账号密码不能为空");
        }
        return Result.success(userService.login(userDto));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam(defaultValue = "") Integer pageSize,
                           @RequestParam(defaultValue = "") String keyWord) {
        IPage<User> userPage = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_num", keyWord);
        queryWrapper.or().like("user_name", keyWord);
        queryWrapper.or().like("telephone", keyWord);
        return Result.success(userService.page(userPage, queryWrapper));
    }

    @GetMapping("/{userNum}")
    public Result findUserById(@PathVariable String userNum) {
        return Result.success(userService.getUserById(userNum));
    }

    @PostMapping("/info")
    public Result info(@RequestBody User user) {
        user = userService.getById(user.getUserNum());
        if (user == null) {
            return Result.error("获取失败");
        }
        return Result.success(user);
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        if (userService.getById(user.getUserNum()) == null) {
            return Result.success(userService.save(user));
        } else {
            return Result.success(userService.updateById(user));
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(userService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeByIds(ids));
    }
}
