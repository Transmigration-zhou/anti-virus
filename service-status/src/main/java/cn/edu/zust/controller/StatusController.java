package cn.edu.zust.controller;

import cn.edu.zust.common.Result;
import cn.edu.zust.entity.Status;
import cn.edu.zust.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @PostMapping
    public Result saveStatus(@RequestBody Status status) {
        return Result.success(statusService.saveStatus(status));
    }

    @GetMapping("/danger/{collegeNum}")
    public Result getDangerNum(@PathVariable Integer collegeNum) {
        if (collegeNum == 0) return Result.success(statusService.getDangerNum());
        else return Result.success(statusService.getDangerNumByCollegeNum(collegeNum));
    }

    @GetMapping("/risk/{collegeNum}")
    public Result getRiskNum(@PathVariable Integer collegeNum) {
        if (collegeNum == 0) return Result.success(statusService.getRiskNum());
        else return Result.success(statusService.getRiskNumByCollegeNum(collegeNum));
    }

    @GetMapping("/danger/{collegeNum}/all")
    public Result getAllDangerUser(@PathVariable Integer collegeNum) {
        if (collegeNum == 0) return Result.success(statusService.getAllDangerUser());
        else return Result.success(statusService.getAllDangerUserByCollegeNum(collegeNum));
    }

    @GetMapping("/risk/{collegeNum}/all")
    public Result getAllRiskUser(@PathVariable Integer collegeNum) {
        if (collegeNum == 0) return Result.success(statusService.getAllRiskUser());
        else return Result.success(statusService.getAllRiskUserByCollegeNum(collegeNum));
    }

    @GetMapping("/{userNum}")
    public Result getUserStatus(@PathVariable String userNum) {
        return Result.success(statusService.getUserStatus(userNum));
    }
}
