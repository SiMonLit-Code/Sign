package com.sign.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 报名表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {
    private Integer sid;
    private String sname;
    private Integer gender;
    private String nation;
//    @DateTimeFormat(pattern = "yyyyMMdd")
    private String birth;
    private String pc;
    private String cmajor;
    private String gmajor;
    private String gid;
    private String did;
    private String addr;
    private String pos;
    private String person;
    private String parent;
    private Integer kid;
    private Integer cid;
    private String cname;
    private String nid;
    private String pay;
    private String tname;
    private String tel;
    private String card;
    private String soldier;
    private String exam;
}
