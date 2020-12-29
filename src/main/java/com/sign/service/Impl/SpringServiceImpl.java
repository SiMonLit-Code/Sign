package com.sign.service.Impl;

import com.sign.entity.Spring;
import com.sign.dao.SpringDao;
import com.sign.service.SpringService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Spring)表服务实现类
 *
 * @author czz
 * @since 2020-06-09 13:54:07
 */
@Service
public class SpringServiceImpl implements SpringService {
    @Resource
    private SpringDao springDao;

    /**
     * 通过ID查询单条数据
     *
     * @param  did
     * @return 实例对象
     */
    @Override
    public Spring queryById(String did) {
        return this.springDao.queryById(did);
    }

}