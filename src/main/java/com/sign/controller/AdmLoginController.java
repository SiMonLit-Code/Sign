package com.sign.controller;

import com.alibaba.excel.EasyExcel;
import com.sign.entity.*;
import com.sign.service.*;
import com.sign.utils.FilePathUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AdmLoginController {
    @Resource
    ISignUpService iSignUpService;

    @Resource
    IDMService idmService;

    @Resource
    IRegisterService iRegisterService;

    @Resource
    private WxPayOrderService payOrderService ;

    @Resource
    IAdminService iAdminService;

    //管理员登陆
    @PostMapping("/loginAdm")
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
    }

    //管理员密码修改
    @PostMapping("/adminUpdate")
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
    }

    //跳转修改页面
    @GetMapping("/adminUpdatePage")
    public String adminUpdatePage(){
        return "admin/adminUpdate";
    }

    @GetMapping("/enter")
    public String enterAdm() {
        return "admin/adminlogin";
    }

    //信息查询
    @GetMapping("/Adxinxi")
    public ModelAndView adXinxi() {
        ModelAndView mv = new ModelAndView();
//        List<RegistrationForm> stus = iSignUpService.findStudent();
        List<Add> addStus=iSignUpService.associationFind();
        List<MZDM> mzdms = idmService.findMZDM();
        List<ZZMMDM> zzmmdms = idmService.findZZMMDM();

        for (Add stu :
                addStus) {
            if(stu.getRegistrationForm()!=null){
                for (MZDM mzdm :
                        mzdms) {
                    if (stu.getRegistrationForm().getNation().equals(mzdm.getMzdm())) {
                        stu.getRegistrationForm().setNation(mzdm.getMzmc());
                        break;
                    }
                }
                for (ZZMMDM zzmmdm :
                        zzmmdms) {
                    if (stu.getRegistrationForm().getPc().equals(zzmmdm.getZzmmdm())) {
                        stu.getRegistrationForm().setPc(zzmmdm.getZzmmmc());
                        break;
                    }
                }
            }
            System.out.println(stu);

        }
        mv.addObject("stus", addStus);
        mv.setViewName("emp/list");
        return mv;
    }


    @GetMapping("/file")
    public String adFile(HttpServletRequest request) {
        return "emp/file";
    }

    @GetMapping("/fileload")
    public String adFileLoad(HttpServletResponse response,Model model) {
//        String fileName = request.getParameter("fpath");
//        System.out.println(fileName);
        String filepath="\\static\\files\\";
        List<RegistrationForm> registrationForms = iSignUpService.findStudent();
        List<Add> addList=iSignUpService.associationFind();
        Map<String,String> map = null ;

        for (RegistrationForm registrationForm :
                registrationForms) {
            try {
                map = payOrderService.orderQuery(registrationForm.getDid()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            registrationForm.setPay(map.get("trade_state_desc"));
        }
        for (Add add:
             addList) {
            for (RegistrationForm registrationForm :
                    registrationForms){
                if (add.getDid().equals(registrationForm.getDid())){
                    registrationForm.setCard(add.getCard());
                    registrationForm.setExam(add.getExam());
                    registrationForm.setSoldier(add.getSoldier());
                }
            }
        }


//        String fileName="C:\\nclg.xls";

        EasyExcel.write(FilePathUtils.getFileName(filepath)+"nclg.xls", CollectExcl.class).sheet("南昌理工考生信息").doWrite(registrationForms);

//        try {
//
//            FileUtils.fileUnload("nclg.xls",response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        model.addAttribute("suc","文件导出成功");
        return "emp/file";
    }

    @GetMapping("/cx")
    public String xc() {
        return "emp/listcx";
    }

//    @GetMapping("/zh")
//    public String zh(){
//        return "emp/listcx";
//    }

    @PostMapping("/cxId")
    public String xcId(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
//        RegistrationForm stu = iSignUpService.selectStudentById(id);
        Add stuAdd=iSignUpService.associationSecFind(id);
        System.out.println(stuAdd.getRegistrationForm());
        if(stuAdd==null){
            model.addAttribute("Msgnull", "查无此人");
            return "emp/listcx";
        }
        User user = iRegisterService.registerFindById(id);
        List<MZDM> mzdms = idmService.findMZDM();
        List<ZZMMDM> zzmmdms = idmService.findZZMMDM();

        for (MZDM mzdm :
                mzdms) {
            if (stuAdd.getRegistrationForm().getNation().toString().equals(mzdm.getMzdm())) {
                stuAdd.getRegistrationForm().setNation(mzdm.getMzmc());
                break;
            }
        }
        for (ZZMMDM zzmmdm :
                zzmmdms) {
            if (stuAdd.getRegistrationForm().getPc().toString().equals(zzmmdm.getZzmmdm())) {
                stuAdd.getRegistrationForm().setPc(zzmmdm.getZzmmmc());
                break;
            }
        }
        model.addAttribute("stu", stuAdd);
        model.addAttribute("zh", user);
        return "emp/listId";
    }

    @PostMapping("/updatezh")
    public String updateZh(User user, Model model) {
        Integer update = iRegisterService.registerUpdate(user);
        if (update == 1) {
            model.addAttribute("zhMsg", "修改成功");
        } else {
            model.addAttribute("zhMsg", "修改失败");
        }
        return "emp/listcx";
    }

    @GetMapping("/returnAd")
    public String returnAdmin(){
        return "admin/adminlogin";
    }
}
