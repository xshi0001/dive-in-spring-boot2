package com.java.springboot.core.configurationproperties.bean;

import org.springframework.stereotype.Component;

/**
 * @author JClearLove
 * @date 2021/07/16 17:34
 */
@Component
public class Dog {
    private String name;
    private int age;

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

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
