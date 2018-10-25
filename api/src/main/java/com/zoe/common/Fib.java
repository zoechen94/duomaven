package com.zoe.common;

/**
 * Created by Lan.Chen on 2018/10/23
 */
public class Fib {
    static long sum=0;
    public static long sum(long n){

        if(n>2)
        return sum(n-1)+sum(n-2);
        return 1;
    }
    public static void main(String[] args){
        System.out.println(sum(3));
    }
}
