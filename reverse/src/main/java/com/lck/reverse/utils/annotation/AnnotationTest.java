package com.lck.reverse.utils.annotation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnnotationTest {

    public static void main(String[] args) {
        Class<? extends Person> aClass = Person.class;
        System.out.println((aClass.isAnnotationPresent(LckAnnotatiaon.class)));
        if (aClass.isAnnotationPresent(LckAnnotatiaon.class)) {
            log.info("Person类上配置了注解 [{}]", "LckAnnotatiaon");
            LckAnnotatiaon lckAno = aClass.getAnnotation(LckAnnotatiaon.class);
            log.info("注解LckAnnotatiaon中的配置信息是 [{}],[{}]", lckAno.isDelete(), lckAno.value());
        } else {
            log.info("Person类上没有配置注解 [{}]", "LckAnnotatiaon");
        }
    }
}
