package com.designpattern.dp5prototypepattern.shallowcopy;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        ConcretePrototypeA ca = new ConcretePrototypeA(); // 具体的待克隆对象
        ca.setAge(18);
        ca.setName("prototype");
        List<String> hobbies = new ArrayList<String>();
        ca.setHobbies(hobbies);
        System.out.println(ca);

        Client c = new Client(ca); // 负责克隆的Client对象
        ConcretePrototypeA caCopy = (ConcretePrototypeA)c.startClone();
        System.out.println(caCopy);

        System.out.println("原对象的引用类型的属性的地址：" + ca.getHobbies());
        System.out.println("克隆对象的引用类型的属性的地址：" + caCopy.getHobbies());
        /**
         * hobbies的引用地址是相同的，意味着复制的不是值，而是引用的地址。
         * 如果修改任意一个对象的属性值，则 concretePrototype 和 concretePrototypeClone的hobbies值都会改变
         *
         * 浅克隆只是完整复制了值类型数据，没有赋值引用对象。换言之，所有的引用对象仍然指向原来的对象，
         */
        System.out.println("对象地址比较：" + (ca.getHobbies() == caCopy.getHobbies()));

    }
}
