package com.sign.controller;

import com.sign.constant.ExamInformation;
import com.sign.entity.Add;
import com.sign.entity.HJDM;
import com.sign.entity.RegistrationForm;
import com.sign.service.IDMService;
import com.sign.service.ISignUpService;
import com.sign.utils.FilePathUtils;
import com.sign.utils.SignUpUtil;
import com.sign.vo.RegistrationFormVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class SignUpController {

    @Resource
    public ISignUpService iSignUpService;

    @Resource
    IDMService idmService;

    /**
     * 报名页面(将民族代码，政治面貌代码，毕业学校代码返回到页面)
     *
     * @param request
     * @return
     */
    @GetMapping("/ksbm")
    public ModelAndView examRegistration(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mv = new ModelAndView();
        Add addstudent = iSignUpService.associationSecFind((String) session.getAttribute("id"));
        if (addstudent != null) {
            System.out.println("已报名");
            mv.setViewName("emp/addfalse");
        } else {
            System.out.println("考生报名");
            initInformation(mv);
            String id = (String) session.getAttribute("id");
            mv.addObject("id", id);
            mv.setViewName("emp/add");
        }
        return mv;
    }

    private void initInformation(ModelAndView mv) {
        mv.addObject("mzdm", ExamInformation.nationCode);
        mv.addObject("zzmmdm", ExamInformation.politicsStatus);
        mv.addObject("byxxdm", ExamInformation.graduation);
        mv.addObject("bkzy", ExamInformation.enterMajor);
    }

    /**
     * 查询信息判断
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/xinxi")
    public String findInformation(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        RegistrationForm student = iSignUpService.selectStudentById((String) session.getAttribute("id"));
        Add addstudent = iSignUpService.associationSecFind((String) session.getAttribute("id"));
        if (addstudent == null) {
            System.out.println("请先报名");
            return "emp/updatefalse";
        } else {
            String id = (String) session.getAttribute("id");
            String pName = id + ".jpg";
            //localhost:8080:/static/sfz/ URL+"/static/sfz/"

            model.addAttribute("zp", "/imagesSFZ/" + pName);

            String nationCode = SignUpUtil.findNationCode(student);
            if (null != nationCode) {
                model.addAttribute("mz", nationCode);
            }
            String politicsStatus = SignUpUtil.findPoliticsStatus(student);
            if (null != politicsStatus) {
                model.addAttribute("zz", politicsStatus);
            }

            model.addAttribute("collect", student);
            return "table/complete";
        }
    }

    //修改前调用信息
    @GetMapping("/xg")
    public ModelAndView updateBefore(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        Add addstudent = iSignUpService.associationSecFind((String) session.getAttribute("id"));
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

    //修改
    @PostMapping("/update")
    public String updateAfter(RegistrationFormVo collect, HttpServletRequest request, Model model) {
        String[] str = request.getParameter("cidname").split(" ");
        collect.setCid(Integer.parseInt(str[0]));
        collect.setCname(str[1]);
        System.out.println("------修改2-----" + collect);
        if (iSignUpService.updateStudent(collect) == 1) {
            if (iSignUpService.updateSecStudent(collect) == 1) {
                System.out.println("修改成功");
                model.addAttribute("suc", "修改成功");
            } else {
                System.out.println("修改失败");
                model.addAttribute("suc", "修改失败，检查信息");
            }
        } else {
            System.out.println("修改失败");
            model.addAttribute("suc", "修改失败，检查信息");
        }
        return "dashboard";
    }

    //报名
    @PostMapping("/insert")
    public ModelAndView insertStudent(RegistrationFormVo collect, HttpServletRequest request) {
        String[] str = request.getParameter("cidname").split(" ");
        collect.setCid(Integer.parseInt(str[0]));
        collect.setCname(str[1]);
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        session.setAttribute("collect", collect);
        System.out.println(collect);
        boolean flag = iSignUpService.insertStudent(collect);
        if (flag) {
            if (iSignUpService.insertSecStudent(collect)) {
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

    /**
     * 上传地址
     */
    // 执行上传
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.getSize() > 204800 || 6144 > file.getSize()) {
            model.addAttribute("zpMsg", "照片大小有误");
            System.out.println(file.getSize());
            return "emp/zp";
        }

        HttpSession session = request.getSession();
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.indexOf(".") + 1);
        if ("jpg".equals(ext) || "JPG".equals(ext)) {
            // 定义上传文件保存路径//static//sfz//
            String path = FilePathUtils.getFileName("//imagesSFZ//");
//            String path = filePath + "rotPhoto/";
            // 新建文件
            String id = (String) session.getAttribute("id");
            String pName = id + "." + ext;
            File filepath = new File(path, pName);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                // 写入文件
//                Thumbnails.of((File) file).size(480,640).toFile((File) file);
                file.transferTo(new File(path + File.separator + pName));

            } catch (IOException e) {

                e.printStackTrace();

            }
            // 将src路径发送至html页面
//            model.addAttribute("filename", "/store/rotPhoto/" + pName);
            session.setAttribute("zp", path + File.separator + pName);
            model.addAttribute("collect", session.getAttribute("collect"));
            model.addAttribute("zpMsg", "照片上传成功");
            return "emp/zp";
        } else {
            model.addAttribute("zpMsg", "照片格式错误");
            return "emp/zp";
        }
    }

    @GetMapping("/zp")
    public String zp(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("id"));
        Add addstudent = iSignUpService.associationSecFind((String) session.getAttribute("id"));
        if (addstudent == null) {
            System.out.println("请先报名");
            return "emp/updatefalse";
        }
        String zp = (String) session.getAttribute("zp");
        model.addAttribute("zp", zp);
        return "emp/zp";
    }

    @GetMapping("/return")
    public String returnPc() {
        return "redirect:/main";
    }

    @GetMapping("/hjdm")
    public String hjdm(Model model) {
        model.addAttribute("hjdms", ExamInformation.censusRegister);
        return "emp/hjdm";
    }

    @PostMapping("/like")
    public String likeHJDM(Model model, HttpServletRequest request) {
        String hjdmmc = request.getParameter("like");
        List<HJDM> hjdms = idmService.likeHJDM(hjdmmc);
        if (hjdms == null) {
            model.addAttribute("likeMsg", "查无此地区");
        }
        model.addAttribute("hjdms", hjdms);
        return "emp/hjdm";
    }
}
