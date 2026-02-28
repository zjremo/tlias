package net.jrz.tlias;

import net.jrz.tlias.controller.DeptController;
import net.jrz.tlias.mapper.EmpMapper;
import net.jrz.tlias.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TliasApplicationTests {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private ApplicationContext applicationContext; // IOC容器对象

    @Test
    public void testList(){
       List<Emp> empList = empMapper.list(1, 10);
       empList.forEach(System.out::println);
    }

    // bean的作用域测试
    @Test
    public void testBeanScope(){
        int i = 0;
        while(i < 10){
            DeptController deptController = applicationContext.getBean(DeptController.class);
            System.out.println(deptController);
            ++i;
        }
    }

}
