package com.designpattern.dp7delegatepattern;

import java.util.HashMap;
import java.util.Map;

public class Leader implements IEmployee {

    private Map<String, IEmployee> targets = new HashMap<String, IEmployee>(); // 员工Map

    public Leader(){
        targets.put("加密", new EmployeeA());
        targets.put("登录", new EmployeeB());
    }

    @Override
    public void doing(String command) {
        // Leader将接到的活委派给雇员
        targets.get(command).doing(command);
    }
}
