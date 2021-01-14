package com.sign.controller;

import com.alibaba.excel.EasyExcel;
import com.sign.entity.CollectExcl;
import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.entity.User;
import com.sign.service.IRegisterService;
import com.sign.service.ISignUpService;
import com.sign.service.WxPayOrderService;
import com.sign.utils.AdministratorUtil;
import com.sign.utils.FilePathUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdministratorController {
    @Resource
    ISignUpService iSignUpService;

    @Resource
    IRegisterService iRegisterService;

    @Resource
    private WxPayOrderService payOrderService;


    //管理员登陆
    /*@PostMapping("/loginAdm")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        Admin admin=iAdminService.findAdmin();
        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(30);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (admin.getAcc().equals(username) && admin.getPassword().equals(password)) {
            //密码正确，设置session
            session.setAttribute("loginAdmMsg", "adminlogin");
            mv.setViewName("redirect:/mainAd");
        } else {
            //密码错误返回登录页面
            mv.addObject("msg", "用户名或密码有误");
            mv.setViewName("admin/adminlogin");
        }
        return mv;
    }*/

    //管理员密码修改
    /*@PostMapping("/adminUpdate")
    public ModelAndView adminUpdate(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        Admin admin=iAdminService.findAdmin();
//        System.out.println(admin.getPassword()+"-----"+request.getParameter("password"));
        if (admin.getPassword().equals(request.getParameter("password")))
        {
            Admin admin1=new Admin();
            admin1.setAcc("admin");
            admin1.setPassword(request.getParameter("password1"));
            iAdminService.updateAdmin(admin1);
            mv.addObject("Msg","密码修改成功");
        }else {
            mv.addObject("Msg","密码错误");
        }
        mv.setViewName("admin/adminUpdate");
        return mv;
    }*/


    //信息查询
    @GetMapping("/xinxi")
    public ModelAndView findStuInformation() {
        //缺少分页
        List<RegistrationFormAddition> stuAdds = iSignUpService.associationFind();
        return AdministratorUtil.findStuInformation(stuAdds);
    }


    @GetMapping("/fileload")
    public String adFileLoad(Model model) {
//        String fileName = request.getParameter("fpath");
//        System.out.println(fileName);
        String filepath = "\\static\\files\\";
        List<RegistrationForm> registrationForms = iSignUpService.findStudent();
        List<RegistrationFormAddition> registrationFormAdditionList = iSignUpService.associationFind();
        Map<String, String> map = null;

        for (RegistrationForm registrationForm : registrationForms) {
            try {
                map = payOrderService.orderQuery(registrationForm.getDid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            registrationForm.setPay(map.get("trade_state_desc"));
        }
        for (RegistrationFormAddition registrationFormAddition : registrationFormAdditionList) {
            for (RegistrationForm registrationForm : registrationForms) {
                if (registrationFormAddition.getDid().equals(registrationForm.getDid())) {
                    registrationForm.setCard(registrationFormAddition.getCard());
                    registrationForm.setExam(registrationFormAddition.getExam());
                    registrationForm.setSoldier(registrationFormAddition.getSoldier());
                }
            }
        }
//        String fileName="C:\\nclg.xls";
        EasyExcel.write(FilePathUtils.getFileName(filepath) + "nclg.xls", CollectExcl.class).sheet("南昌理工考生信息").doWrite(registrationForms);

//        try {
//
//            FileUtils.fileUnload("nclg.xls",response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        model.addAttribute("suc", "文件导出成功");
        return "emp/file";
    }


//    @GetMapping("/zh")
//    public String zh(){
//        return "emp/listcx";
//    }

    @PostMapping("/cxId")
    public ModelAndView findStuInformationById(@RequestParam("id") String id) {
//        RegistrationForm stu = iSignUpService.selectStudentById(id);
        RegistrationFormAddition stuRegistrationFormAddition = iSignUpService.associationSecFind(id);
//        System.out.println(stuRegistrationFormAddition.getRegistrationForm());
        User user = iRegisterService.registerFindById(id);
        return AdministratorUtil.findStuInformationById(stuRegistrationFormAddition, user);
    }

    @PostMapping("/updatezh")
    public String updateStuUser(User user, Model model) {
        Integer update = iRegisterService.registerUpdate(user);
        if (update == 1) {
            model.addAttribute("zhMsg", "修改成功");
        } else {
            model.addAttribute("zhMsg", "修改失败");
        }
        return "emp/listcx";
    }


}