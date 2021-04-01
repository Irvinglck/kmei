package com.lck.reverse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class TUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String nickName;
    private String realName;
    private String sex;
    private String emailAddress;
    private String introduce;
    private String account;
    private String titleUrl;
    private String password;
    private String createTime;
}
