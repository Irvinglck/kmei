package com.lck.reverse.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TBannerImg {
    private Integer id;
    private String bannerId;
    private String bUrl;
    private String type;
    private String sort;
}
