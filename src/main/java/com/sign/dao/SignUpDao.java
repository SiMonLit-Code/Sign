package com.sign.dao;

import com.sign.entity.Add;
import com.sign.entity.Collect;
import com.sign.vo.CollectVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface SignUpDao {
    //报名
    @Insert("insert into collect(sid,sname,gender,nation,birth,pc,cmajor,gmajor,gid,did,addr,pos,person,parent,kid,cid,cname,nid,tname,tel) value(#{sid},#{sname},#{gender},#{nation},#{birth},#{pc},#{cmajor},#{gmajor},#{gid},#{did},#{addr},#{pos},#{person},#{parent},#{kid},#{cid},#{cname},#{nid},#{tname},#{tel})")
    public boolean insertStudent(CollectVo collect);
    //报名附表
    @Insert("insert into addcollect(did,exam,card,soldier) value(#{did},#{exam},#{card},#{soldier})")
    public boolean insertSecStudent(CollectVo collectVo);



    //查询所有
    @Select("select * from collect")
    public List<Collect> findStudent();

    @Select("select sid,did from collect")
    public List<Collect> findStudentdId();

    //查询单个
    @Select("select * from collect where did=#{did}")
    public Collect selectStudentById(String dId);

    //修改
    @Update("update collect set sid=#{sid},sname=#{sname},gender=#{gender},nation=#{nation},birth=#{birth},pc=#{pc},cmajor=#{cmajor},gmajor=#{gmajor},gid=#{gid},addr=#{addr},pos=#{pos},person=#{person},parent=#{parent},kid=#{kid},cid=#{cid},cname=#{cname},nid=#{nid},tname=#{tname},tel=#{tel} where did=#{did} ")
    public Integer updateStudent(CollectVo collect);
    //修改附表
    @Update("update addcollect set exam=#{exam},card=#{card},soldier=#{soldier} where did=#{did}")
    public Integer updateSecStudent(CollectVo collect);

    //连表查询
    @Select("select * from addcollect")
    @Results(id = "collectMap",value = {
            @Result(id = true,column = "did",property = "did"),
            @Result(column = "pay",property = "pay"),
            @Result(column = "exam",property = "exam"),
            @Result(column = "card",property = "card"),
            @Result(column = "soldier",property = "soldier"),
            @Result(property = "collect",column = "did",one = @One(select = "com.sign.dao.SignUpDao.selectStudentById",fetchType = FetchType.EAGER))
    })
    public List<Add> associationFind();

    //连表单个查询
    @Select("select * from addcollect where addcollect.did=#{did}")
    @Results(id = "collectSecMap",value = {
            @Result(id = true,column = "did",property = "did"),
            @Result(column = "pay",property = "pay"),
            @Result(column = "exam",property = "exam"),
            @Result(column = "card",property = "card"),
            @Result(column = "soldier",property = "soldier"),
            @Result(property = "collect",column = "did",one = @One(select = "com.sign.dao.SignUpDao.selectStudentById",fetchType = FetchType.EAGER))
    })
    public Add associationSecFind(String did);
}
