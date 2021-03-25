package com.lck.reverse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TBannerImg {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String bannerId;
    private String bUrl;
    private String type;
    private String sort;
    private String name;
}
