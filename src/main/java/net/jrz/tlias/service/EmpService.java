package net.jrz.tlias.service;

import net.jrz.tlias.pojo.Emp;
import net.jrz.tlias.pojo.EmpQueryParam;
import net.jrz.tlias.pojo.LoginInfo;
import net.jrz.tlias.pojo.PageResult;

import java.util.List;

public interface EmpService {
    /**
     * 分页查询 不使用分页插件
     * @param page 页码
     * @param pageSize 每页记录数
     */
    PageResult<Emp> page(Integer page, Integer pageSize);

    /**
     * 分页查询 使用分页插件
     * @param page 页码
     * @param pageSize 每页记录数
     */
    PageResult<Emp> page2(Integer page, Integer pageSize);

    /**
     * 分页条件查询 使用分页插件
     * @param empQueryParam 访问参数
     */
    PageResult<Emp> pageWithCondition(EmpQueryParam empQueryParam);


    /**
     * 插入员工
     * @param emp 员工
     */
    void save(Emp emp);

    /**
     * 删除员工
     * @param ids 员工id列表
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id查询员工的详细信息
     * @param id 员工id
     */
    Emp getInfo(Integer id);

    /**
     * 根据name分页查询员工信息
     * @param name 员工id
     */
    List<Emp> getByName(String name, Integer page, Integer pageSize);

    /**
     * 更新员工信息
     * @param emp 员工
     */
    void update(Emp emp);

    /**
     * 登录
     * @param emp 员工
     */
    LoginInfo login(Emp emp);
}
