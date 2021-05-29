package com.designpattern.dp8strategypattern.v1;

public class CashbackStrategy implements SaleStrategy {
    @Override
    public void doSale() {
        System.out.println("返现策略");
    }
}
