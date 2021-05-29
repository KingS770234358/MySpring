package com.designpattern.dp8strategypattern.v2;

import com.designpattern.dp8strategypattern.v1.SaleActivity;

/**
 * 策略模式：
 * 优点：
 * （1）开闭原则。
 * （2）可避免使用多重条件语句，如if...else语句、switch语句。
 * （3）使用策略模式可以提高算法的【保密性】和【安全性】。
 * 缺点：
 * （1）客户端必须知道所有的策略，才能决定可以使用哪一个策略类。
 * （2）代码中有非常多的策略类，增加了代码的维护难度。
 */
public class Test {
    public static void main(String[] args) {
        String saleKey = "GROUPBUY";
        // 通过单例模式的策略工厂直接生产策略，避免大量的if else语句
        SaleActivity sa = new SaleActivity(SaleStrategyFactory.getSaleStrategy(saleKey));
        sa.execute();
    }
}
