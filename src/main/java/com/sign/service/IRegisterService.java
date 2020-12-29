package com.sign.service;

import com.sign.entity.Register;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IRegisterService {
    public boolean registerInsert(Register register);

    public boolean registerFind(Register register);

    public Integer registerFindAc(Register register);

    public List<Register> registerFindAll();

    public Register registerFindById(String account);

    public Integer registerUpdate(Register register);
}
