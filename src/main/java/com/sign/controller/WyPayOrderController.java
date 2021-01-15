package com.sign.controller;

import com.sign.constant.ExamInformation;
import com.sign.dao.SignUpDao;
import com.sign.entity.*;
import com.sign.service.IRegisterService;
import com.sign.service.ISignUpService;
import com.sign.service.WxPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @className WyPayOrderController
 * @description TODO
 * @date 2020/5/21 15:20
 */
@Controller
@RequestMapping("/payment")
public class WyPayOrderController {
    /**
     * 指定回调地址
     * 实际开发中:
     * 1. 当前可访问的地址
     */
    @Value("${notify_url}")
    private String url;

    @Autowired
    private WxPayOrderService payOrderService;

    @Autowired
    private ISignUpService iSignUpService;

    @PostMapping("/getpayResult")
    public String getpayResult(HttpServletRequest request) throws IOException {

        InputStream inputStream = request.getInputStream();
        byte[] b = new byte[1024];
        while (inputStream.read(b) != -1) {
            System.out.println("输入流消息：" + new String(b));
        }
        System.out.println("回调信息：" + request.getServletContext());
        System.out.println("回调信息：" + request.getQueryString());
        System.out.println("回调信息：" + request.getContextPath());

        return "";
    }



    //    @GetMapping({"/","/index"})
//    public String index() {
//        return "index" ;
//    }
    //下订单
    @GetMapping("/order")
    public String order() {
        RegistrationForm student = iSignUpService.selectStudentById(ExamInformation.userDetails.getUsername());
        if (student == null) {
            System.out.println("请先报名");
            return "emp/updatefalse";
        }
        return "emp/payment";
    }

    //管理订单
//    @GetMapping("/orderAd")
//    public String orderAd(){
//        return "emp/zfAd" ;
//    }

