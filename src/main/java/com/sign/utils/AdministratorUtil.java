package com.sign.utils;

import com.sign.constant.ExamInformation;
import com.sign.entity.MZDM;
import com.sign.entity.RegistrationForm;
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
    public static ModelAndView findStuInformationById(RegistrationFormAddition stuAdd, User user) {
        ModelAndView mv = new ModelAndView();
        if (null == stuAdd  || null == user || !"ROLE_USER".equals(user.getRole())) {
            mv.addObject("Msgnull", "查无此人");
            mv.setViewName("emp/listcx");
            return mv;
        }
        findNationCodeAndPoliticsStatus(stuAdd.getRegistrationForm());
        mv.addObject("stu", stuAdd);
        mv.addObject("zh", user);
        mv.setViewName("emp/listId");
        return mv;
    }


    public static ModelAndView findStuInformation(List<RegistrationFormAddition> stuAdds) {
        ModelAndView mv = new ModelAndView();
        stuAdds.forEach(stu -> findNationCodeAndPoliticsStatus(stu.getRegistrationForm()));
        mv.addObject("stus", stuAdds);
        mv.setViewName("emp/list");
        return mv;
    }


    private static void findNationCodeAndPoliticsStatus(RegistrationForm registrationForm){
        List<MZDM> nationCode = ExamInformation.nationCode;
        List<ZZMMDM> politicsStatus = ExamInformation.politicsStatus;
        if (registrationForm != null) {
            for (MZDM mzdm : nationCode) {
                if (registrationForm.getNation().equals(mzdm.getMzdm())) {
                    registrationForm.setNation(mzdm.getMzmc());
                    break;
                }
            }
            for (ZZMMDM zzmmdm : politicsStatus) {
                if (registrationForm.getPc().equals(zzmmdm.getZzmmdm())) {
                    registrationForm.setPc(zzmmdm.getZzmmmc());
                    break;
                }
            }
        }
    }
}
