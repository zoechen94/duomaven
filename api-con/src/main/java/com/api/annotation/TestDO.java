package com.api.annotation;

/**
 * Created by Lan.Chen on 2018/10/26
 */
public class TestDO {
    @MyInfoAnnotation(value = "姓名",id=2)
    private String name;

    private int age;


    @RefelTest(msg="得到姓名")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @MyInfoAnnotation(value = "打印",id=3)
    public void sout(String name){
        System.out.println(name);
    }
}
