package com.task01scheduleclean.dao;

import com.task01scheduleclean.pojo.Consumer;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.jmx.export.annotation.ManagedMetric;

import java.sql.Date;
import java.util.List;

@Mapper
@CacheNamespace(blocking = true) // blocking 默认为false 不开启二级缓存
public interface ConsumerMapper {

    @Select("SELECT * FROM consumer")
    /**
     * pojo 的 属性名 与数据库中表的列不能对应上的时候，需要手动使用@Results和@Result建立它们之间的映射关系
     * 否则 不对应的字段会 成null ==== Consumer(id=2, consumerName=null, address=fuzhou, sex=男, birthday=2021-06-21)
     * 可以指定id属性，给Result起别名，然后在别的地方使用@ResultMap引用
     */
    @Results(id = "consumerToDB",value = {
            // 这里要指定id=true，否则在主键自增的情况下 主键会为0（默认值）
            @Result(id = true, property = "id", column = "id", javaType = java.lang.Integer.class),
            @Result(property = "consumerName", column = "name", javaType = java.lang.String.class),
            @Result(property = "accountList", column = "id", // 这里的id是 账户表的 id？
                // many 属性描述一对多的关系，一个消费者可以有多个账户，使用@Many注解指定如何查找账户列表，
                many = @Many(select = "com.task01scheduleclean.dao.AccountDao.getAccountByConsumerId", fetchType = FetchType.LAZY))})
    List<Consumer> getAll();
    // 模糊查询
    @Select("SELECT * FROM consumer where name like #{name}")
    // 引用上述定义的@Results pojo与数据库表之间的属性映射关系
    @ResultMap(value = {"consumerToDB"})
    List<Consumer> getFuzzy(String name);
    @Select("SELECT * FROM consumer where id = #{id}")
    @ResultMap(value = {"consumerToDB"})
    Consumer getConsumerById(int id);
    @Select("SELECT id from consumer where birthday <= #{date}")
    @ResultMap(value = {"consumerToDB"})
    List<Consumer> getConsumerByBirthday(String date);


    @Insert("insert into consumer(id,name,address,sex,birthday) values(#{id}, #{name}, #{address},#{sex},#{birthday})")
    int insert(Consumer consumer);

    @Delete("DELETE FROM consumer WHERE id =#{id}")
    void delete(int id);
    // 表的嵌套 == 再看看代码的DAO
    @Delete("DELETE FROM consumer WHERE id in (SELECT a.cId from (SELECT id as cId from consumer " +
            // 在#{}中指定jdbcType进行 java的Date类型到jdbc TIMESTAMP的转换 这样才能比较
            "where birthday <= #{date,jdbcType=TIMESTAMP} limit 2) as a)")
    void deleteRange(Date date);

    @Update("UPDATE consumer SET address=#{address},birthday=#{birthday} WHERE id =#{id}")
    void update(Consumer consumer);
/*
    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({ @Result(property = "sex", column = "sex", javaType = SexEnum.class),
            @Result(property = "nickName", column = "nick_name") })
    Consumer getOne(Long id);

    @SelectProvider(type = ConsumerProvider.class, method = "queryUser")
    @Results({
            @Result(property = "sex", column = "sex", javaType = SexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    public List<Consumer> queryUser(Consumer user);

    @InsertProvider(type= ConsumerProvider.class, method = "batchInsert")
    int batchInsert(@Param("userList")List<Consumer> userList);


    @Update("UPDATE users SET userName=#{username},nick_name=#{nickName} WHERE id =#{id}")
    void update(Consumer consumer);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);
    
    @UpdateProvider(type = ConsumerProvider.class, method = "updateUser")
    public int updateUser(@Param("U")Consumer user);
    */
}
