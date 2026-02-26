package net.jrz.tlias.mapper;

import net.jrz.tlias.pojo.EmpLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpLogMapper {
    // 日志插入
    @Insert("insert into emp_log(operate_time, info) values (#{operateTime}, #{info})")
    void insert(EmpLog empLog);
}
