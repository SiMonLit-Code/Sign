package com.sign.controller;

import com.sign.constant.ExamInformation;
import com.sign.entity.HJDM;
import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.service.IDMService;
import com.sign.service.ISignUpService;
import com.sign.utils.SignUpUtil;
import com.sign.vo.RegistrationFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public ISignUpService iSignUpService;

    @Resource
    IDMService idmService;

    @Value("${pic.url}")
    private String picUrl;

    /**
     * 报名页面(将民族代码，政治面貌代码，毕业学校代码返回到页面)
     *
     * @return
     */
    @GetMapping("/ksbm")
    public ModelAndView examRegistration() {
        RegistrationFormAddition formAddition = iSignUpService.associationSecFind(ExamInformation.userDetails.getUsername());
        return SignUpUtil.examRegistrationDecorateMV(formAddition);
    }

    /**
     * 查询信息判断
     *
     * @return
     */
    @GetMapping("/xinxi")
    public ModelAndView findInformation() {
        String username = ExamInformation.userDetails.getUsername();
        RegistrationForm student = iSignUpService.selectStudentById(username);
        RegistrationFormAddition addstudent = iSignUpService.associationSecFind(username);
        return SignUpUtil.findInformationDecorateMV(student, addstudent, picUrl);
    }

    /**
     * 修改前调用信息
     *
     * @return
     */
    @GetMapping("/xg")
    public ModelAndView updateBefore() {
        RegistrationFormAddition addstudent = iSignUpService.associationSecFind(ExamInformation.userDetails.getUsername());
        return SignUpUtil.updateBeforeDecorateMV(addstudent);
    }


    /**
     * 修改
     *
     * @param collect
     * @param cidName
     * @return
     */
    @PostMapping("/update")
    public ModelAndView updateAfter(RegistrationFormVo collect, @RequestParam("cidname") String cidName) {
        String[] str = cidName.split(" ");
        collect.setCid(Integer.parseInt(str[0]));
        collect.setCname(str[1]);
        Integer updateStatus = iSignUpService.updateStudent(collect);
        Integer updateAddStatus = iSignUpService.updateSecStudent(collect);
        return SignUpUtil.updateAfterDecorateMV(updateStatus, updateAddStatus);
    }


    /**
     * 报名
     *
     * @param collect
     * @param cidName
     * @return
     */
    @PostMapping("/insert")
    public ModelAndView insertStudent(RegistrationFormVo collect, @RequestParam("cidname") String cidName) {
        String[] str = cidName.split(" ");
        collect.setCid(Integer.parseInt(str[0]));
        collect.setCname(str[1]);
//        HttpSession session = request.getSession();
//        session.setAttribute("collect", collect);
//        System.out.println(collect);
        boolean var1 = iSignUpService.insertStudent(collect);
        boolean var2 = iSignUpService.insertSecStudent(collect);
        return SignUpUtil.insertStudentDecorateMV(var1, var2, collect);
    }

    /**
     * 上传地址
     * <p>
     * 执行上传
     */
    @RequestMapping("/upload")
    public ModelAndView picUpload(@RequestParam("file") MultipartFile file) {
        ModelAndView mv = new ModelAndView();
        String filename = file.getOriginalFilename();
        if (!SignUpUtil.picJudgeSizeAndName(filename, file.getSize(), mv)) {
            return mv;
        }
        // 获取上传文件名

        String ext = filename.substring(filename.indexOf(".") + 1);
        if ("jpg".equals(ext) || "JPG".equals(ext)) {
            File picFile = SignUpUtil.picPathFile(ext);
            try {
                // 写入文件
//                Thumbnails.of((File) file).size(480,640).toFile((File) file);
                file.transferTo(picFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 将src路径发送至html页面
//            model.addAttribute("filename", "/store/rotPhoto/" + pName);
//            session.setAttribute("zp", picFile.getAbsolutePath());
//            model.addAttribute("collect", session.getAttribute("collect"));
            mv.addObject("zpMsg", "照片上传成功");
        } else {
            mv.addObject("zpMsg", "照片格式错误");
        }
        mv.setViewName("emp/zp");
        return mv;
    }

    @GetMapping("/zp")
    public String zp(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        RegistrationFormAddition addstudent = iSignUpService.associationSecFind(ExamInformation.userDetails.getUsername());
        if (addstudent == null) {
//            System.out.println("请先报名");
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
