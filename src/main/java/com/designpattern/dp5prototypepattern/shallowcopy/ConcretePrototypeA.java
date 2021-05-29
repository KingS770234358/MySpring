package com.designpattern.dp5prototypepattern.shallowcopy;

import java.util.List;

/**
 * 具体的拷贝逻辑
 */
public class ConcretePrototypeA implements Prototype {

    private String name;
    private int age;
    private List hobbies;

    @Override
    public ConcretePrototypeA clone() { // 这里返回的是具体的Prototype实现类
        ConcretePrototypeA result = new ConcretePrototypeA();
        result.setName(this.name);
        result.setAge(this.age);
        result.setHobbies(this.hobbies);
        return result;
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

    public List getHobbies() {
        return hobbies;
    }

    public void setHobbies(List hobbies) {
        this.hobbies = hobbies;
    }
}
