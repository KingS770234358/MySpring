package com.task01scheduleclean.service;

import com.task01scheduleclean.dao.AccountDao;
import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Account;
import com.task01scheduleclean.pojo.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerDao consumerDao; // 要测试的类当中用到的其他类 预设/模拟这些类的某些行为

    @Autowired
    private AccountDao accountDao;

    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    public void firstMockitoTest() {
        Consumer c = consumerDao.getConsumerById(2);
        List<Account> al = accountDao.getAccountByConsumerId(2);
        System.out.println(c);
        System.out.println(al);
    }
    /**
     * 插入数据XXX秒后提交
     */
    private static final int COMMIT_AFTER_INSERT = 10000;
    @Transactional
    public void insert(List<Consumer> consumers) throws InterruptedException {
        for (Consumer c : consumers) {
            int insertRes =  consumerDao.insert(c);
            System.out.println("插入完毕，等待事务提交...");
            Thread.sleep(COMMIT_AFTER_INSERT);
        }
    }

    @Transactional
    public int delete(int[] cIds) {
        int cnt = 0;
        Instant startTime = Instant.now();
        List<Account> accountsList;
        for (int cId : cIds) {
            System.out.println("****" + cId);
            accountsList = accountDao.getAccountByConsumerId(cId);
            System.out.println(accountsList);
            cnt += consumerDao.delete(cId);
            if (accountsList.size() == 0) {
                // System.out.println(1);
                cnt -= 1;
            }
        }
        Instant endTime = Instant.now();
        // 45.97
        // System.out.println("开始时间：" + startTime + " " + "结束时间：" + endTime + " " + "运行时间：" + Duration.between(startTime, endTime));
        return cnt;
    }

    public int add(int a) {
        return consumerDao.delete(a);
    }
}
