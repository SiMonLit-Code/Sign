package com.sign.service.Impl;

import com.sign.dao.AddVoDao;
import com.sign.entity.RegistrationForm;
import com.sign.service.WxPayOrderService;
import com.sign.utils.DataJoinUtils;
import com.sign.utils.FilePathUtils;
import com.sign.utils.QRCodeUtil;
import com.sign.vo.AddVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 周志通
 * @version 1.0
 * @className WxPayOrderServiceImpl
 * @description TODO
 * @date 2020/5/21 14:55
 */
@Service
public class WxPayOrderServiceImpl implements WxPayOrderService {

    @Autowired
    private WxPayOrderService payOrderService ;

    //获取二维码下载路径
    private static final String FILEPATH = FilePathUtils.getFileName();

    /**
     * 统一下订单，并一键生成代码
     * @param notify_url 回调地址（测试期间可以使用 http://www.baidu.com）
     * @param out_trade_no 商户订单号（唯一的）
     * @param total_fee 订单总金额(单位：分)
     * @param ip IP （客户端IP）（测试期间可以使用： 127.0.0.1）
     * @param body 商品内容描述
     * @param response servlet原生API
     * @param request servlet原生API
     * @return 下单成功返回 null ，如果错误返回错误(原因)信息
     */
    @Override
    public String unifiedOrder(String notify_url, String out_trade_no, String total_fee, String ip, String body, HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> map = null;
        try {
            map = DataJoinUtils.wxPay(notify_url, out_trade_no, total_fee, ip, body);
            String return_code = map.get("return_code");
            if("SUCCESS".equals(return_code)){
                String url = map.get("code_url");
                QRCodeUtil.createQRCode(url, FILEPATH, out_trade_no);
                String images = QRCodeUtil.parseQRFile(FILEPATH + out_trade_no + ".png");
                ServletOutputStream outputStream = response.getOutputStream() ;
                QRCodeUtil.createQROutput(images, outputStream) ;
                return "" ;
            }else {
                return map.get("return_msg") ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "下订单失败！！！请稍后重试";
    }

    /**
     * 订单查询
     * @param out_trade_no 商户订单号（唯一的）
     * @return 返回结果说明：（重要的 key 及说明）
     *      return_code         SUCCESS/FAIL，判断是否查询成功
     *      err_code_des        错误代码描述(return_code=FAIL)
     *      out_trade_no        商户订单号
     *      trade_state_desc   交易状态描述
     * @throws Exception
     */
    @Override
    public Map<String, String> orderQuery(String out_trade_no) throws Exception {
        Map<String, String> resultMap = DataJoinUtils.wxQuery(out_trade_no) ;
        return resultMap ;
    }

    /**
     * 关闭订单
     * @param out_trade_no 商户订单号（唯一的）
     * @return 返回结果说明：（重要的 key 及说明）
     *      return_code  SUCCESS/FAIL       判断是否关闭成功
     *      err_code_des                    错误代码描述(return_code=FAIL)
     *      result_msg                      业务结果描述
     *
     */
    @Override
    public Map<String, String> closeOrder(String out_trade_no) throws Exception {
        return DataJoinUtils.wxClosed(out_trade_no);
    }

    @Resource
    private AddVoDao addVoDao ;

    @Override
    public void queryAllOrder(List<RegistrationForm> registrationForms) {

        Map<String,String> map = null ;
        List<AddVo> addVos = new ArrayList<>() ;
        for (RegistrationForm registrationForm : registrationForms) {
            try {
                map = payOrderService.orderQuery(registrationForm.getDid()) ;
                String status = map.get("trade_state_desc");//订单状态信息
                if (status == null){
                    continue;
                }
                String payDate = map.get("time_end") ;
                addVoDao.insert(new AddVo(registrationForm.getDid() , status , payDate)) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
