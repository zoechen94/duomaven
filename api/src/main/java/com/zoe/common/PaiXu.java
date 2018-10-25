package com.zoe.common;

/**
 * Created by Lan.Chen on 2018/10/23
 */
public class PaiXu {
    public static void sout(long[] a){
        for(long n:a){
            System.out.print(n+" ");;
        }
        System.out.println();
    }
    public static void maopao(long[] a){
        for(int i=0;i<a.length-1;i++){
            long temp;
//            for(int j=a.length-1;j>i;j--){
//                if(a[j]<a[j-1]){
//                    temp=a[j];
//                    a[j]=a[j-1];
//                    a[j-1]=temp;
//                }
//            }
            for(int j=0;j<a.length-1;j++){
                if(a[j]>a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        sout(a);
    }
    public static void xuanze(long[] a){
        for(int i=0;i<a.length-1;i++){
            int k=i;
            for(int j=i;j<a.length;j++){
                if(a[j]<a[k]){
                    k=j;
                }
            }
            long temp=a[i];
            a[i]=a[k];
            a[k]=temp;
        }
    }

    /**
     * 直接插入排序（最基础）第一次从
     * @param a
     */
    public static void charu(long[] a){
        for(int i=1;i<a.length;i++){
            int j=i-1;
            long x=a[i];
            while(j>=0&&a[j]>x){
                a[j+1]=a[j];
                j--;
            }
            a[j+1]=x;
        }
    }

    /**
     * 快速排序
     * @param a
     * @param left  第一次是0,
     * @param right 第一次是数组最后一个下标
     */
    public static void kuaisu(long[] a ,int left,int right){
        long basic=a[left];
        int i=left;
        int j=right;
        long temp;
        while(i!=j){
            while(a[j]>=basic&&i<j)j--;//顺序很重要，先从右到左寻找，直到第一个小于这个基准数的值
            while(a[i]<=basic&&i<j)i++;//从左至右寻找，直到第一个大于基准数的值停止循环。
            //需要做一个判断，i<j才开始交换找到的两个“第一个”
            if(i<j){
                temp=a[i];
                a[i]=a[j];
                a[j]=temp;
            }
        }
        //当i=j的时候停止循环，交换基准数和a[i]
        temp=a[left];
        a[left]=a[i];
        a[i]=temp;
        //左边的进行递归
        if(i>left){
        kuaisu(a,left,i-1);}
        //右边的进行递归
        if(i<right){
            kuaisu(a,i+1,right);
        }
    }
    public static void main(String[] args){
        long[] arr=new long[]{18,32,64,3,2,65,64};
//        maopao(arr);
//        xuanze(arr);
        charu(arr);
//        kuaisu(arr,0,arr.length-1);
        sout(arr);
    }
}
