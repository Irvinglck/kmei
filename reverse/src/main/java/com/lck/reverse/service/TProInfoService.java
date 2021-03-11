package com.lck.reverse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lck.reverse.entity.TProInfo;

public interface TProInfoService {

    Page<TProInfo> selectUserPage(Page<TProInfo> page, String state);
}
