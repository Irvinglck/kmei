package com.lck.reverse.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TNews {
    private Integer id;
    private String newId;
    private String title;
    private String nurl;
    private String nContent;
    private String titleUrl;
}
