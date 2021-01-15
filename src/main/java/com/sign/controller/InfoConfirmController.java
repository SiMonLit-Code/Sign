package com.sign.controller;

import com.sign.entity.ConfirmInfo;
import com.sign.service.IConfirmInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class InfoConfirmController {
    @Resource
    IConfirmInfoService iConfirmInfoService;


    @PostMapping("/confirmAf")
    public String findConfirmInfoById(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");
        model.addAttribute("confirmInfo", iConfirmInfoService.findConfirmInfoById(id));
        return "emp/confirm";
    }

    @PostMapping("/confirmBe")
    public String ConfirmInfoBe(ConfirmInfo confirmInfo) {
//        model.addAttribute("confirmInfo",iConfirmInfoService.findConfirmInfoById(id));
        return "emp/confirm";
    }

    @PostMapping("/InfoUpdate")
    public Integer updateConfirmInfoById(String BKZY, String CINFO) {
        return iConfirmInfoService.updateConfirmInfoById(BKZY, CINFO);
    }

}
