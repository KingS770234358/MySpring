package com.designpattern.dp7delegatepattern;

public class Boss {
    // 老板将command分配给指定的leader
    public void command(Leader leader, String command){
        leader.doing(command);
    }
}
