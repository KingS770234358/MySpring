package com.designpattern.dp4singletonpattern.register.EnumSingleton;

public class ColorsTest {
    public static void main(String[] args) {
        for (Colors value : Colors.values()) {
            System.out.println(value + ":" + value.getValue() + " " + value.isRest());
        }
        System.out.println(Colors.getColorById(2));
    }
}
