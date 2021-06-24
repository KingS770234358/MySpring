package com.task01scheduleclean.dao;

import com.task01scheduleclean.pojo.Consumer;
import com.task01scheduleclean.provider.ConsumerProvider;
import com.task01scheduleclean.provider.ConsumerProvider2;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Mapper
@Component
@CacheNamespace(blocking = true) // blocking 默认为false 不开启二级缓存
public interface ConsumerDao {

    // 全量查询
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
    String[] getConsumerByBirthday(String date);

    @Select("SELECT id from consumer where birthday <= date_add(now(), interval - #{interval} minute) limit #{limitation}")
    // 这里返回的是int[]数组，不能再使用如下@ResultMap了
    // @ResultMap(value = {"consumerToDB"})
    // 否则报错 java.lang.IllegalArgumentException: argument type mismatch
    int[] getConsumerIdsByBirthdayWithLimitation(int interval, int limitation);

    // 方式一、先查后删
    @Delete("<script>" +
                "DELETE from consumer where" +
                "<foreach collection='cIds' item='cId' separator=' or '>" +
                    "id=#{cId}" +
                "</foreach>" +
            "</script>")
    int deleteBatch(@Param("cIds")int[] cIds);

    @Delete("<script>" +
            "DELETE from consumer where id in" +
            "<foreach collection='cIds' item='cId' separator=',' open='(' close=')'>" +
            "#{cId}" +
            "</foreach>" +
            "</script>")
    int deleteBatch2(@Param("cIds")int[]cIds);
    // 方式二、查完即删
    @Delete("DELETE FROM consumer WHERE id in (SELECT a.cId from (SELECT id as cId from consumer " +
            // 在#{}中指定jdbcType进行 java的Date类型到jdbc TIMESTAMP的转换 这样才能比较
            "where birthday <= #{date,jdbcType=TIMESTAMP} limit #{limitation}) as a)")
    int deleteBatch3(Date date, int limitation);


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 查询时使用动态SQL - SelectProvider
    /*
     * 1.单个参数：type - 指定SQL语句的提供类 method - 指定提供类中提供SQL语句的具体方法
     */
    @SelectProvider(type = ConsumerProvider.class, method = "getConsumerByNameDynamicSingleParam")
    @ResultMap(value = {"consumerToDB"})
    List<Consumer> getConsumerByNameDynamicParam(String name);

    /**
     * 2.多个参数：type - 指定SQL语句的提供类 method - 指定提供类中提供SQL语句的具体方法
     *   该Dao中，参数使用@Param注解，方便在Provider中引用参数。
     *   在Provider的方法参数列表中不使用@Param注解
     *   在查询时，指定按什么属性排序
     * @param name
     * @return
     */
    @SelectProvider(type = ConsumerProvider.class, method = "getConsumerByNameDynamicMultiParam")
    @ResultMap(value = {"consumerToDB"})
    List<Consumer> getConsumerByNameDynamicParam2(@Param("name") String name, @Param("orderByColumn") String orderByColumn);

    /**
     * 3.多个参数：type - 指定SQL语句的提供类 method - 指定提供类中提供SQL语句的具体方法
     *   该Dao中，参数使用@Param注解，方便在Provider中引用参数。
     *   【在Provider的方法参数列表中使用@Param注解】
     *   在查询时，指定按什么属性排序
     * @param name
     * @return
     */
    @SelectProvider(type = ConsumerProvider.class, method = "getConsumerByNameDynamicMultiParam3")
    @ResultMap(value = {"consumerToDB"})
    List<Consumer> getConsumerByNameDynamicParam3(@Param("name") String name, @Param("orderByColumn") String orderByColumn);

    /**
     * 4.Provider实现ProviderMethodResolver接口，实现 Provider中SQL语句获取方法 与 Dao中被标注方法的自动匹配，
     *   而不需要使用@XXXProvider的method属性指定
     */
    @SelectProvider(type = ConsumerProvider2.class)
    @ResultMap(value = {"consumerToDB"})
    Consumer getConsumerByNameImplProviderMethodResolver(@Param("name") String name);
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // 新增

    /**
     * 批量新增
     * @param consumers
     * @return
     */
    @Insert("<script> " +
            "insert into consumer " +
            "(name, address, sex, birthday) " +
            "values " +
            "<foreach collection='consumers' item='consumer' separator=','>" +
            "(#{consumer.consumerName},#{consumer.address},#{consumer.sex},#{consumer.birthday})"+
            "</foreach> " +
            "</script>")
    int batchInsert(@Param("consumers") List<Consumer> consumers);
    @Insert("insert into consumer(id, name,address,sex,birthday) values(#{id}, #{consumerName}, #{address},#{sex},#{birthday})")
    /**
     * 对于SelectKey的理解：
     * 1.Select last_insert_id()或者 Select @@IDENTITY需要与insert语句在同一个连接里才能返回 当前自增id，否则将返回0
     * 2.@SelectKey注解：
     *   before属性指定 statement属性所指定的SQL语句是在 主insertSQL语句 执行之前/之后执行；
     *   resultType属性指定 statement属性所指定的SQL语句 的返回值的类型
     *   keyProperty属性指定将 statement属性所指定的SQL语句的返回结果 赋值到 主insertSQL语句对应 pojo的哪个属性上
     */
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = int.class)
    int insert(Consumer consumer);

    // 删除
    @Delete("DELETE FROM consumer WHERE id =#{id}")
    int delete(int id);
    // 表的嵌套 == 再看看代码的DAO
    @Delete("DELETE FROM consumer WHERE id in (SELECT a.cId from (SELECT id as cId from consumer " +
            // 在#{}中指定jdbcType进行 java的Date类型到jdbc TIMESTAMP的转换 这样才能比较
            "where birthday <= #{date,jdbcType=TIMESTAMP} limit 100000) as a)")
    void deleteRange(Date date);
    @Delete("DELETE * FROM consumer")
    void deleteAll();

    // 更新
    @Update("UPDATE consumer SET address=#{address},birthday=#{birthday} WHERE id =#{id}")
    void update(Consumer consumer);

}
