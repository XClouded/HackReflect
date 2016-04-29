package cn.edu.zafu;

import cn.edu.zafu.hack.Hack;
import cn.edu.zafu.hack.Interception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by lizhangqu on 16/4/28.
 */
public class HackReflect {
    public static void main(String[] args) throws Hack.HackDeclaration.HackAssertionException, IllegalAccessException, InvocationTargetException {
        //通过Class来获得一个HackedClass
        Hack.HackedClass<Student> hackPersonByClass = Hack.into(Student.class);
        //输出class测试
        System.out.println(hackPersonByClass.getmClass());
        //通过Class的全类名获得一个HackedClass
        Hack.HackedClass<Student> hackPersonByName = Hack.into("cn.edu.zafu.Student");
        //输出class测试
        System.out.println(hackPersonByName.getmClass());
        //获得构造函数
        Hack.HackedConstructor personConstructor = hackPersonByName.constructor(String.class, int.class);
        //创建并获得实例
        Student student = (Student) personConstructor.getInstance("区长", 121);
        //输出结果测试
        System.out.println(student);
        //直接调用反射获得的对象的方法
        student.say("世界你好,世界再见");

        //通过field -> ofType 获得一个HackedField,非静态
        Hack.HackedField<Student, String> hackName = hackPersonByName.field("name").ofType(String.class);
        //反射调用
        String name = hackName.get(student);
        //输出结果测试
        System.out.println(name);

        //通过field -> ofType 获得一个HackedField,非静态
        Hack.HackedField<Student, Integer> hackAge = hackPersonByName.field("age").ofType(int.class);
        //反射设置属性
        hackAge.set(student, 16);
        //反射获得age,进行验证
        Integer age = hackPersonByName.field("age").ofType(int.class).get(student);
        //输出结果测试
        System.out.println(age);

        //反射获得静态变量
        Hack.HackedField<Student, Object> hackSchool = hackPersonByName.staticField("school").ofType("java.lang.String");

        //获得静态变量值
        String sSchool = (String) hackSchool.get(null);
        //输出结果测试
        System.out.println(sSchool);

        //设置值
        hackSchool.getField().set(null, "北京大学");
        //获得值验证是否设置成功,通过getField()方式
        sSchool = (String) hackSchool.getField().get(null);
        //输出结果测试
        System.out.println(sSchool);


        //泛型参数
        Hack.HackedField<Student, Map<String, Integer>> hackScores = hackPersonByName.field("scores").ofGenericType(Map.class);
        Map<String, Integer> stringIntegerMap = hackScores.get(student);

        stringIntegerMap.put("语文", 80);
        stringIntegerMap.put("数学", 90);
        //泛型参数设置
        hackScores.set(student, stringIntegerMap);
        //输出结果测试
        System.out.println(student.getScores());

        //反射非静态方法调用
        hackPersonByName.method("say", String.class).invoke(student, "fuck the source code");

        //反射静态方法调用
        hackPersonByName.staticMethod("setSchool", String.class).invoke(null, "南京大学");

        //输出结果测试
        System.out.println(Student.getSchool());

        //反射静态方法调用
        String school = (String) hackPersonByName.staticMethod("getSchool").getMethod().invoke(null);
        System.out.println(school);


        //字段劫持处理
        Interception.InterceptionHandler handler = new Interception.InterceptionHandler<IBehavior>() {
            @Override
            public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
                System.out.println("hijack:[invoke start]");

                Object o = super.invoke(obj, method, args);

                System.out.println("hijack:[invoke end]");
                return o;
            }
        };
        //劫持behavior字段
        hackPersonByName.field("behavior").hijack(student, handler);
        //测试劫持效果
        student.getBehavior().perform("sleep", "sleep 10h");

        //异常处理
        Hack.setAssertionFailureHandler(new Hack.AssertionFailureHandler() {
            @Override
            public boolean onAssertionFailure(Hack.HackDeclaration.HackAssertionException failure) {
                //如果是notHandler字段,则不处理,即扔出异常,否则打印输出,不扔出异常
                if ("notHandler".equals(failure.getHackedFieldName())) {
                    return false;
                }

                Class<?> hackedClass = failure.getHackedClass();
                String hackedFieldName = failure.getHackedFieldName();
                String hackedMethodName = failure.getHackedMethodName();
                System.out.println("=====onAssertionFailure start=====");
                System.out.println("hackedClass:" + hackedClass);
                System.out.println("hackedFieldName:" + hackedFieldName);
                System.out.println("hackedMethodName:" + hackedMethodName);
                System.out.println("=====onAssertionFailure end=====");
                //返回true不会抛出异常,否则抛出异常

                return true;
            }
        });
        //获得一个不存在的对象,验证onAssertionFailure回调
        Hack.HackedField<Student, String> unknownField = hackPersonByName.field("unknownField").ofType(String.class);
        Hack.HackedField<Student, String> notHandler = hackPersonByName.field("notHandler").ofType(String.class);


    }
}
