package com.lck.reverse.service.impl;

import com.lck.reverse.dao.TConsultMapper;
import com.lck.reverse.dao.TProAttributeMapper;
import com.lck.reverse.entity.TConsult;
import com.lck.reverse.entity.TProAttribute;
import com.lck.reverse.service.WxClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxClientServiceImpl implements WxClientService {

    @Autowired
    private TConsultMapper tConsultMapper;
    @Autowired
    private TProAttributeMapper tProAttributeMapper;





    public TConsult getOne(Integer id){
        return tConsultMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertBatch(List<TProAttribute> pros) {
        return tProAttributeMapper.insertBatch(pros);
    }



}
