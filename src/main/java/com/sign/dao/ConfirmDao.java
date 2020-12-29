package com.sign.dao;

import com.sign.entity.ConfirmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ConfirmDao {
    //查询单个
    @Select("select * from 表名 where id=#{id}")
    ConfirmInfo findConfirmInfoById(String id);

    //修改专业
    @Update("update 表名 set BKZY=#{BKZY},CINFO=#{CINFO} where id=#{id}")
    Integer updateConfirmInfoById(String BKZY,String CINFO);
}
