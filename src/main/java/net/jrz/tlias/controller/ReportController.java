package net.jrz.tlias.controller;

import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.pojo.JobOption;
import net.jrz.tlias.pojo.Result;
import net.jrz.tlias.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/reports")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;
    /*
    * 统计各个职位的员工人数
    * */
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计各个职位的员工人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /*
    * 统计员工性别信息
    * */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别信息");
        List<Map<String, Object>> list = reportService.getEmpGenderData();
        return Result.success(list);
    }
}
