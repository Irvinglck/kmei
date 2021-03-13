package com.lck.reverse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lck.reverse.dao.TProAttributeMapper;
import com.lck.reverse.entity.TProAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TProAttributeServiceImpl extends ServiceImpl<TProAttributeMapper, TProAttribute>  {

    @Autowired
    private TProAttributeMapper tProAttributeMapper;

    public List<TProAttribute> getTProAttrs(Map<String, Object> params){
        return tProAttributeMapper.getTProAttrs(params);
    }
}
