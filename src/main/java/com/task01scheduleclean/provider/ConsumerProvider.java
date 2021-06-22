package com.task01scheduleclean.provider;

//import com.github.pagehelper.util.StringUtil;
import com.task01scheduleclean.pojo.Consumer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class ConsumerProvider {

    /**
     * 单个参数的动态查询
     * 【重点】：动态条件（注意参数需要使用 final 修饰，以便return new中的匿名内部类对它们进行访问）
     * @param name
     * @return
     */
    public static String getConsumerByNameDynamicSingleParam(final String name) {
        return new SQL(){{
            SELECT("*");
            FROM("consumer");
            if (name != null) {
                // MyBatis中#{xxx}不能拼接！！！
                WHERE("name like #{name}");
            }
            ORDER_BY("id");
        }}.toString();
    }

    /**
     *
     * @param name
     * @param orderByColumn
     * @return
     */
    // 参数列表不使用 @Param，参数列表的定义就必须与 mapper/dao 方法完全相同
    public static String getConsumerByNameDynamicMultiParam(
            final String name, final String orderByColumn) {
        return new SQL(){{
            SELECT("*");
            FROM("consumer");
            WHERE("name like #{name}");
            ORDER_BY(orderByColumn);
        }}.toString();
    }

    // 参数列表使用 @Param，参数列表的定义就可以与 mapper/dao 方法不同，可以只定义需要使用的参数
    public static String getConsumerByNameDynamicMultiParam3(@Param("orderByColumn") final String orderByColumn) {
        return new SQL(){{
            SELECT("*");
            FROM("consumer");
            // WHERE("name like #{name}");
            ORDER_BY(orderByColumn);
        }}.toString();
    }

}
