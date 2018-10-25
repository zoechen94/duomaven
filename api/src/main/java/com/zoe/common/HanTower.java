package com.zoe.common;

/**
 * Created by Lan.Chen on 2018/10/23
 */
public class HanTower {
   public static void doTower(long disk,char from,char inner,char to){
       if(disk==1){
           System.out.println("盘子:"+disk+",从 "+from+" 到 "+to);
       }else {
           doTower(disk-1,from,to,inner);
           System.out.println("盘子:"+disk+",从 "+from+" 到 "+to);
           doTower(disk-1,inner,from,to);
       }
   }

   public static void main(String[] args){
       doTower(2,'A','B','C');
   }
}
