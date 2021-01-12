package com.sign.dao;

import com.sign.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DMDao {

    @Select("select * from mzdm")
    public List<MZDM> findMZDM();

    @Select("select * from zzmmdm")
    public List<ZZMMDM> findZZMMDM();

    @Select("select * from byxxdm")
    public List<BYXXDM> findBYXXDM();

    //    @Select("SELECT * FROM registrationForm into outfile 'D:/Uploads/nclg.xls' character set gbk")
//    public void fileLoad(String fname);
    @Select("select * from hjdm")
    public List<HJDM> findHJDM();

    @Select("SELECT * from hjdm WHERE hjmc LIKE concat('%',#{hjdmmc},'%')")
    public List<HJDM> likeHJDM(String hjdmmc);

    @Select("select * from bkzy")
    public List<BKZY> findBKZY();

}
