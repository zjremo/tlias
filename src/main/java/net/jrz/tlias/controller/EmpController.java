package net.jrz.tlias.controller;

import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.pojo.Emp;
import net.jrz.tlias.pojo.EmpQueryParam;
import net.jrz.tlias.pojo.PageResult;
import net.jrz.tlias.pojo.Result;
import net.jrz.tlias.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("不使用分页插件，查询员工信息， page = {}, pageSize = {}", page, pageSize);
        PageResult<Emp> pageResult = empService.page(page, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping("/page2")
    public Result page2(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("使用分页插件，查询员工信息， page = {}, pageSize = {}", page, pageSize);
        PageResult<Emp> pageResult = empService.page2(page, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping("/condition")
    public Result pageWithCondition(EmpQueryParam empQueryParam) {
        log.info("查询请求参数，{}", empQueryParam);
        PageResult<Emp> pageResult = empService.pageWithCondition(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("请求参数emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("批量删除员工: ids={}", ids);
        empService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工的详细信息, id = {}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工信息，{}", emp);
        empService.update(emp);
        return Result.success();
    }

}
