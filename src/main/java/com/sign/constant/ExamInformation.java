package com.sign.constant;

import com.sign.entity.*;
import com.sign.service.IDMService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author czz
 * @version 1.0
 * @date 2021/1/12 21:31
 */
@Component
public class ExamInformation {
    @Autowired
    IDMService idmService;
    /** 政治面貌 */
    public static List<ZZMMDM> politicsStatus = new ArrayList<>();
    /** 民族代码 */
    public static List<MZDM> nationCode = new ArrayList<>();
    /** 毕业院校 */
    public static List<BYXXDM> graduation = new ArrayList<>();
    /** 报考专业 */
    public static List<BKZY> enterMajor = new ArrayList<>();
    /** 户籍代码 */
    public static List<HJDM> censusRegister  = new ArrayList<>();

    public static UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @PostConstruct
    private void ExamInformationInit(){
        ExamInformation.nationCode = idmService.findMZDM();
        ExamInformation.politicsStatus = idmService.findZZMMDM();
        ExamInformation.graduation = idmService.findBYXXDM();
        ExamInformation.enterMajor = idmService.findBKZY();
        ExamInformation.censusRegister  = idmService.findHJDM();
    }

}
