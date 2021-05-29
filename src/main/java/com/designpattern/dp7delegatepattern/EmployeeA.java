package com.designpattern.dp7delegatepattern;

public class EmployeeA implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("员工A：" + command);
    }
}
