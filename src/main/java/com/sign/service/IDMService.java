package com.sign.service;


import com.sign.entity.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IDMService {
    public List<MZDM> findMZDM();

    public List<ZZMMDM> findZZMMDM();

    public List<BYXXDM> findBYXXDM();

    //    public void fileLoad(String fname);
    public List<HJDM> findHJDM();

    public List<HJDM> likeHJDM(String hjdmmc);

    public List<BKZY> findBKZY();
}
