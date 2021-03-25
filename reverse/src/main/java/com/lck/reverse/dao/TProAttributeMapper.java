package com.lck.reverse.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lck.reverse.entity.TProAttribute;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TProAttributeMapper extends BaseMapper<TProAttribute> {


    int insert(TProAttribute record);


    int insertBatch(@Param("pros") List<TProAttribute> pros);

    List<TProAttribute> getTProAttrs(Map<String, Object> params);

    List<Map<String,Object>> getProInfos(Map<String,Object> params);
}