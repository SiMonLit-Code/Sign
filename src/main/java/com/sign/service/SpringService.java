package com.sign.service;

import com.sign.entity.Spring;
import java.util.List;

/**
 * (Spring)表服务接口
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */
public interface SpringService {


    /**
     * 通过ID查询单条数据
     *
     * @param  did
     * @return 实例对象
     */
    Spring queryById(String did);



}