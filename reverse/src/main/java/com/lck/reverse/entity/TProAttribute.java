package com.lck.reverse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TProAttribute implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer idattr;


    private Integer id;


    private String classid;


    private String title;


    private String ftitle;


    private String titlepic;


    private String smalltext;


    private String pimg1;


    private String pimg2;


    private String pimg3;


    private String pimg4;


    private String price;


    private String cert;


    private String pdfdownload;


    private String outputSpeedColor;


    private String outputSpeedMono;


    private String outputsizemax;


    private String colour;


    private static final long serialVersionUID = 1L;



}