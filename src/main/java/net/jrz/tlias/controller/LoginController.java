package net.jrz.tlias.controller;

import lombok.extern.slf4j.Slf4j;
import net.jrz.tlias.pojo.Emp;
import net.jrz.tlias.pojo.LoginInfo;
import net.jrz.tlias.pojo.Result;
import net.jrz.tlias.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    private EmpService empService;

    @PostMapping
    public Result login(@RequestBody Emp emp) {
        log.info("员工请求登录，{}", emp);
        LoginInfo empLoginInfo = empService.login(emp);
        if (empLoginInfo != null) {
            return Result.success(empLoginInfo);
        }
        return Result.error("用户名或者密码错误");
    }
}
