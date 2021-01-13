package com.sign.dao;

import com.sign.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RegisterDao {
    @Insert("insert into sys_user(account,pwd) value(#{account},#{pwd})")
    boolean registerInsert(User user);

    @Select("select count(*) from sys_user where account=#{account} and pwd=#{pwd}")
    Integer registerFind(User user);

    @Select("select count(*) from sys_user where account=#{account} ")
    Integer registerFindAc(User user);

    @Select("select account from sys_user")
    List<User> registerFindAll();

    @Select("select * from sys_user where username=#{username} ")
    User registerFindById(String username);

    @Update("update sys_user set pwd=#{pwd} where account=#{account}")
    Integer registerFindUpdate(User user);
}
