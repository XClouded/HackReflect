package cn.edu.zafu;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lizhangqu on 16/4/29.
 */
public class Reflect {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //直接通过class
        Class<?> studentClassByClass = Student.class;

        //通过class的字符串全类名
        Class<?> studentClassByName = Class.forName("cn.edu.zafu.Student");

        //构造函数new对象
        Constructor<?> constructor = studentClassByName.getConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Student student = (Student) constructor.newInstance("区长", 22);
        System.out.println(student);

        //非静态变量
        Field ageFiled = studentClassByName.getDeclaredField("age");
        ageFiled.setAccessible(true);
        Integer age = (Integer) ageFiled.get(student);
        System.out.println(age);

        ageFiled.set(student, 100);
        System.out.println(student.getAge());


        //静态变量
        Field schoolField = studentClassByName.getDeclaredField("school");
        schoolField.setAccessible(true);
        schoolField.set(null, "浙江大学");
        System.out.println(Student.getSchool());

        //非静态方法调用
        Method sayMethod = studentClassByName.getDeclaredMethod("say", String.class);
        sayMethod.setAccessible(true);
        sayMethod.invoke(student, "hello world");


        //静态方法调用
        Method setSchoolMethod = studentClassByName.getDeclaredMethod("setSchool", String.class);
        setSchoolMethod.invoke(null, "清华大学");
        System.out.println(Student.getSchool());

    }
}
