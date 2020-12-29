package com.sign.dao;

import com.sign.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminDao {

    @Update("update admin set password=#{password} where acc=#{acc}")
    public Integer updateAdmin(Admin admin);

    @Select("select * from admin where acc='admin'")
    public Admin findAdmin();
}
