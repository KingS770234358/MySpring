package com.designpattern.dp7delegatepattern;

public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("员工B：" + command);
    }
}
