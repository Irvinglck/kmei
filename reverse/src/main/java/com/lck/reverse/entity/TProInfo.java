package com.lck.reverse.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TProInfo implements Serializable {

    private Integer id;
    private String proid;
    private String proname;
    private String protype;
    private String picurl1;
    private String picurl2;
    private String picurl3;
    private String picurl4;
    private String picurl5;
    private String picurl6;
    private String picurl7;
    private String picurl8;
    private String picurl9;
    private static final long serialVersionUID = 1L;



}