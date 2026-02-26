package net.jrz.tlias.service.impl;

import net.jrz.tlias.mapper.EmpMapper;
import net.jrz.tlias.pojo.JobOption;
import net.jrz.tlias.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public JobOption getEmpJobData() {
        // "教研主管" -> {"pos" : "教研主管", "total": 1}
        Map<String, Map<String, Object>> map = empMapper.countEmpJobData();
        // 流式编程拆解list
        List<Object> jobList = map.values().stream().map(e -> e.get("pos")).toList();
        List<Object> dataList = map.values().stream().map(e -> e.get("total")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        Map<String, Map<String, Object>> map = empMapper.countEmpGenderData();
        return map.values().stream().toList();
    }
}
