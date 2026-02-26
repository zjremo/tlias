package net.jrz.tlias.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.jrz.tlias.mapper.EmpExprMapper;
import net.jrz.tlias.mapper.EmpMapper;
import net.jrz.tlias.pojo.*;
import net.jrz.tlias.service.EmpLogService;
import net.jrz.tlias.service.EmpService;
import net.jrz.tlias.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    /*
     * 不使用分页插件，分页查询
     * */
    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        // 1. 获取总记录数
        Long total = empMapper.count();

        // 2. 获取结果列表
        Integer start = (page - 1) * pageSize;
        List<Emp> empList = empMapper.list(start, pageSize);

        // 3. 封装结果
        return new PageResult<>(total, empList);
    }

    /*
     * 使用分页插件，分页查询
     * */
    @Override
    public PageResult<Emp> page2(Integer page, Integer pageSize) {
        // 1. 设置分页参数
        PageHelper.startPage(page, pageSize);

        // 2. 执行查询
        List<Emp> empList = empMapper.list2();
        Page<Emp> p = (Page<Emp>) empList;

        // 3. 封装结果
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    /*
     * 分页条件查询
     * */
    @Override
    public PageResult<Emp> pageWithCondition(EmpQueryParam empQueryParam) {
        // 1. 设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2. 执行查询
        List<Emp> empList = empMapper.listWithCondition(empQueryParam);
        Page<Emp> p = (Page<Emp>) empList;

        // 3. 封装结果
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    // Transactional注解一般在业务层使用，用于控制事务，默认只会是遇到运行时异常时才会进行回滚
    // 此时用rollbackFor指定还会处理什么异常
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        try {
            // 1. 补充emp信息，设置创建时间和更新时间
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            // 2. emp表 调用插入方法保存个人信息
            empMapper.insert(emp);

//        int i = 1/0; 由于开启了事务管理，所以此时遇到异常之后会进行整体回滚
//            int i = 1/0;

            // 3. expr表 调用插入方法保存工作经历
            // 3.1 主键返回获取员工Id
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if (CollectionUtils.isEmpty(exprList)) {
                // 3.2 组装员工id到工作经历中
                exprList.forEach(
                        e -> e.setEmpId(empId)
                );
                // 3.3 批量插入工作经历
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            // 不管成功与否都要记录日志
            EmpLog empLog = new EmpLog();
            empLog.setOperateTime(LocalDateTime.now());
            empLog.setInfo(emp.toString());

            empLogService.insertLog(empLog);
        }
    }

    @Transactional
    @Override
    public void deleteByIds(List<Integer> ids) {
        // 1. 根据id批量删除员工基本信息
        empMapper.deleteByIds(ids);

        // 2. 根据员工的id批量删除工作经历
        empExprMapper.deleteByEmpIds(ids);

    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional
    @Override
    public void update(Emp emp) {
        // 1. 根据ID更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 2. 根据员工ID删除员工的工作经历信息 删除旧的
        empExprMapper.deleteByEmpIds(Collections.singletonList(emp.getId()));

        // 3. 新增员工的工作经历数据 新增新的
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp empLogin = empMapper.getUsernameAndPassword(emp);
        if (empLogin != null) {
            // 1. 生成JWT令牌
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", empLogin.getId());
            dataMap.put("username", empLogin.getUsername());

            // 2. 对上面的claims map进行签名生成jwt
            String jwt = JwtUtils.generateJwt(dataMap);

            // 3. 生成完整的登录信息
            return new LoginInfo(empLogin.getId(), empLogin.getUsername(), empLogin.getName(), jwt);
        }
        return null;
    }

    @Override
    public List<Emp> getByName(String name, Integer page, Integer pageSize) {
        // 1. 启动分页
        PageHelper.startPage(page, pageSize);

        // 2. 获取分页结果
        List<Emp> empList = empMapper.getByName(name);
        Page<Emp> p = (Page<Emp>) empList;

        // 3. 组装分页结果
        return p.getResult();
    }


}