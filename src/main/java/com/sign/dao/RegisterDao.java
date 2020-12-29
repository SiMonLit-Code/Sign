package com.sign.dao;

import com.sign.entity.Register;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RegisterDao {
    @Insert("insert into register(account,pwd) value(#{account},#{pwd})")
    boolean registerInsert(Register register);

    @Select("select count(*) from register where account=#{account} and pwd=#{pwd}")
    Integer registerFind(Register register);

    @Select("select count(*) from register where account=#{account} ")
    Integer registerFindAc(Register register);

    @Select("select account from register")
    List<Register> registerFindAll();

    @Select("select * from register where account=#{account} ")
    Register registerFindById(String account);

    @Update("update register set pwd=#{pwd} where account=#{account}")
    Integer registerFindUpdate(Register register);
}
