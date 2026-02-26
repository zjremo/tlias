package net.jrz.tlias.controller;

import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.exception.DefaultException;
import net.jrz.tlias.pojo.Clazz;
import net.jrz.tlias.pojo.ClazzQueryParam;
import net.jrz.tlias.pojo.PageResult;
import net.jrz.tlias.pojo.Result;
import net.jrz.tlias.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result list(ClazzQueryParam clazzQueryParam){
        log.info("条件分页查询: {}", clazzQueryParam );
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Clazz clazz){
        log.info("添加班级: {}", clazz);
        clazzService.save(clazz);
        return Result.success();
    }


    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
       log.info("获取班级信息，id = {}", id);
        Clazz clazz = clazzService.getInfo(id);
        return Result.success(clazz);
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("更新班级信息，{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) throws DefaultException {
        log.info("删除班级，ids={}", ids);
        clazzService.delete(ids);
        return Result.success();
    }
}
