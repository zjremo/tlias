package net.jrz.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.jrz.tlias.exception.DefaultException;
import net.jrz.tlias.mapper.ClazzMapper;
import net.jrz.tlias.pojo.Clazz;
import net.jrz.tlias.pojo.ClazzQueryParam;
import net.jrz.tlias.pojo.Emp;
import net.jrz.tlias.pojo.PageResult;
import net.jrz.tlias.service.ClazzService;
import net.jrz.tlias.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private EmpService empService;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        // 1. 开启分页
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        // 2. 获取分页结果
        List<Clazz> clazzList = clazzMapper.page(clazzQueryParam);
        Page<Clazz> p = (Page<Clazz>) clazzList;

        // 3. 手动组装最终结果
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    /*
     * 新增班级
     * */
    @Override
    public void save(Clazz clazz) {
        // 1. 搜索masterName对应的masterId信息
        List<Emp> empList = empService.getByName(clazz.getMasterName(), 1, 1);

        // 2. 添加班级
        clazz.setMasterId(empList.getFirst().getId());
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    /*
     * 根据ID获取班级信息
     * */
    @Override
    public Clazz getInfo(Integer id) {
        return clazzMapper.getById(id);
    }

    /*
     * 更新班级信息
     * */
    @Override
    public void update(Clazz clazz) {
        // 1. 填充信息: 更新时间 masterId subject
        clazz.setUpdateTime(LocalDateTime.now());

        if(StringUtils.hasText(clazz.getMasterName())){
            List<Emp> empList = empService.getByName(clazz.getMasterName(), 1, 1);
            clazz.setMasterId(empList.getFirst().getId());
        }
        String subjectName = clazz.getSubjectName();
        if (StringUtils.hasText(subjectName)){
            switch (subjectName) {
                case "java":
                    clazz.setSubject(1);
                    break;
                case "前端":
                    clazz.setSubject(2);
                    break;
                case "大数据":
                    clazz.setSubject(3);
                    break;
                case "Python":
                    clazz.setSubject(4);
                    break;
                case "Go":
                    clazz.setSubject(5);
                    break;
                case "嵌入式":
                    clazz.setSubject(6);
                    break;
                default:
            }
        }
        clazzMapper.update(clazz);
    }

    @Override
    public void delete(List<Integer> ids) throws DefaultException {
        // 1. 查询班级下是否还有人
        Map<Long, Map<String, Object>> count = clazzMapper.count(ids);

        // 2. 班级下有人就抛出异常，说明班级下有人不能删除
        List<Integer> exceptionIdList = count.values().stream().filter(map -> (Long) map.get("count") != 0).map(m -> {
            long v = (Long) m.get("clazzId");
            return (int) v;
        }).toList();
        List<Integer> deleteIdList = ids.stream().filter(id -> !exceptionIdList.contains(id)).toList();

        // 3. 把能删除掉的删除，不能删除的报异常
        if (!CollectionUtils.isEmpty(deleteIdList))
            clazzMapper.delete(deleteIdList);
        if (!CollectionUtils.isEmpty(exceptionIdList)){
            String msg = String.format("删除时有误, 如下班级存在学生无法删除: %s\n", exceptionIdList);
            throw new DefaultException(msg);
        }
    }
}
