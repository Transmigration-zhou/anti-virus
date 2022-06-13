package cn.edu.zust.client;

import cn.edu.zust.entity.Record;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient("service-position")
public interface PositionClient {

    @GetMapping("/position/record/duration/{userNum}")
    List<Record> getRecordByRangeAndUserNum(@PathVariable String userNum,
                                                   @RequestParam Date start,
                                                   @RequestParam Date end);

    @GetMapping("/position/record/duration/pid/{pid}")
    List<Record> getRecordByRangeAndPositionId(@PathVariable Integer pid, @RequestParam Date start, @RequestParam Date end);
}
