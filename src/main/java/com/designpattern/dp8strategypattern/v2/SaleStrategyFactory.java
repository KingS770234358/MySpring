package com.designpattern.dp8strategypattern.v2;

import com.designpattern.dp8strategypattern.v1.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 结合【单例模式】和【工厂模式】来进行优化。
 */
public class SaleStrategyFactory {

    // 工厂模式生产促销策略
    private static Map<String, SaleStrategy> SALE_STRATEGY_MAP = new HashMap<>(); //  存储策略的Map

    // 所有促销策略的Key
    private interface SaleKey{
        String COUPON = "COUPON";
        String CASHBACK = "CASHBACK";
        String GROUPBUY = "GROUPBUY";
    }

    static{
        /*
         * 静态代码块中就存好所有的策略
         * 1）必须知道所有的策略，并且自行决定使用哪一个策略类。
         * 2）代码中会产生非常多的策略类，增加了代码的维护难度。
         */
        // 饿汉式单例模式，直接初始化 SALE_STRATEGY_MAP
        SALE_STRATEGY_MAP.put(SaleKey.COUPON, new CouponStrategy());
        SALE_STRATEGY_MAP.put(SaleKey.CASHBACK, new CashbackStrategy());
        SALE_STRATEGY_MAP.put(SaleKey.GROUPBUY, new GroupbuyStrategy());
    }

    private static final SaleStrategy NON_SALE = new EmptyStrategy(); // 默认的促销策略

    private SaleStrategyFactory(){}

    public static SaleStrategy getSaleStrategy(String saleKey){
        SaleStrategy saleStrategy = SALE_STRATEGY_MAP.get(saleKey);
        return saleStrategy == null ? NON_SALE : saleStrategy;
    }


}
