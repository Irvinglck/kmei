package com.lck.reverse.stu.juc;

public class SingleDemoDcl {

    private static  SingleDemoDcl singleDemoDcl=null;

    private SingleDemoDcl(){
        System.out.println(Thread.currentThread().getName() +"**********SingleDemoDcl构造方法");
    }
    //dcl double check lock 双端检锁机制
    public SingleDemoDcl getSingleDemoDcl(){
        if(singleDemoDcl==null){
            synchronized (SingleDemoDcl.class){
                if(singleDemoDcl==null){
                    //这个地方可能会出现指令重排
                    //内存地址不为空，但是对象还没分配好
                    //需要在singleDemoDecl属性 加上volatile
                    singleDemoDcl=new SingleDemoDcl();
                }
            }
        }
        return singleDemoDcl;
    }
}
