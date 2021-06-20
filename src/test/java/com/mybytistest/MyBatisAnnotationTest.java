package com.mybytistest;

import com.task01scheduleclean.SchedulerApplication;
import com.task01scheduleclean.dao.AccountDao;
import com.task01scheduleclean.dao.ConsumerMapper;
import com.task01scheduleclean.pojo.Account;
import com.task01scheduleclean.pojo.Consumer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class MyBatisAnnotationTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private ConsumerMapper consumerMapper;
    @Before
    public void getDaoProxy(){
        ApplicationContext ac = SpringApplication.run(SchedulerApplication.class);
        this.sqlSessionFactory = ac.getBean(SqlSessionFactory.class); // SqlSessionFactory工厂
        this.sqlSession = this.sqlSessionFactory.openSession(); // SqlSession
        this.consumerMapper = sqlSession.getMapper(ConsumerMapper.class);// 使用SqlSession获取Dao的代理对象
    }
    @After
    public void closeSqlSession(){
        this.sqlSession.close();
    }

    /**
     * 按范围删除
     */
    @Test
    public void deleteInRange(){
        consumerMapper.deleteRange(new Date(Calendar.getInstance().getTimeInMillis()-24*60*60*1000));
    }

    /**
     * 二级缓存测试
     * 0.Consumer类要实现Serializable接口
     * 1.在mybatis的全局配置中要配置开启缓存（默认是开启的） mybatis:cache-enabled: true
     * 2.在对应的Dao加上注解@CacheNamespace(blocking = true) 开启二级缓存
     * 3.测试
     */
    @Test
    public void getConsumerFrom2LCache(){
        Consumer consumer = consumerMapper.getConsumerById(2);
        Consumer consumer1 = consumerMapper.getConsumerById(2);
        System.out.println(consumer==consumer1); // 同一个SqlSession（一级缓存中取出的数据对象）是相同的
        sqlSession.close();// 关闭SqlSession再重启一个，清空了一级缓存
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        ConsumerMapper cm = sqlSession1.getMapper(ConsumerMapper.class);// 使用SqlSession获取Dao的代理对象
        Consumer consumer2 = cm.getConsumerById(2);
        System.out.println(consumer==consumer2); // 不开启二级缓存返回false，开启二级缓存返回true
    }

    /*
    * 一对多测试
    * 查询用户信息的同时查出该用户下所有的账户信息
    * */
    @Test
    public void getConsumerByIdWithAccoutsTest(){
        Consumer consumer = consumerMapper.getConsumerById(2);
        System.out.println(consumer);
    }

    /**
     * 多对一/一对一测试
     * 查询账户信息的同时查出账户所属消费者的信息
     */
    @Test
    public void getAccountByIdTest(){
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        Account account = accountDao.getAccountById(1);
        System.out.println(account);
    }

    /**
     * 获取所有消费者
     */
    @Test
    public void getAllTest(){
        List<Consumer> all = consumerMapper.getAll();
        System.out.println(all.get(0));
    }
    @Test
    public void getFuzzyTest(){
        List<Consumer> all = consumerMapper.getFuzzy("%w%");
        System.out.println(all.get(0));
    }
    /**
     * 新增消费者
     */
    @Test
    public void insetTest(){

        System.out.println(consumerMapper.insert(new Consumer(0, "wqiang", "suzhou", "男", new Date(Calendar.getInstance().getTimeInMillis()))));
    }
    /**
     * 删除消费者
     */
    @Test
    public void deleteTest(){
        consumerMapper.delete(1);
    }

    @Test
    public void updateTest(){
        consumerMapper.update(new Consumer(2, "wqiang", "fuzhou", "男", new Date(Calendar.getInstance().getTimeInMillis()+24*60*60*1000)));
    }



}
