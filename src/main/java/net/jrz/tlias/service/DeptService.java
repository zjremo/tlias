package net.jrz.tlias.service;

import net.jrz.tlias.pojo.Dept;

import java.util.List;

public interface DeptService {
    /*
    * 查询所有部门信息
    * */
    List<Dept> findAll();

    /*
    * 根据ID删除部门
    * */
    void deleteById(Integer id);

    /*
    * 添加部门
    * */
    void save(Dept dept);

    /*
    * 根据ID查询部门
    * */
    Dept getById(Integer id);

    /*
    * 更新部门信息 依据id
    * */
    void update(Dept dept);
}
