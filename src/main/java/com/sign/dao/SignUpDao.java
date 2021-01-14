package com.sign.dao;

import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.vo.RegistrationFormVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface SignUpDao {
    //报名
    @Insert("insert into stu_info(sid,sname,gender,nation,birth,pc,cmajor,gmajor,gid,did,addr,pos,person,parent,kid,cid,cname,nid,tname,tel) value(#{sid},#{sname},#{gender},#{nation},#{birth},#{pc},#{cmajor},#{gmajor},#{gid},#{did},#{addr},#{pos},#{person},#{parent},#{kid},#{cid},#{cname},#{nid},#{tname},#{tel})")
     boolean insertStudent(RegistrationFormVo collect);
    //报名附表
    @Insert("insert into stu_info_add(did,exam,card,soldier) value(#{did},#{exam},#{card},#{soldier})")
     boolean insertSecStudent(RegistrationFormVo collectVo);

    //查询所有
    @Select("select * from stu_info")
     List<RegistrationForm> findStudent();

    @Select("select sid,did from stu_info")
     List<RegistrationForm> findStudentdId();

    //查询单个
    @Select("select * from stu_info where did=#{did}")
     RegistrationForm selectStudentById(String dId);

    //修改
    @Update("update stu_info set sid=#{sid},sname=#{sname},gender=#{gender},nation=#{nation},birth=#{birth},pc=#{pc},cmajor=#{cmajor},gmajor=#{gmajor},gid=#{gid},addr=#{addr},pos=#{pos},person=#{person},parent=#{parent},kid=#{kid},cid=#{cid},cname=#{cname},nid=#{nid},tname=#{tname},tel=#{tel} where did=#{did} ")
     Integer updateStudent(RegistrationFormVo collect);
    //修改附表
    @Update("update stu_info_add set exam=#{exam},card=#{card},soldier=#{soldier} where did=#{did}")
     Integer updateSecStudent(RegistrationFormVo collect);

    //连表查询
    @Select("select * from stu_info_add")
    @Results(id = "collectMap",value = {
            @Result(id = true,column = "did",property = "did"),
            @Result(column = "pay",property = "pay"),
            @Result(column = "exam",property = "exam"),
            @Result(column = "card",property = "card"),
            @Result(column = "soldier",property = "soldier"),
            @Result(property = "registrationForm",column = "did",one = @One(select = "com.sign.dao.SignUpDao.selectStudentById",fetchType = FetchType.EAGER))
    })
     List<RegistrationFormAddition> associationFind();

    //连表单个查询
    @Select("select * from stu_info_add where stu_info_add.did=#{did}")
    @Results(id = "collectSecMap",value = {
            @Result(id = true,column = "did",property = "did"),
            @Result(column = "pay",property = "pay"),
            @Result(column = "exam",property = "exam"),
            @Result(column = "card",property = "card"),
            @Result(column = "soldier",property = "soldier"),
            @Result(property = "registrationForm",column = "did",one = @One(select = "com.sign.dao.SignUpDao.selectStudentById",fetchType = FetchType.EAGER))
    })
     RegistrationFormAddition associationSecFind(String did);
}
