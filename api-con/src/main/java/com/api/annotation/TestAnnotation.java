package com.api.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Lan.Chen on 2018/10/26
 */
public class TestAnnotation {
    public static void main(String[] args) throws NoSuchFieldException {
       Field field= TestDO.class.getDeclaredField("name");
        MyInfoAnnotation annotation=  field.getAnnotation(MyInfoAnnotation.class);
       if(annotation!=null){
           System.out.println("field-annotation:"+((MyInfoAnnotation) annotation).value());
       }

       Method[] methods=TestDO.class.getDeclaredMethods();
       for(Method m:methods){
//           Annotation a=m.getAnnotation(MyInfoAnnotation.class);
//           if(a!=null){
//               System.out.println("类型:"+a.annotationType()+"  值"+((MyInfoAnnotation) a).value()+((MyInfoAnnotation) a).id());
//           }
           Annotation[] anons=m.getAnnotations();
           for(Annotation a:anons){
               System.out.println("annos:"+a.annotationType());
               if(a.annotationType() == MyInfoAnnotation.class){
                   Annotation temp=m.getAnnotation(MyInfoAnnotation.class);
                   System.out.println("temp:"+((MyInfoAnnotation) temp).id());
               }
           }
       }
    }
}
