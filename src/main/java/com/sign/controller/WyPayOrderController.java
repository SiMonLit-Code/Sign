package com.sign.controller;

import com.alibaba.excel.EasyExcel;
import com.sign.dao.SignUpDao;
import com.sign.entity.*;
import com.sign.service.IRegisterService;
import com.sign.service.ISignUpService;
import com.sign.service.WxPayOrderService;
import com.sign.vo.CollectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleToIntFunction;

/**
 * @author 周志通
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
     *      1. 当前可访问的地址
     */
    @Value("${notify_url}")
    private String url ;

    @PostMapping("/getpayResult")
    public String getpayResult(HttpServletRequest request) throws IOException {

        InputStream inputStream = request.getInputStream() ;
        byte[] b = new byte[1024] ;
        while (inputStream.read(b)!=-1){
            System.out.println("输入流消息："+new String(b)) ;
        }
        System.out.println("回调信息：" + request.getServletContext());
        System.out.println("回调信息：" + request.getQueryString());
        System.out.println("回调信息：" + request.getContextPath());

        return "" ;
    }


    @Autowired
    private WxPayOrderService payOrderService ;

    @Resource
    private IRegisterService iRegisterService;

    @Resource
    private ISignUpService iSignUpService;

//    private

//    @GetMapping({"/","/index"})
//    public String index() {
//        return "index" ;
//    }
    //下订单
    @GetMapping("/order")
    public String order(HttpServletRequest request){
        HttpSession session=request.getSession();
        String id= (String) session.getAttribute("id");
        Collect student = iSignUpService.selectStudentById((String) session.getAttribute("id"));
        if (student == null) {
            System.out.println("请先报名");
            return "emp/updatefalse";
        }
        return "emp/zf" ;
    }

    //管理订单
//    @GetMapping("/orderAd")
//    public String orderAd(){
//        return "emp/zfAd" ;
//    }

    //支付订单
    @PostMapping("/orderSubmit")
    public String order(Product product, HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
        HttpSession session=request.getSession();
        String id= (String) session.getAttribute("id");
//        Collect student = iSignUpService.selectStudentById((String) session.getAttribute("id"));
//        if (student == null) {
//            System.out.println("请先报名");
//            return "emp/updatefalse";
//        }
        product.setOrderNo(id);
        System.out.println(product);
        String s=null;
        try{
             s = payOrderService.unifiedOrder(url, product.getOrderNo(), "13000", "127.0.0.1","专升本缴费", response, request);

        }catch (Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("MsgErro","已支付或订单已关闭");
            return "emp/zf" ;
        }
        if ("".equals(s)){
            System.out.println("下订单成功") ;
        }else{
            System.out.println("下订单失败原因："+s) ;
        }
        return "emp/zf" ;
    }

    //管理员订单文件
//    @PostMapping("/payfile")
//    public String payFile(HttpServletRequest request) {
//        String fileName = request.getParameter("payfile");
//        System.out.println(fileName);
//        List<Product> products=new ArrayList<>();
//        List<Collect> collects=iSignUpService.findStudentdId();
//        System.out.println(collects);
//        Map<String,String> map = null ;
//        for (Collect collect:
//                collects) {
//            Product product=new Product();
//            try {
//                map = payOrderService.orderQuery(collect.getDid()) ;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            product.setRemark(collect.getSid().toString());
//            product.setOrderNo(collect.getDid());//订单号
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
    public String orderQueryAll(){
        List<Collect> collects = iSignUpService.findStudentdId();
        payOrderService.queryAllOrder(collects) ;
        return "redirect:/payment/orderQueryAd" ;
    }


    @Resource
    private SignUpDao signUpDao ;

    //管理员订单查询
    @RequestMapping("/orderQueryAd")
    public String orderQueryAd(Model model){
//        List<Register> accounts=iRegisterService.registerFindAll();
      /*  List<Product> products=new ArrayList<>();
        List<Collect> collects=iSignUpService.findStudentdId();
        System.out.println(collects);
        Map<String,String> map = null ;
        for (Collect collect:
             collects) {
            Product product=new Product();
            try {
                map = payOrderService.orderQuery(collect.getDid()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            for (Collect collect:
//                 collects) {
//                if (collect.getDid()==(register.getAccount().toString())){
//                    product.setRemark(collect.getSid().toString());
//                    break;
//                }
//            }
            product.setRemark(collect.getSid().toString());
            product.setOrderNo(collect.getDid());//订单号
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
        List<Add> adds = signUpDao.associationFind();
        List<Product> products=new ArrayList<>();
        for (Add add : adds){
            products.add(new Product(add.getDid() , ""+add.getCollect().getSid() ,add.getPay())) ;
        }
        model.addAttribute("products" ,products) ;
        return "emp/zfAd" ;
    }

    //订单查询
    @RequestMapping("/orderQuery")
    public String orderQuery(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        String id= (String) session.getAttribute("id");
        Map<String,String> map = null ;
        try {
            map = payOrderService.orderQuery(id) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if ("SUCCESS".equals(map.get("return_code"))){
//            System.out.println("查询成功，结果：");
//
//        }else {
//            System.out.println("查询失败，原因："+map.get("err_code_des"));
//        }
        model.addAttribute("out_trade_no",map.get("out_trade_no")) ;    //订单号
        model.addAttribute("trade_state_desc",map.get("trade_state_desc")) ;    //订单状态信息
        model.addAttribute("err_code_des",map.get("err_code_des")) ;    //订单状态信息
        return "emp/zf" ;
    }

    //按照身份证查询订单
    @RequestMapping("/orderQueryid")
    public String orderQueryByid(HttpServletRequest request,Model model){
        String id =request.getParameter("payid");
        List<Product> products=new ArrayList<>();
        Product product=new Product();
        Map<String,String> map = null ;
        try {
            map = payOrderService.orderQuery(id) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if ("SUCCESS".equals(map.get("return_code"))){
//            System.out.println("查询成功，结果：");
//
//        }else {
//            System.out.println("查询失败，原因："+map.get("err_code_des"));
//        }
        Collect collect=iSignUpService.selectStudentById(id);
        product.setRemark(collect.getSid().toString());
        product.setOrderNo(id);//订单号
        product.setPrice(map.get("trade_state_desc")); //订单状态信息
        if (product!=null){
            products.add(product);
        }
        model.addAttribute("products",products) ;
        return "emp/zfAd" ;
    }


    //订单关闭
    @PostMapping("/orderClose")
    public String orderClose(HttpServletRequest request,Model model){
        String out_trade_no=request.getParameter("id");
        Map<String, String> map = null;
        try {
            map = payOrderService.closeOrder(out_trade_no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map==null){
            model.addAttribute("result_code","订单不存在") ;
        }
        if ("SUCCESS".equals(map.get("return_code"))){
            System.out.println("关闭成功：");
        }else {
            System.out.println("关闭失败：原因："+map.get("err_code_des"));
        }
        model.addAttribute("result_code",map.get("result_code")) ;
        return "adminboard" ;
    }


}
