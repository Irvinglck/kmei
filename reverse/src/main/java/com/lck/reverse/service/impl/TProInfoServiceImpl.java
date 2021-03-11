package com.lck.reverse.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lck.reverse.dao.TProInfoMapper;
import com.lck.reverse.entity.TProInfo;
import com.lck.reverse.service.TProInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TProInfoServiceImpl extends ServiceImpl<TProInfoMapper, TProInfo> implements TProInfoService {

    @Autowired
    private TProInfoMapper tProInfoMapper;

    public Page<TProInfo> selectUserPage(Page<TProInfo> page, String state) {
        Page<TProInfo> tProInfoPage = page.setRecords(tProInfoMapper.selectProList(page, state));
        return tProInfoPage;

    }
}
