package cn.edu.zust.controller;

import cn.edu.zust.common.Result;
import cn.edu.zust.entity.Vacation;
import cn.edu.zust.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacation")
public class VacationController {
    @Autowired
    VacationService vacationService;

    @GetMapping("/{collegeNum}")
    public Result getVacationsByCollegeNum(@PathVariable Integer collegeNum) {
        return Result.success(vacationService.getVacationsByCollegeNum(collegeNum));
    }

    @GetMapping("/user/{userNum}")
    public Result getVacationsByUserNum(@PathVariable String userNum) {
        return Result.success(vacationService.getVacationsByUserNum(userNum));
    }

    @PostMapping
    public Result saveVacation(@RequestBody Vacation vacation) {
        return Result.success(vacationService.saveVacation(vacation));
    }

    @DeleteMapping("/{vacationNum}")
    public Result revokeVacation(@PathVariable Integer vacationNum) {
        return Result.success(vacationService.revokeVacation(vacationNum));
    }

    @PutMapping("/refuse/{vacationNum}")
    public Result refuse(@PathVariable Integer vacationNum) {
        return Result.success(vacationService.refuse(vacationNum));
    }

    @PutMapping("/accept/{vacationNum}")
    public Result accept(@PathVariable Integer vacationNum) {
        return Result.success(vacationService.accept(vacationNum));
    }
}
