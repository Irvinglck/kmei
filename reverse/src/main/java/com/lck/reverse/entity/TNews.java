package com.lck.reverse.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TNews {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String newId;
    private String title;
    private String subtitle;
    private String nurl;
    private String nContent;
    private String titleUrl;
    private String createTime;
}
