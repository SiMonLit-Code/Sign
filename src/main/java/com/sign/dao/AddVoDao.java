package com.sign.dao;

import com.sign.vo.AddVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddVoDao {

    @Update("update stu_info_add set pay = #{pay} , paydate=#{paydate} where did = #{did} ")
    int insert(AddVo addVo) ;

}
