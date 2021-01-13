package com.lck.reverse.service;

import com.lck.reverse.entity.TConsult;
import com.lck.reverse.entity.TProAttribute;

import java.util.List;

public interface WxClientService {
    TConsult getOne(Integer id);

    int insertBatch(List<TProAttribute> pros);
}
