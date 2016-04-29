package cn.edu.zafu;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by lizhangqu on 16/4/28.
 */
public class Student implements Serializable {
    private static String school = "清华大学";
    private String name;
    private int age;
    private HashMap<String, Integer> scores = new HashMap<String, Integer>();

    private IBehavior behavior = new BehaviorImpl();

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public IBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(IBehavior behavior) {
        this.behavior = behavior;
    }

    public void addScore(String name, int score) {
        scores.put(name, score);
    }


    public HashMap<String, Integer> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, Integer> scores) {
        this.scores = scores;
    }

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

    public static String getSchool() {
        return school;
    }

    public static void setSchool(String school) {
        Student.school = school;
    }

    public void say(String word) {
        System.out.println(word);
    }


    @Override
    public String toString() {
        return "Student{" +
                "school=" + school +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                ", behavior=" + behavior +
                '}';
    }
}
