package com.sign.service;

import com.sign.entity.RegistrationForm;
import com.sign.entity.RegistrationFormAddition;
import com.sign.vo.RegistrationFormVo;

import java.util.List;

public interface ISignUpService {
    boolean insertStudent(RegistrationFormVo collect);

    boolean insertSecStudent(RegistrationFormVo collectVo);

    List<RegistrationForm> findStudent();

    RegistrationForm selectStudentById(String did);

    Integer updateStudent(RegistrationFormVo collect);

    Integer updateSecStudent(RegistrationFormVo collect);

    List<RegistrationForm> findStudentdId();

    List<RegistrationFormAddition> associationFind();

    RegistrationFormAddition associationSecFind(String did);
}
