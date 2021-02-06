package com.sign.dao;

import com.sign.entity.Admin;
import com.sign.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminDao {

    @Update("update sys_user set password=#{password} where username=#{username} and role='ROLE_ADMIN'")
    Integer updateAdmin(User admin);

    @Select("select * from sys_user where username=#{username} and role='ROLE_ADMIN'")
    User findAdmin(String username);
}
