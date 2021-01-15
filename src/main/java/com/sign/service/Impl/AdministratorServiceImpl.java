package com.sign.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.sign.entity.CollectExcl;
import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.service.IAdministratorService;
import com.sign.service.ISignUpService;
import com.sign.service.WxPayOrderService;
import com.sign.utils.FilePathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : czz
 * @version : 1.0.0
 * @create : 2021-01-15 11:17:00
 * @description :
 */
@Service
@Slf4j
public class AdministratorServiceImpl implements IAdministratorService {
    @Autowired
    ISignUpService iSignUpService;

    @Autowired
    private WxPayOrderService payOrderService;

    @Override
    public boolean exportExl() {
        String filepath = "\\static\\files\\";
        List<RegistrationForm> registrationForms = iSignUpService.findStudent();
        List<RegistrationFormAddition> registrationFormAdditionList = iSignUpService.associationFind();
        Map<String, String> map = null;
        try {
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
            EasyExcel.write(FilePathUtils.getFileName(filepath) + "nclg.xls", CollectExcl.class).sheet("南昌理工考生信息").doWrite(registrationForms);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
