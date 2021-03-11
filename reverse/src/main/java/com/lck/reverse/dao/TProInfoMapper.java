package com.lck.reverse.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lck.reverse.entity.TProInfo;

import java.util.List;

public interface TProInfoMapper extends BaseMapper<TProInfo> {

//    @Select("selectUserList")
    List<TProInfo> selectProList(Page<TProInfo> page, String type);
}
