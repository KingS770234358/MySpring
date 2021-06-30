package com.pagehelperlearning.provider;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

/*
 * 该Provider实现 ProviderMethodResolver接口
 * 在使用@XXXProvider时，如@SelectProvider(type = ConsumerProvider.class, method = "getConsumerByNameDynamicMultiParam3")
 * 可以不使用method方法指定调用Provider中的哪个方法构造SQL，Provider中的方法名与DAO中被@XXXProvider标注的方法名相同即可
 * ·默认实现中，会将映射器Dao/Mapper方法的调用解析到实现Provider类的同名方法上
 */
public class ConsumerProvider2 implements ProviderMethodResolver {

    /**
     * Dao中的
     * @param name
     * @return
     */
    public static String getConsumerByNameImplProviderMethodResolver(@Param("name") final String name) {
        return new SQL(){{
            SELECT("*");
            FROM("consumer");
            if (name != null) {
                WHERE("name = #{name}");
            }
        }}.toString();
    }
}
