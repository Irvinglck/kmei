package com.lck.reverse.utils.annotation;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@LckAnnotatiaon(isDelete=true)
public class Person {
    private String name;
    private Integer age;
    private boolean isDelete;
}
