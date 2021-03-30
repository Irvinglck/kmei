package com.lck.reverse.utils.reflect;

import com.lck.reverse.entity.TProInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Slf4j
public class Reflect {
    /**
     * 知道标志找字段
     * @param flag
     * @param value
     * @return
     */
    public static TProInfo reflectValue(String flag, String value){
        try {
            Class<?> tProInfo = Class.forName("com.lck.reverse.entity.TProInfo");
            Constructor<?> declaredConstructor = tProInfo.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            TProInfo newInstance = (TProInfo) declaredConstructor.newInstance();
            //通过字节码获取字段
            Field field = tProInfo.getDeclaredField("picurl" + flag);
            //反射为字段赋值
            field.setAccessible(true);
            field.set(newInstance,value);
            System.out.println(newInstance.toString());
            return newInstance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TProInfo reflectByObj(TProInfo tProInfo,String flag){
        log.info("赋值前 [{}]",tProInfo.toString());
        try {
            Class<? extends TProInfo> aClass = tProInfo.getClass();
            Field declaredField = aClass.getDeclaredField("picurl" + flag);
            //反射为字段赋值
            declaredField.setAccessible(true);
            declaredField.set(tProInfo,"");
            log.info("赋值后 [{}]",tProInfo.toString());
            return tProInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
