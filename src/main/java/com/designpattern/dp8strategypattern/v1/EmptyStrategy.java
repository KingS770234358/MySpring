package com.designpattern.dp8strategypattern.v1;

public class EmptyStrategy implements SaleStrategy {
    @Override
    public void doSale() {
        System.out.println("无策略");
    }
}
