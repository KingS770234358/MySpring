package com.designpattern.dp8strategypattern.v1;

public class Test {
    public static void main(String[] args) {

        SaleActivity activity1111 = new SaleActivity(new CouponStrategy());
        SaleActivity activity618 = new SaleActivity(new CashbackStrategy());

        activity1111.execute();
        activity618.execute();

        /**
         * 实际应用
         * 当策略很多的时候 会有大量的if else语句
         */
        SaleActivity sa = null;
        String saleKey = "COUPON";
        if("COUPON".equals(saleKey)){
            sa = new SaleActivity(new CouponStrategy());
        }else if("CASHBACK".equals(saleKey)){
            sa = new SaleActivity(new CashbackStrategy());
        }
    }
}
