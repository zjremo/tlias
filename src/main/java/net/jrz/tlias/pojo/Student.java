package net.jrz.tlias.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private String no;
    private Integer gender; // 性别, 1: 男, 2: 女
    private String phone;
    private String idCard;
    private Integer isCollege;
    private String address;
    private Integer degree; // 最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士
    private LocalDate graduationDate;
    private Integer clazzId;
    private Integer violationCount;
    private Integer violationScore;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}