package com.lck.reverse.service.impl;

import com.lck.reverse.dao.TConsultMapper;
import com.lck.reverse.entity.TConsult;
import com.lck.reverse.service.WxClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxClientServiceImpl implements WxClientService {

    @Autowired
    private TConsultMapper tConsultMapper;

    public TConsult getOne(Integer id){
        return tConsultMapper.selectByPrimaryKey(id);
    }

}
