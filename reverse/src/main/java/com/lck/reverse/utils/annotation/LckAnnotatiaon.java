package com.lck.reverse.utils.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface LckAnnotatiaon {
    String value() default "lck";

    boolean isDelete();
}
