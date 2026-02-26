package net.jrz.tlias.mapper;

import net.jrz.tlias.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    void insertBatch(List<EmpExpr> empExprList);

    void deleteByEmpIds(List<Integer> empIds);
}
