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
public class CollectExcl {
    @ExcelProperty(value = "考生报名序号",index = 0)
    private Integer sid;
    @ExcelProperty(value = "考生姓名",index = 1)
    private String sname;
    @ExcelProperty(value = "性别",index = 2)
    private Integer gender;
    @ExcelProperty(value = "民族",index = 3)
    private String nation;
    //    @DateTimeFormat(pattern = "yyyyMMdd")
    @ExcelProperty(value = "出生日期",index = 4)
    private String birth;
    @ExcelProperty(value = "政治面貌",index = 5)
    private String pc;
    @ExcelProperty(value = "专科所学专业",index = 6)
    private String cmajor;
    @ExcelProperty(value = "报考专业",index = 7)
    private String gmajor;
    @ExcelProperty(value = "录检表内学生高考考生号",index = 8)
    private String gid;
    @ExcelProperty(value = "身份证号码",index = 9)
    private String did;
    @ExcelProperty(value = "学生详细通讯地址",index = 10)
    private String addr;
    @ExcelProperty(value = "邮编",index = 11)
    private String pos;
    @ExcelProperty(value = "本人联系方式",index = 12)
    private String person;
    @ExcelProperty(value = "父母联系电话",index = 13)
    private String parent;
    @ExcelProperty(value = "考生类别代码",index = 14)
    private Integer kid;
    @ExcelProperty(value = "毕业学校代码",index = 15)
    private Integer cid;
    @ExcelProperty(value = "毕业学校名称",index = 16)
    private String cname;
    @ExcelProperty(value = "户籍代码",index = 17)
    private String nid;
    @ExcelProperty(value = "推荐老师姓名",index = 18)
    private String tname;
    @ExcelProperty(value = "推荐老师电话",index = 19)
    private String tel;
    @ExcelProperty(value = "缴费状态",index = 20)
    private String pay;
    @ExcelProperty(value = "是否建档立卡",index = 21)
    private String card;
    @ExcelProperty(value = "是否退役士兵",index = 22)
    private String soldier;
    @ExcelProperty(value = "考试专业选择",index = 23)
    private String exam;
}
