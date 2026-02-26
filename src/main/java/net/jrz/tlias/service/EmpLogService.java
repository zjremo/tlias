package net.jrz.tlias.service;

import net.jrz.tlias.pojo.Emp;
import net.jrz.tlias.pojo.EmpLog;

public interface EmpLogService {
    // 记录增加员工日志
    void insertLog(EmpLog empLog);
}
