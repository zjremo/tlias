package net.jrz.tlias.mapper;

import net.jrz.tlias.pojo.Clazz;
import net.jrz.tlias.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClazzMapper {
    /*
    * 条件分页查询
    * */
    List<Clazz> page(ClazzQueryParam clazzQueryParam);

    /*
    * 新增班级
    * */
    void insert(Clazz clazz);

    /*
    * 根据ID获取班级信息
    * */
    Clazz getById(Integer id);

    /*
    * 更新班级信息
    * */
    void update(Clazz clazz);

    /*
    * 查询班级人数 , 为了复用，传递id列表
    * */
    @MapKey("clazzId")
    Map<Long, Map<String, Object>> count(List<Integer> ids);

    /*
    * 批量删除班级
    * */
    void delete(List<Integer> ids);
}
