package com.sign.utils;

import com.sign.constant.ExamInformation;
import com.sign.entity.MZDM;
import com.sign.entity.RegistrationFormAddition;
import com.sign.entity.User;
import com.sign.entity.ZZMMDM;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-14 18:03:00
 * @description :
 */
public class AdministratorUtil {
    public static ModelAndView findStuInformationById(RegistrationFormAddition stuAdd, User user){
        ModelAndView mv = new ModelAndView();
        if (stuAdd == null) {
            mv.addObject("Msgnull", "查无此人");
            mv.setViewName("emp/listcx");
        }
        List<MZDM> mzdms = ExamInformation.nationCode;
        List<ZZMMDM> zzmmdms = ExamInformation.politicsStatus;

        for (MZDM mzdm : mzdms) {
            if (stuAdd.getRegistrationForm().getNation().equals(mzdm.getMzdm())) {
                stuAdd.getRegistrationForm().setNation(mzdm.getMzmc());
                break;
            }
        }
        for (ZZMMDM zzmmdm :
                zzmmdms) {
            if (stuAdd.getRegistrationForm().getPc().equals(zzmmdm.getZzmmdm())) {
                stuAdd.getRegistrationForm().setPc(zzmmdm.getZzmmmc());
                break;
            }
        }
        mv.addObject("stu", stuAdd);
        mv.addObject("zh", user);
        mv.setViewName("emp/listId");
        return mv;
    }


    public static ModelAndView findStuInformation(List<RegistrationFormAddition> stuAdds){
        ModelAndView mv = new ModelAndView();
        List<MZDM> nationCode = ExamInformation.nationCode;
        List<ZZMMDM> politicsStatus = ExamInformation.politicsStatus;
        stuAdds.forEach(stu -> {
            if (stu.getRegistrationForm() != null) {
                for (MZDM mzdm : nationCode) {
                    if (stu.getRegistrationForm().getNation().equals(mzdm.getMzdm())) {
                        stu.getRegistrationForm().setNation(mzdm.getMzmc());
                        break;
                    }
                }
                for (ZZMMDM zzmmdm : politicsStatus) {
                    if (stu.getRegistrationForm().getPc().equals(zzmmdm.getZzmmdm())) {
                        stu.getRegistrationForm().setPc(zzmmdm.getZzmmmc());
                        break;
                    }
                }
            }
        });
        mv.addObject("stus", stuAdds);
        mv.setViewName("emp/list");
        return mv;
    }
}
