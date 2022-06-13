package cn.edu.zust.controller;

import cn.edu.zust.common.Result;
import cn.edu.zust.entity.Position;
import cn.edu.zust.entity.Record;
import cn.edu.zust.service.PositionService;
import cn.edu.zust.service.RecordService;
import cn.edu.zust.util.CommonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService positionService;

    @Autowired
    RecordService recordService;

    @GetMapping
    public Result findAll() {
        List<Position> list = positionService.list();
        if (list != null) return Result.success(list);
        else return Result.error("查询点失败");
    }

    @GetMapping("/{pid}")
    public Result findOne(@PathVariable Integer pid) {
        Position position = positionService.getById(pid);
        if (position == null) return Result.error("获取信息失败");
        else return Result.success(position);
    }

    @GetMapping("/record/{pid}")
    public Result findAllRecordsByPid(@PathVariable Integer pid, @RequestParam Integer pageNum,
                                      @RequestParam(defaultValue = "") Integer pageSize) {
        IPage<Record> recordIPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.orderByDesc("sid");
        return Result.success(recordService.page(recordIPage, queryWrapper));
    }

    @GetMapping("/record/duration/pid/{pid}")
    List<Record> getRecordByRangeAndPositionId(@PathVariable Integer pid, @RequestParam Date start, @RequestParam Date end) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("check_time", start, end);
        queryWrapper.eq("pid", pid);
        return recordService.list(queryWrapper);
    }

    @GetMapping("/record/duration/{userNum}")
    List<Record> getRecordByRangeAndUserNum(@PathVariable String userNum,
                                            @RequestParam Date start,
                                            @RequestParam Date end) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_num", userNum);
        queryWrapper.between("check_time", start, end);
        return recordService.list(queryWrapper);
    }

    /**
     * 根据打卡用户id，返回用户打卡记录
     * @param userNum
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/record/u/{uid}")
    public Result findUserRecordsById(@PathVariable("uid") Integer userNum, @RequestParam Integer pageNum,
                                      @RequestParam(defaultValue = "") Integer pageSize) {
        IPage<Record> recordIPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_num", userNum);
        queryWrapper.orderByDesc("sid");
        return Result.success(recordService.page(recordIPage, queryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam(defaultValue = "") Integer pageSize) {
        IPage<Position> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        return Result.success(positionService.page(page, queryWrapper));
    }

    /**
     * 判断用户100s内是否有打卡记录
     */
    @GetMapping("/record/checked/{uid}")
    public Result findRecord(@PathVariable("uid") String userNum) {
        if (recordService.getIsChecked(userNum)) {
            Integer pid = recordService.getLastRecordById(userNum).getPid();
            return Result.success(positionService.getById(pid));
        }
        return Result.error("请打卡");
    }


    @PutMapping
    public Result updatePosition(@RequestBody Position position) {
        Boolean res = positionService.updatePosition(position);
        if (res == null || !res) {
            return Result.error("更新失败！");
        } else {
            return Result.success(position);
        }
    }

    @PostMapping
    public Result save(@RequestBody Position position) {
        Boolean res = positionService.savePosition(position);
        if (res == null || !res) {
            return Result.error("添加失败！");
        } else {
            return Result.success(position);
        }
    }

    @PostMapping("/check")
    public Result checkPoint(@RequestBody Record record) {
        Boolean res = recordService.saveRecord(record);
        if (res == null || !res) {
            return Result.error("添加失败！");
        } else {
            return Result.success(1);
        }
    }

    @DeleteMapping("/{pid}")
    public Result deletePoint(@PathVariable Integer pid) {
        Boolean res = positionService.deletePoint(pid);
        Boolean res2 = recordService.deleteRecord(pid);
        return Result.success();
    }
}
