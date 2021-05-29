package com.designpattern.dp8strategypattern.v1;

public class GroupbuyStrategy implements SaleStrategy {
    @Override
    public void doSale() {
        System.out.println("团购策略");
    }
}
