package com.mybytistest;

import com.task01scheduleclean.SchedulerApplication;
import com.task01scheduleclean.dao.AccountDao;
import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Account;
import com.task01scheduleclean.pojo.Consumer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class MyBatisAnnotationTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private ConsumerDao consumerDao;
    @Before
    public void getDaoProxy(){
        ApplicationContext ac = SpringApplication.run(SchedulerApplication.class);
        this.sqlSessionFactory = ac.getBean(SqlSessionFactory.class); // SqlSessionFactory工厂
        this.sqlSession = this.sqlSessionFactory.openSession(); // SqlSession
        this.consumerDao = sqlSession.getMapper(ConsumerDao.class);// 使用SqlSession获取Dao的代理对象
    }
    @After
    public void closeSqlSession(){
        this.sqlSession.close();
    }

    /**
     * 耗时测试
     */
    @Test
    public void consumeTimeTestInsert(){
        //this.sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH); // SqlSession
        //this.consumerMapper = this.sqlSession.getMapper(ConsumerMapper.class);// 使用SqlSession获取Dao的代理对象
        for(int i=0; i<80000; i++){
            consumerDao.insert(new Consumer(0, "wqiang" + i, "suzhou" + i, "男", new Date()));
        }
        sqlSession.commit();
    }
    @Test
    public void timeTest(){
        Instant startTime = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant endTime = Instant.now();
        System.out.println("开始时间：" + startTime);
        System.out.println("结束时间：" + endTime);
        System.out.println("运行时间：" + Duration.between(startTime, endTime));
    }
    @Test
    public void deleteInRange8w(){
        Instant startTime = Instant.now();
        consumerDao.deleteRange(new Date());
        Instant endTime = Instant.now();
        // 1.1239923S
        System.out.println("开始时间：" + startTime + " " + "结束时间：" + endTime + " " + "运行时间：" + Duration.between(startTime, endTime));
    }
    public void deleteAll8w(){
        Instant startTime = Instant.now();
        consumerDao.deleteAll();
        Instant endTime = Instant.now();
        // 1.1239923S
        System.out.println("开始时间：" + startTime + " " + "结束时间：" + endTime + " " + "运行时间：" + Duration.between(startTime, endTime));
    }
    /**
     * 按范围删除
     */
    @Test
    public void deleteInRange(){
        consumerDao.deleteRange(new Date(Calendar.getInstance().getTimeInMillis()));
    }

    /**
     *
     */
    @Test
    public void getConsumerByNameImplProviderMethodResolverTest(){
        Consumer consumer = consumerDao.getConsumerByNameImplProviderMethodResolver("wq");
        System.out.println(consumer);
    }
    /**
     * 多个参数的动态SQL查询 - Provider 方法的参数列表使用@Param注解，只定义需要的参数
     * 例子中的第一个参数"w%"没有被Provider方法使用，Provider只用了需要的排序参数
     */
    @Test
    public void getConsumerByNameDynamicParam3Test(){                                      // 指定按什么属性排序，且指定降/升序
        List<Consumer> consumerList = consumerDao.getConsumerByNameDynamicParam3("w%", "id desc");
        System.out.println(consumerList.size());
        consumerList.forEach(c->{
            System.out.println(c);
        });
    }

    /**
     *
     * 多个参数的动态SQL查询 - Provider 方法的参数列表不使用@Param注解
     */
    @Test
    public void getConsumerByNameDynamicParam2Test(){                                      // 指定按什么属性排序，且指定降/升序
        List<Consumer> consumerList = consumerDao.getConsumerByNameDynamicParam2("w%", "id desc");
        System.out.println(consumerList.size());
        consumerList.forEach(c->{
            System.out.println(c);
        });
    }

    /**
     * 单个参数的动态SQL查询
     */
    @Test
    public void getConsumerByNameDynamicParamTest(){
        List<Consumer> consumerList = consumerDao.getConsumerByNameDynamicParam("w%");
        System.out.println(consumerList.size());
        consumerList.forEach(c->{
            System.out.println(c);
        });
    }

    /**
     * @SelectKey 标签的测试
     */
    @Test
    public void retAutoIncrePriKeyAfterInsert(){
        Consumer consumer = new Consumer(0, "wqiang", "suzhou", "男", new Date(Calendar.getInstance().getTimeInMillis()));
        int insertCount = consumerDao.insert(consumer);
        System.out.println(consumer.getId());
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
        Consumer consumer = consumerDao.getConsumerById(2);
        Consumer consumer1 = consumerDao.getConsumerById(2);
        System.out.println(consumer==consumer1); // 同一个SqlSession（一级缓存中取出的数据对象）是相同的
        sqlSession.close();// 关闭SqlSession再重启一个，清空了一级缓存
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        ConsumerDao cm = sqlSession1.getMapper(ConsumerDao.class);// 使用SqlSession获取Dao的代理对象
        Consumer consumer2 = cm.getConsumerById(2);
        System.out.println(consumer==consumer2); // 不开启二级缓存返回false，开启二级缓存返回true
    }

    /*
    * 一对多测试
    * 查询用户信息的同时查出该用户下所有的账户信息
    * */
    @Test
    public void getConsumerByIdWithAccoutsTest(){
        Consumer consumer = consumerDao.getConsumerById(2);
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
        List<Consumer> all = consumerDao.getAll();
        System.out.println(all.get(0));
    }
    @Test
    public void getFuzzyTest(){
        List<Consumer> all = consumerDao.getFuzzy("%w%");
        System.out.println(all.get(0));
    }
    /**
     * 新增消费者
     */
    @Test
    public void insetTest(){
        System.out.println(consumerDao.insert(new Consumer(0, "wqiang", "suzhou", "男", new Date(Calendar.getInstance().getTimeInMillis()))));
    }
    /**
     * 删除消费者
     */
    @Test
    public void deleteTest(){
        consumerDao.delete(1);
    }

    /**
     * 更新消费者
     */
    @Test
    public void updateTest(){
        consumerDao.update(new Consumer(2, "wqiang", "fuzhou", "男", new Date(Calendar.getInstance().getTimeInMillis()+24*60*60*1000)));
    }



}
