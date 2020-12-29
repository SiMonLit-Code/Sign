package com.sign.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @ClassName Product
 * @Description TODO
 * @Author 周志通
 * @Date 2020/5/17 9:56
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @ExcelProperty(value = "考生身份证",index = 0)
    private String orderNo ;    //订单编号
    @ExcelProperty(value = "考生报名序号",index = 1)
    private String remark ;     //商品描述
    @ExcelProperty(value = "缴费状态",index = 2)
    private String price ;      //价格：分

    @Override
    public String toString() {
        return "Product{" +
                "orderNo='" + orderNo + '\'' +
                ", remark='" + remark + '\'' +
                ", price=" + price +
                '}';
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
