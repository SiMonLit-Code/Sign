package com.sign.dao;

import com.sign.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RegisterDao {
    @Insert("insert into register(account,pwd) value(#{account},#{pwd})")
    boolean registerInsert(User user);

    @Select("select count(*) from register where account=#{account} and pwd=#{pwd}")
    Integer registerFind(User user);

    @Select("select count(*) from register where account=#{account} ")
    Integer registerFindAc(User user);

    @Select("select account from register")
    List<User> registerFindAll();

    @Select("select * from sys_user where username=#{username} ")
    User registerFindById(String username);

    @Update("update register set pwd=#{pwd} where account=#{account}")
    Integer registerFindUpdate(User user);
}
