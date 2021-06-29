package com.mybytistest;

import com.task01scheduleclean.SchedulerApplication;
import com.task01scheduleclean.dao.AccountDao;
import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Account;
import com.task01scheduleclean.pojo.Consumer;
import com.task01scheduleclean.service.ConsumerService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class MyBatisAnnotationTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private ConsumerDao consumerDao;
    private ConsumerService consumerService;
    @Before
    public void getDaoProxy(){
        ApplicationContext ac = SpringApplication.run(SchedulerApplication.class);
        this.sqlSessionFactory = ac.getBean(SqlSessionFactory.class); // SqlSessionFactory工厂
        this.sqlSession = this.sqlSessionFactory.openSession(); // SqlSession
        this.consumerDao = sqlSession.getMapper(ConsumerDao.class);// 使用SqlSession获取Dao的代理对象
        this.consumerService = ac.getBean(ConsumerService.class);
    }
    @After
    public void closeSqlSession(){
        this.sqlSession.close();
    }

    /**
     * 事务测试
     */
    @Test
    public void transactionTest() throws InterruptedException {

        Consumer c1 = new Consumer(0,"nnnnnnnnnnnnnnn","fz","nan",new Date());
//        Consumer c2 = new Consumer(0,"xq11","fz","nv",new Date());
//        Consumer c3 = new Consumer(0,"mm11","fz","nv",new Date());
        List<Consumer> consumers = new ArrayList<>();
        consumers.add(c1);
//        consumers.add(c2);
//        consumers.add(c3);
        this.consumerService.insert(consumers);
    }

    /**
     * 耗时测试
     */
    @Test
    public void consumeTimeTestInsert(){
        // this.sqlSession = this.sqlSessionFactory.openSession(false); // SqlSession
        // this.consumerDao = this.sqlSession.getMapper(ConsumerDao.class);// 使用SqlSession获取Dao的代理对象
        List<Consumer> consumers = new ArrayList<>();
        for(int i=0; i<300000; i++){
            consumers.add(new Consumer(0, "wqiang" + i, "suzhou" + i, "男", new Date()));
            if((i+1)%1000 == 0){
                this.consumerDao.batchInsert(consumers);
                consumers.clear();
            }
        }
        // sqlSession.commit();
    }
    /**
     * 先查后删
     * 开启事务 逐个删除 最后提交事务
     */
    @Test
    public void deleteOneByOneThenCommit() {
        int delNum = Integer.MAX_VALUE;
        int limitation = 3000;
        while(delNum>=limitation){
            int[] cIds = consumerDao.getConsumerIdsByBirthdayWithLimitation(1, limitation);
            System.out.println("查到：" + cIds.length + "条数据。");
            delNum = consumerService.delete(cIds);
            System.out.println("删除：" + delNum + "条数据。");
        }
    }
    /**
     * 按范围删除
     */
    @Test
    public void deleteInRange(){
        int delNum = Integer.MAX_VALUE;
        int limitation = 100000;
        Instant startTime = Instant.now();
        while(delNum>=limitation){
            delNum = consumerDao.deleteBatch3(new Date(Calendar.getInstance().getTimeInMillis()), limitation);
            // System.out.println(delNum);
        }
        Instant endTime = Instant.now();
        // 18.8s 18.27 17.21
        System.out.println("开始时间：" + startTime + " " + "结束时间：" + endTime + " " + "运行时间：" + Duration.between(startTime, endTime));
    }

    @Test
    public void deleteInRange2(){
        // int delNum = Integer.MAX_VALUE;
        int limitation = 1000; // 先查后删 limitation不能太大
        Instant startTime = Instant.now();
        // int[] cIds = consumerDao.getConsumerIdsByBirthdayWithLimitation(1, limitation);
        while(true){
            int[] cIds = consumerDao.getConsumerIdsByBirthdayWithLimitation(1, limitation);
            if(cIds.length!=0){ // 这里一定要判断cIds数组是否为空，否则 <script>的拼接会出语法错误
                consumerDao.deleteBatch(cIds); // 1-40s 31s   2-25.5 30.58
            }else{
                break;
            }
            // System.out.println(delNum);
        }
        Instant endTime = Instant.now();
        System.out.println("开始时间：" + startTime + " " + "结束时间：" + endTime + " " + "运行时间：" + Duration.between(startTime, endTime));
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
        List<Account> c = accountDao.getAccountByConsumerId(2);
        System.out.println(c.size());
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
     * 按Id获取消费者
     */
    @Test
    public void getConsumerByIdTest(){
        Consumer c = consumerDao.getConsumerById(2);
        System.out.println(c);
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
