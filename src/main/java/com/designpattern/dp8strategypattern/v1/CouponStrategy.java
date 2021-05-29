package com.designpattern.dp8strategypattern.v1;

public class CouponStrategy implements SaleStrategy{
    @Override
    public void doSale() {
        System.out.println("优惠券策略");
    }
}
