package com.task01scheduleclean.dao;

import com.task01scheduleclean.pojo.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 测试 一对一/多对一的配置查询
 */
@Mapper
public interface AccountDao {
    @Select("SELECT * FROM account where id=#{id}")
    @Results(id="accountToDB", value = {
            @Result(property = "consumer", column = "consumerId", // 这里的column要和数据库中的字段相同
                    // one 属性描述多对一/一对一的关系，一个账户只属于一个消费者，使用@One注解指定如何查找该消费者，
                    // fetchType是该字段初始化的方式， 有 LAZY EAGER 和 DEFAULT三种取值 多对一/一对一的时候选择EAGER
                    one = @One(select = "com.task01scheduleclean.dao.ConsumerDao.getConsumerById", fetchType = FetchType.EAGER))
    })
    Account getAccountById(int id);

    // 用于测试 一对多 ---- 一个用户可以拥有多个账户 根据用户的id查询出所有该用户的账户信息
    @Select("SELECT * FROM account where consumerid=#{consumerId}")
    List<Account> getAccountByConsumerId(int consumerId);
}
