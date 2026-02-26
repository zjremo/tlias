package net.jrz.tlias.service;

import net.jrz.tlias.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /*
    * 统计各个职位的员工人数
    * @return
    * */
    JobOption getEmpJobData();

    /*
    * 统计员工性别信息
    * */
    List<Map<String, Object>> getEmpGenderData();

}
