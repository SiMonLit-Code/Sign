package com.sign.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddVo {

    private String did;
    private String pay;
    private String paydate;
//    private String card ;
//    private String soldier ;
//
//    public AddVo(String did , String pay , String paydate){
//
//    }
}
