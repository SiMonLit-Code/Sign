package com.sign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Spring)实体类
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Spring implements Serializable {
//    private static final long serialVersionUID = 910504581118860451L;
    
    private String sid;
    
    private String kid;
    
    private String sname;
    
    private String gender;
    
    private String gmajor;
    
    private String did;
    
    private String pid;
    
    private String seat;
    
    private String cid1;
    
    private String cid2;
    
    private String cid3;
    
    private String gp;

}