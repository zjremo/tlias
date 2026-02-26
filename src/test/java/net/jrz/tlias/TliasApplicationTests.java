package net.jrz.tlias;

import net.jrz.tlias.mapper.EmpMapper;
import net.jrz.tlias.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TliasApplicationTests {

    @Autowired
    private EmpMapper empMapper;

    @Test
    public void testList(){
       List<Emp> empList = empMapper.list(1, 10);
       empList.forEach(System.out::println);
    }

}
