package com.sign.utils;

import com.sign.constant.ExamInformation;
import com.sign.entity.MZDM;
import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.entity.ZZMMDM;
import com.sign.vo.RegistrationFormVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author czz
 * @version 1.0
 * @date 2021/1/12 21:46
 */
public class SignUpUtil {

    public static void picUploadDecorateMV(Integer picSize, ModelAndView mv){
        if (picSize > 204800 || 6144 > picSize) {
            mv.addObject("zpMsg", "照片大小有误");
            mv.setViewName("emp/zp");
        }
    }

    public static ModelAndView insertStudentDecorateMV(boolean var1, boolean var2, RegistrationFormVo collect){
        ModelAndView mv = new ModelAndView();
        if (var1) {
            if (var2) {
                mv.addObject("collect", collect);
                System.out.println("报名成功");
                mv.setViewName("emp/addsuc");
            } else {
                System.out.println("报名失败");
                mv.addObject("errorMsg", "报名失败，请检查填写信息");
                mv.setViewName("dashboard");
            }
        } else {
            System.out.println("报名失败");
            mv.addObject("errorMsg", "报名失败，请检查填写信息");
            mv.setViewName("dashboard");
        }
        return mv;
    }

    public static ModelAndView updateAfterDecorateMV(Integer updateStatus, Integer updateAddStatus){
        ModelAndView mv = new ModelAndView();
//        System.out.println("------修改2-----" + collect);
        if (updateStatus == 1) {
            if (updateAddStatus == 1) {
                System.out.println("修改成功");
                mv.addObject("suc", "修改成功");
            } else {
                System.out.println("修改失败");
                mv.addObject("suc", "修改失败，检查信息");
            }
        } else {
            System.out.println("修改失败");
            mv.addObject("suc", "修改失败，检查信息");
        }
        mv.setViewName("dashboard");
        return mv;
    }


    public static ModelAndView updateBeforeDecorateMV(RegistrationFormAddition addstudent){
        ModelAndView mv = new ModelAndView();
        if (addstudent == null) {
            System.out.println("请先报名");
            mv.setViewName("emp/updatefalse");
        } else {
            mv.addObject("student", addstudent.getRegistrationForm());
            mv.addObject("addstudent", addstudent);
            initInformation(mv);
            mv.setViewName("emp/update");
        }
        return mv;
    }

    /**
     * 信息判断
     * @param student
     * @param addstudent
     * @return
     */
    public static ModelAndView findInformationDecorateMV(RegistrationForm student, RegistrationFormAddition addstudent){
        ModelAndView mv = new ModelAndView();
        if (addstudent == null) {
            System.out.println("请先报名");
            mv.setViewName("emp/updatefalse");
        } else {
            String pName = ExamInformation.userDetails.getUsername() + ".jpg";
            //localhost:8080:/static/sfz/ URL+"/static/sfz/"

            mv.addObject("zp", "/imagesSFZ/" + pName);

            String nationCode = SignUpUtil.findNationCode(student);
            if (null != nationCode) {
                mv.addObject("mz", nationCode);
            }
            String politicsStatus = SignUpUtil.findPoliticsStatus(student);
            if (null != politicsStatus) {
                mv.addObject("zz", politicsStatus);
            }
            mv.addObject("collect", student);
            mv.setViewName("table/complete");
        }
        return mv;
    }

    /**
     * 注册报名页面预处理
     * @return
     */
    public static ModelAndView examRegistrationDecorateMV(RegistrationFormAddition formAddition){
        ModelAndView mv = new ModelAndView();
        if (formAddition != null) {
            System.out.println("已报名");
            mv.setViewName("emp/addfalse");
        } else {
            System.out.println("考生报名");
            initInformation(mv);
            mv.addObject("id", ExamInformation.userDetails.getUsername());
            mv.setViewName("emp/add");
        }
        return mv;
    }


    private static void initInformation(ModelAndView mv) {
        mv.addObject("mzdm", ExamInformation.nationCode);
        mv.addObject("zzmmdm", ExamInformation.politicsStatus);
        mv.addObject("byxxdm", ExamInformation.graduation);
        mv.addObject("bkzy", ExamInformation.enterMajor);
    }

    public static String findNationCode(RegistrationForm student){
        for (MZDM mzdm: ExamInformation.nationCode) {
            if (student.getNation().equals(mzdm.getMzdm())){
                return mzdm.getMzmc();
            }
        }
        return null;
    }

    public static String findPoliticsStatus(RegistrationForm student){
        for (ZZMMDM zzmmdm: ExamInformation.politicsStatus) {
            if (student.getPc().equals(zzmmdm.getZzmmdm())){
                return zzmmdm.getZzmmmc();
            }
        }
        return null;
    }


    private static List<?> switchClass(Class<?> clazz){
        switch (clazz.getSimpleName()){
            case "BKZY" : return ExamInformation.enterMajor;
            case "BYXXDM" : return ExamInformation.graduation;
            case "ZZMMDM" : return ExamInformation.politicsStatus;
            case "MZDM" : return ExamInformation.nationCode;
        }
        return Collections.EMPTY_LIST;
    }

    public static void ifNotConsumer(Consumer consumer,Object o){
        consumer.accept(o);
    }
}
