package net.jrz.tlias.service;

import net.jrz.tlias.exception.DefaultException;
import net.jrz.tlias.pojo.Clazz;
import net.jrz.tlias.pojo.ClazzQueryParam;
import net.jrz.tlias.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    /*
    * 条件分页查询
    * */
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    /*
    * 新增班级
    * */
    void save(Clazz clazz);

    /*
    * 根据ID获取班级信息
    * */
    Clazz getInfo(Integer id);

    /*
    * 更新操作
    * */
    void update(Clazz clazz);

    /*
    * 删除操作 批量
    * */
    void delete(List<Integer> ids) throws DefaultException;
}
