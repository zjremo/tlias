package net.jrz.tlias.mapper;

import net.jrz.tlias.pojo.Emp;
import net.jrz.tlias.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    /*
    * 查询所有的员工及其对应的部门名称, 不使用分页插件
    * */
    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id limit #{start}, #{pageSize}")
    List<Emp> list(Integer start, Integer pageSize);

    /*
    * 查询所有的员工及其对应的部门名称，使用分页插件
    * */
    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id")
    List<Emp> list2();

    /*
    * 查询总记录数
    * */
    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    Long count();

    /*
    * 条件查询，使用分页插件
    * */
    List<Emp> listWithCondition(EmpQueryParam empQueryParam);

    /*
     * 新增员工数据
     * 设置主键返回
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /*
    * 批量删除员工信息
    * */
    void deleteByIds(List<Integer> ids);

    /*
    * 根据id查询员工的详细信息
    * */
    Emp getById(Integer id);

    /*
    * 根据姓名获取员工的Id, 使用分页
    * */
    @Select("select * from emp where name like concat('%', #{name}, '%')")
    List<Emp> getByName(String name);

    /*
    * 更新员工信息
    * */
    void updateById(Emp emp);

    /*
    * 统计各个职位的员工人数
    * */
    @MapKey("pos")
    Map<String, Map<String, Object>> countEmpJobData();

    /*
    * 统计各个性别的员工人数
    * */
    @MapKey("name")
    Map<String, Map<String, Object>> countEmpGenderData();

    /*
    * 根据用户名和密码查询员工信息
    * */
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp getUsernameAndPassword(Emp emp);
}
