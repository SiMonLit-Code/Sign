package com.sign.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * @author 周志通
 * @version 1.0
 * @className FilePathUtils
 * @description TODO
 * @date 2020/5/21 13:58
 */
public class FilePathUtils {
    /**
     * 二维码保存地址
     */
    private static final String FILEPATH = "//static//images//" ;
    /**
     * 获取项目的路径
     * @return
     */
    public static String getFileName(){
        ApplicationHome home = new ApplicationHome(FilePathUtils.class);
        String filePath = home.getSource().getParent()+ FILEPATH; // 上传后的路径
        System.out.println(FilePathUtils.class+"文件路径:"+filePath);
        File file = new File(filePath) ;
        if (!file.exists()){    //如果文件夹不存在
            file.mkdirs() ; //创建文件夹
        }
        return filePath ;
    }


    public static String getFileName(String path){
        ApplicationHome home = new ApplicationHome(FilePathUtils.class);
        String filePath = home.getSource().getParent()+ path; // 上传后的路径
        File file = new File(filePath) ;
        if (!file.exists()){    //如果文件夹不存在
            file.mkdirs() ; //创建文件夹
        }
        return filePath ;
    }
}
