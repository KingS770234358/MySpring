package com.designpattern.dp8strategypattern.v1;

public class SaleActivity { // 持有一种促销策略的实现 SaleStrategy
    private SaleStrategy saleStrategy;

    public SaleActivity(SaleStrategy saleStrategy){ // 构造函数中指定促销策略
        this.saleStrategy = saleStrategy;
    }
    public void execute(){ // 执行促销策略
        saleStrategy.doSale();
    }
}
