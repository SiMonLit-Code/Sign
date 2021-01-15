package com.sign.service;

import com.sign.entity.RegistrationForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @className WxPayOrderService
 * @date 2020/5/21 14:45
 */
public interface WxPayOrderService {
    /**
     * 统一下订单，并一键生成代码
     * @param notify_url 回调地址（测试期间可以使用 http://www.baidu.com）
     * @param out_trade_no 商户订单号（唯一的）
     * @param total_fee 订单总金额(单位：分)
     * @param ip IP （客户端IP）（测试期间可以使用： 127.0.0.1）
     * @param body 商品内容描述
     * @param request servlet原生API
     * @param response servlet原生API
     * @return String 下单成功返回 null ，如果错误返回错误(原因)信息，
     **/
    String unifiedOrder(String notify_url, String out_trade_no, String total_fee, String ip, String body, HttpServletResponse response, HttpServletRequest request) ;

    /**
     * 订单查询
     * @param out_trade_no 商户订单号（唯一的）
     * @return 返回结果说明：（重要的 key 及说明）
     *       return_code         SUCCESS/FAIL，判断是否查询成功
     *       err_code_des        错误代码描述(return_code=FAIL)
     *       cash_fee            现金支付金额
     *       out_trade_no        商户订单号
     *       trade_state_desc   交易状态描述
     *       time_end            支付完成时间（格式yyyyMMddHHmmss）
     */
    Map<String, String> orderQuery(String out_trade_no) throws Exception;

    /**
     * 订单关闭
     * @param out_trade_no 商户订单号（唯一的）
     * @return 返回关闭的结果：（重要的 key 及说明）
     *      return_code  SUCCESS/FAIL       判断是否关闭成功
     *      err_code_des                    错误代码描述(return_code=FAIL)
     *      result_msg                      业务结果描述
     */
    Map<String, String> closeOrder(String out_trade_no) throws Exception;

    /**
     * 查询所有订单消息
     * @param registrationForms 订单编号集合
     */
    void queryAllOrder(List<RegistrationForm> registrationForms) ;


}
