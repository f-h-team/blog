package com.chao.utils;

public class ThreadLocalUtils {
    private static final ThreadLocal THREAD_LOCAL=new ThreadLocal<>();
    public static void set(Object t){
        THREAD_LOCAL.set(t);
    }
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
