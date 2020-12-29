package com.sign.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件上传和下载的工具类
 *      1. 默认最大文件大小(10MB)
 *          可以在application.properties文件中修改配置
 *          # 设置文件上传大小的限制
 *          spring.servlet.multipart.max-file-size=10MB
 *          # 文件的上传最大 大小
 *          spring.servlet.multipart.max-request-size=100MB
 * 文件状态返回，字符串（String）类型：
 *          SUCCESS             文件下载成功
 *          FAIL                文件下载失败
 *          FAIL_SIZE_OVERFLOW  文件下载失败 --- 文件太大
 *          FAIL_OTHER          文件下载失败 --- 其他原因
 * @author 周志通
 * @version 1.0
 * @date 2020/5/23 17:07
 */
public class FileUtils {
    private static final String DEFAULT_FILEPATH = "//static//files//" ;
    /**
     * 功能描述: <br>文件下载
     * <>
     * @description TODO
     * @methodName fileDownload
     * @author 周志通
     * @param file      需要下载的文件
     * @param filePath  文件的下载路径
     * @param fileName  文件名
     * @param suffix    文件的后缀名(希望不要含有 . )
     * @param maxSize   设置文件的最大的大小
     * @date 2020/5/23
     * @Return boolean
     **/
    public static String fileDownload(MultipartFile file , String filePath , String fileName , String suffix , Long maxSize){
        //判断文件是否太大
        if (maxSize<file.getSize()){
            return FileConstant.FAIL_SIZE_OVERFLOW ;
        }
        //路径+文件名+后缀名
        File dest = new File(filePath+fileName+"."+suffix) ;
        //判断是否存在该路径
        if (!dest.getParentFile().exists()){
            //不存在该路径，则创建该路径
            boolean mkdirs = dest.getParentFile().mkdirs();
            if (!mkdirs){
                System.out.println("文件路径创建失败！！！");
                return FileConstant.FAIL_OTHER ;
            }
        }
        try {
            file.transferTo(dest);
        }catch (IOException e){
            System.out.println("文件下载失败");
            e.printStackTrace();
            return  FileConstant.FAIL_OTHER ;
        }
        return FileConstant.SUCCESS ;
    }

    /**
     * 功能描述: <br>客户端下载文件
     *      注意：
     *          1， 文件路径上，不能出现中文目录
     * <>
     * @author 周志通
     * @param fileName 文件名
     * @param response  Servlet 原生 API
     * @date 2020/5/23
     **/
    public static void fileUnload(String fileName ,HttpServletResponse response) throws IOException {
        //根据文件名去指定目录去查找文件
        //String temp = ResourceUtils.getURL("classpath:").getPath() + "static" ;
        String temp = FilePathUtils.getFileName("//static//files//") ;

        String realPath = new String( temp.getBytes() ,"UTF-8");
        System.out.println("路径：" + realPath );
        // 读取文件
        File file = new File(realPath , fileName);
        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(file) ;
        //获取相应的输出流
        response.setHeader("content-disposition","attachment;fileName="+fileName) ;
        ServletOutputStream outputStream = response.getOutputStream() ;
        // 文件拷贝
        IOUtils.copy(fileInputStream , outputStream) ;
        // 关流：（优雅的关流）
        IOUtils.closeQuietly(fileInputStream) ;
        IOUtils.closeQuietly(outputStream) ;
    }

    /**
     * 获取项目的当前路径
     * @return
     */
    public static String getFilePath(){
        ApplicationHome home = new ApplicationHome(FileUtils.class);
        // 上传后的路径
        String filePath = home.getSource().getParent()+"//static//files//";
        return filePath ;
    }

    /**
     * 文件下载的返回状态
     */
    public static class FileConstant{
        /**
         * 文件上传成功
         */
        public static final String SUCCESS = "SUCCESS" ;
        /**
         * 文件上传失败
         */
        public static final String FAIL = "FAIL" ;
        /**
         * 文件上传失败：文件太大
         */
        public static final String FAIL_SIZE_OVERFLOW = "FAIL_SIZE_OVERFLOW" ;
        /**
         * 文件上传失败：其他原因
         */
        public static final String FAIL_OTHER = "FAIL_OTHER" ;



    }
}
