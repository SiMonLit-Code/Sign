package com.sign.utils;

import com.sign.constant.ExamInformation;
import com.sign.entity.RegistrationForm;
import com.sign.entity.MZDM;
import com.sign.entity.ZZMMDM;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author czz
 * @version 1.0
 * @date 2021/1/12 21:46
 */
public class SignUpUtil {

    public static String findNationCode(RegistrationForm student){
        for (MZDM mzdm: ExamInformation.nationCode) {
            if (student.getNation().equals(mzdm.getMzdm())){
                return mzdm.getMzmc();
            }
        }
        return null;
    }

    public static String findPoliticsStatus(RegistrationForm student){
        for (ZZMMDM zzmmdm: ExamInformation.politicsStatus) {
            if (student.getPc().equals(zzmmdm.getZzmmdm())){
                return zzmmdm.getZzmmmc();
            }
        }
        return null;
    }


    private static List<?> switchClass(Class<?> clazz){
        switch (clazz.getSimpleName()){
            case "BKZY" : return ExamInformation.enterMajor;
            case "BYXXDM" : return ExamInformation.graduation;
            case "ZZMMDM" : return ExamInformation.politicsStatus;
            case "MZDM" : return ExamInformation.nationCode;
        }
        return Collections.EMPTY_LIST;
    }

    public static void ifNotConsumer(Consumer consumer,Object o){
        consumer.accept(o);
    }
}
