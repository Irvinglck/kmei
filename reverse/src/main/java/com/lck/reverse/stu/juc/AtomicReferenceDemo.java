package com.lck.reverse.stu.juc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@ToString
@AllArgsConstructor
class User{
    String userName;
    int age;
}


public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User u=new User("lck",28);
        User u2=new User("wu",28);

        //原子引用类型
        AtomicReference<User> atomicReference=new AtomicReference<>();
        atomicReference.set(u);

        System.out.println(atomicReference.compareAndSet(u,u2)+"*********"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(u,u2)+"*********"+atomicReference.get().toString());
    }

}
