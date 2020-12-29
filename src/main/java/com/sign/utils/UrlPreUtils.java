package com.sign.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName UrlPreUtils
 * @Description TODO
 * @Author 周志通
 * @Date 2020/5/17 10:51
 * @Version 1.0
 */
public class UrlPreUtils {
    //编码格式
    private static final String ENCODING = "UTF-8" ;

    /**
     * @Description: 发送POST请求
     * @MethodName: post
     * @Author: 周志通
     * @Date: 2020/5/17
     * @param url 访问的URL地址
     * @param json 发送的数据
     * @Return: java.lang.String
     **/
    public static String post(String url, String json) {
        StringBuffer requestText = new StringBuffer() ;
        CloseableHttpResponse response ;

        CloseableHttpClient client = null ;
        HttpPost httpPost = new HttpPost(url) ;
        StringEntity entityParams = null ;
        try {
            entityParams = new StringEntity(json,"UTF-8") ;
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-type","type/json;charset=ISO-8859-1") ;
//            httpPost.setHeader("Content-type","type/json;charset=UTF-8") ;
            client = HttpClients.createDefault() ;
            response = client.execute(httpPost) ;
            byte[] x = EntityUtils.toByteArray(response.getEntity()) ;
            requestText.append(new String(x,ENCODING)) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(requestText.toString());
        return requestText.toString() ;
    }

/*    public static String post(String url, String json) {
        StringBuffer requestText = new StringBuffer() ;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null ;
        HttpPost httpPost = new HttpPost(url) ;
        StringEntity entityParams = null ;
        try {
            entityParams = new StringEntity(json,"UTF-8") ;
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-type","type/json;charset=ISO-8859-1") ;
            client = HttpClients.createDefault() ;
            response = client.execute(httpPost) ;
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            if (response.getStatusLine().getStatusCode() == 200) {
//                    logger.error(result+"-----------success------------------");
                System.out.println("-----------success------------------");
                System.out.println(result);

                return result;
            }else {
                return null ;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null ;
        }
    }*/

}
