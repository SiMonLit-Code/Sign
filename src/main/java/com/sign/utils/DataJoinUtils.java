package com.sign.utils;

import com.sign.constant.ConfigConstant;
import com.sign.sdk.WXPayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DataJoinUtils
 * @Description TODO
 * @Date 2020/5/17 9:59
 * @Version 1.0
 */
public class DataJoinUtils {
    /**
     * 统一下单 URL
     */
    private static String url_unified = "https://api.mch.weixin.qq.com/pay/unifiedorder" ;
    /**
     * 查询订单 URL
     */
    private static String url_query = "https://api.mch.weixin.qq.com/pay/orderquery" ;
    /**
     * 关闭订单 URL
     */
    private static String url_close = "https://api.mch.weixin.qq.com/pay/closeorder" ;

    /**
     * @description 微信下单接口
     * @param notify_url 回调指定
     * @param out_trade_no 商户订单号
     * @param total_fee 订单总金额
     * @param ip IP
     * @param body 商品内容描述
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> wxPay(String notify_url, String out_trade_no, String total_fee, String ip, String body) throws Exception {
        Map<String, String> map = new HashMap<>() ;
//        notify_url="http://www.baidu.com";
        //公众账号ID
        map.put("appid", ConfigConstant.APPID) ;
        //商户号
        map.put("mch_id", ConfigConstant.MCHID) ;
        //随机字符串
        map.put("nonce_str", WXPayUtil.generateNonceStr()) ;
        // 商品描述
        map.put("body",body) ;
        //订单结束时间
        map.put("time_expire","20220701000000") ;
        // 商品订单号
        map.put("out_trade_no",out_trade_no) ;
        // 订单总金额，单位：分
        map.put("total_fee", total_fee) ;
        // 终端 IP
        map.put("spbill_create_ip", ip) ;
        //异步接受微信支付结果通知的回调地址，通知 url 必须为外网可访问的 url , 不能携带参数
        map.put("notify_url", notify_url) ;
        //交易类型（JSPAPI公众号支付/NATIVE扫码支付/APP支付）
        map.put("trade_type", "NATIVE") ;
        //trade_type=NATIVE时(即扫码支付), 此参数必传， 此参数为二维码中包含：商品ID，商户自行定义
        map.put("product_id", out_trade_no) ;

        //以上信息+密钥生成签名
        String sign = WXPayUtil.generateSignature(map, ConfigConstant.KEY) ;
        map.put("sign", sign) ;

        //生成带有 sign 的 XML 格式字符串
        String xmlStr = getSignatureXml(map);
        System.out.println(xmlStr);
        //指定与微信交互URL接口地址
        return getPostURLToMap(url_unified, xmlStr);
    }

    /**
     * 功能描述: <br>微信订单查询
     * <>
     * @description TODO
     * @methodName wxQuery
     * @param out_trade_no 商户订单 ID
     * @date 2020/5/18
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> wxQuery(String out_trade_no) throws Exception {
        return wx_query_and_closed(url_query, out_trade_no) ;
    }
    /**
     * 功能描述: <br>关闭订单
     * <>
     * @description TODO
     * @methodName wxClosed
     * @param out_trade_no
     * @date 2020/5/19
     * @Return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> wxClosed(String out_trade_no) throws Exception {
        return wx_query_and_closed(url_close, out_trade_no) ;
    }

    private static Map<String, String> wx_query_and_closed(String url, String out_trade_no) throws Exception{
        Map<String, String> map = new HashMap<>() ;
        //公众账号ID appid
        map.put("appid", ConfigConstant.APPID) ;
        //商户号 mch_id
        map.put("mch_id", ConfigConstant.MCHID);
        //随机字符串 nonce_str
        map.put("nonce_str", WXPayUtil.generateNonceStr()) ;
        //商户订单号 out_trade_no
        map.put("out_trade_no", out_trade_no) ;
        //签名 sign
        String sign = WXPayUtil.generateSignature(map, ConfigConstant.KEY) ;
        map.put("sign", sign) ;
        String xmlStr = getSignatureXml(map) ;
        return  getPostURLToMap(url, xmlStr) ;
    }

    /**
     * 功能描述: 将封装好的xml信息，发送 POST 请求并返回
     * @description TODO
     * @methodName getPostURLToMap
     * @param xmlStr 发送 POST 请求的 xml 数据
     * @date 2020/5/18
     **/
    private static Map<String, String> getPostURLToMap(String url, String xmlStr) {
        String str = UrlPreUtils.post(url, xmlStr) ;
        Map<String,String> retMap = new HashMap<>() ;
        try {
            retMap = WXPayUtil.xmlToMap(str) ;  // XML格式字符串转换为Map
        }catch (Exception e){
            e.printStackTrace();
        }
        return retMap ;
    }

    /**
     * 获取含签名的xml (需要含有 签名 sign )
     * @param map 参数
     * @return 含签名的xml
     * @throws Exception
     */
    private static String getSignatureXml(Map<String, String> map) {
        StringBuilder strb = new StringBuilder();
        strb.append("<xml>");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!"".equals(entry.getValue()) ) {	// && !"sign".equals(entry.getKey())
                strb.append("\t\n<").append(entry.getKey()).append(">").append("<![CDATA[" + entry.getValue() + "]]>").append("</").append(entry.getKey()).append(">");
            }
        }
        strb.append("\n</xml>") ;
        return strb.toString();
    }
}
