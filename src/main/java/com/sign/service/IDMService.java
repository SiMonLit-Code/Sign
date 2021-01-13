package com.sign.service;


import com.sign.entity.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IDMService {
    List<MZDM> findMZDM();

    List<ZZMMDM> findZZMMDM();

    List<BYXXDM> findBYXXDM();

    //    public void fileLoad(String fname);
    List<HJDM> findHJDM();

    List<HJDM> likeHJDM(String hjdmmc);

    List<BKZY> findBKZY();
}
