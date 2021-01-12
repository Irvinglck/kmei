package com.lck.reverse.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Consult {

    private Integer id;
    private String name;
    private String telPhone;
    private String address;
    private String position;
    private String email;
    private String consultConent;
}
