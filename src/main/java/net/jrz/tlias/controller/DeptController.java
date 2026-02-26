package net.jrz.tlias.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.pojo.Dept;
import net.jrz.tlias.pojo.Result;
import net.jrz.tlias.service.DeptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {
    /*
    * 注入依赖 部门服务
    * */
    @Resource(name="deptServiceImpl")
    private DeptService deptService;

    /*
    * 查询所有部门信息
    * */
//    @RequestMapping(value = "/depts", method = {RequestMethod.GET})
    @GetMapping
    public Result list(){
//        System.out.println("查询部门列表");
        log.info("查询部门列表");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /*
    * 根据ID删除部门
    *  */
    @DeleteMapping
    public Result delete(Integer id){
//        System.out.println("根据Id删除部门, id = " + id);
        log.info("根据ID删除部门，{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    /*
    * 新增部门
    * */
    @PostMapping
    public Result add(@RequestBody Dept dept){
//        System.out.println("新增部门，dept = " + dept);
        log.info("新增部门, dept = {}", dept);
        deptService.save(dept);
        return Result.success();
    }

    /*
    * 根据路径参数获取部门信息
    * */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer deptId){
//        System.out.println("根据ID查询部门信息: dept_id = " + deptId);
        log.info("根据Id查询部门信息, dept_id = {}", deptId);
        Dept dept = deptService.getById(deptId);
        return Result.success(dept);
    }

    /*
    * 更新部门信息 依据id
    * */
    @PutMapping
    public Result update(@RequestBody Dept dept){
//        System.out.println("修改部门，dept = " + dept);
        log.info("修改部门, dept = {}", dept);
        deptService.update(dept);
        return Result.success();
    }

}
