package com.sign.service;

import com.sign.entity.Add;
import com.sign.entity.RegistrationForm;
import com.sign.vo.RegistrationFormVo;

import java.util.List;

public interface ISignUpService {
    public boolean insertStudent(RegistrationFormVo collect);
    public boolean insertSecStudent(RegistrationFormVo collectVo);

    public List<RegistrationForm> findStudent();
    public RegistrationForm selectStudentById(String did);
    public Integer updateStudent(RegistrationFormVo collect);
    public Integer updateSecStudent(RegistrationFormVo collect);
    public List<RegistrationForm> findStudentdId();

    public List<Add> associationFind();
    public Add associationSecFind(String did);
}