    //支付订单
    @PostMapping("/orderSubmit")
    public String order(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
        product.setOrderNo(ExamInformation.userDetails.getUsername());
        System.out.println(product);
        String msg = null;
        try {
            msg = payOrderService.unifiedOrder(url, product.getOrderNo(), "13000", "127.0.0.1", "专升本缴费", response, request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("MsgErro", "已支付或订单已关闭");
            return "emp/payment";
        }
        if ("".equals(msg)) {
            System.out.println("下订单成功");
        } else {
            System.out.println("下订单失败原因：" + msg);
        }
        return "emp/payment";
    }

    //管理员订单文件
//    @PostMapping("/payfile")
//    public String payFile(HttpServletRequest request) {
//        String fileName = request.getParameter("payfile");
//        System.out.println(fileName);
//        List<Product> products=new ArrayList<>();
//        List<RegistrationForm> collects=iSignUpService.findStudentdId();
//        System.out.println(collects);
//        Map<String,String> map = null ;
//        for (RegistrationForm registrationForm:
//                collects) {
//            Product product=new Product();
//            try {
//                map = payOrderService.orderQuery(registrationForm.getDid()) ;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            product.setRemark(registrationForm.getSid().toString());
//            product.setOrderNo(registrationForm.getDid());//订单号
//            product.setPrice(map.get("trade_state_desc")); //订单状态信息
//            System.out.println(product);
//            if (product!=null){
//                products.add(product);
//            }
//        }
//
////        String fileName="C:\\nclg.xls";
//        EasyExcel.write(fileName, Product.class).sheet("南昌理工考生缴费信息").doWrite(products);
//        return "redirect:/mainAd";
//    }
    //查询所有
    @RequestMapping("/orderQueryAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String orderQueryAll() {
        List<RegistrationForm> registrationForms = iSignUpService.findStudentdId();
        payOrderService.queryAllOrder(registrationForms);
        return "redirect:/payment/orderQueryAd";
    }


    @Resource
    private SignUpDao signUpDao;

    //管理员订单查询
    @RequestMapping("/orderQueryAd")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String orderQueryAd(Model model) {
//        List<Register> accounts=iRegisterService.registerFindAll();
      /*  List<Product> products=new ArrayList<>();
        List<RegistrationForm> collects=iSignUpService.findStudentdId();
        System.out.println(collects);
        Map<String,String> map = null ;
        for (RegistrationForm registrationForm:
             collects) {
            Product product=new Product();
            try {
                map = payOrderService.orderQuery(registrationForm.getDid()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            for (RegistrationForm registrationForm:
//                 collects) {
//                if (registrationForm.getDid()==(register.getAccount().toString())){
//                    product.setRemark(registrationForm.getSid().toString());
//                    break;
//                }
//            }
            product.setRemark(registrationForm.getSid().toString());
            product.setOrderNo(registrationForm.getDid());//订单号
            product.setPrice(map.get("trade_state_desc")); //订单状态信息
//            System.out.println(product);
            if (product!=null){
                products.add(product);
            }
        }
//
//        if ("SUCCESS".equals(map.get("return_code"))){
//            System.out.println("查询成功，结果："+map.get("err_code_des"));
//
//        }else {
//            System.out.println("查询失败，原因："+map.get("err_code_des"));
//        }
//        model.addAttribute("out_trade_no",map.get("out_trade_no")) ;    //订单号
//        model.addAttribute("trade_state_desc",map.get("trade_state_desc")) ;    //订单状态信息
        model.addAttribute("products",products) ;*/
        List<RegistrationFormAddition> registrationFormAdditions = signUpDao.associationFind();
        List<Product> products = new ArrayList<>();
        for (RegistrationFormAddition registrationFormAddition : registrationFormAdditions) {
            products.add(new Product(registrationFormAddition.getDid(), "" + registrationFormAddition.getRegistrationForm().getSid(), registrationFormAddition.getPay()));
        }
        model.addAttribute("products", products);
        return "emp/zfAd";
    }

    //订单查询
    @RequestMapping("/orderQuery")
    public String orderQuery(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");
        Map<String, String> map = null;
        try {
            map = payOrderService.orderQuery(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if ("SUCCESS".equals(map.get("return_code"))){
//            System.out.println("查询成功，结果：");
//
//        }else {
//            System.out.println("查询失败，原因："+map.get("err_code_des"));
//        }
        model.addAttribute("out_trade_no", map.get("out_trade_no"));    //订单号
        model.addAttribute("trade_state_desc", map.get("trade_state_desc"));    //订单状态信息
        model.addAttribute("err_code_des", map.get("err_code_des"));    //订单状态信息
        return "emp/payment";
    }

    //按照身份证查询订单
    @RequestMapping("/orderQueryid")
    public String orderQueryByid(HttpServletRequest request, Model model) {
        String id = request.getParameter("payid");
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        Map<String, String> map = null;
        try {
            map = payOrderService.orderQuery(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if ("SUCCESS".equals(map.get("return_code"))){
//            System.out.println("查询成功，结果：");
//
//        }else {
//            System.out.println("查询失败，原因："+map.get("err_code_des"));
//        }
        RegistrationForm registrationForm = iSignUpService.selectStudentById(id);
        product.setRemark(registrationForm.getSid().toString());
        product.setOrderNo(id);//订单号
        product.setPrice(map.get("trade_state_desc")); //订单状态信息
        if (product != null) {
            products.add(product);
        }
        model.addAttribute("products", products);
        return "emp/zfAd";
    }


    //订单关闭
    @PostMapping("/orderClose")
    public String orderClose(HttpServletRequest request, Model model) {
        String out_trade_no = request.getParameter("id");
        Map<String, String> map = null;
        try {
            map = payOrderService.closeOrder(out_trade_no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map == null) {
            model.addAttribute("result_code", "订单不存在");
        }
        if ("SUCCESS".equals(map.get("return_code"))) {
            System.out.println("关闭成功：");
        } else {
            System.out.println("关闭失败：原因：" + map.get("err_code_des"));
        }
        model.addAttribute("result_code", map.get("result_code"));
        return "adminboard";
    }


}
