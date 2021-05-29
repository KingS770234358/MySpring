package com.designpattern.dp7delegatepattern;

/**
 *  1. 老板将command分配给指定的leader
 *  new Boss().command(new Leader(), "登录");
 *  2. leader将command委派给自己雇员map中的员工
 *  targets.get(command).doing(command);
 *
 *  代理模式注重过程，委派模式注重结果
 *  策略模式注重（外部）可扩展性，委派模式注重内部的灵活性和可复用性
 *  委派模式核心：分发、调度、派遣，是【静态代理】和【策略模式】的一种特殊组合
 */
public class Test {
    public static void main(String[] args) {
        new Boss().command(new Leader(), "登录");
    }
}
