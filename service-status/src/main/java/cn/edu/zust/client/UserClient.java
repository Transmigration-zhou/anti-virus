package cn.edu.zust.client;

import cn.edu.zust.common.Result;
import cn.edu.zust.dto.UserDto;
import cn.edu.zust.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserClient {
    @GetMapping("/user/{userNum}")
    Result getUserByUserId(@PathVariable String userNum);
}
