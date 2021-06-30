package com.pagehelperlearning.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pagehelperlearning.dao.AccountDao;
import com.pagehelperlearning.dao.ConsumerDao;
import com.pagehelperlearning.pojo.Account;
import com.pagehelperlearning.pojo.Consumer;
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
    public void getConsumerById() {
        Consumer c = consumerDao.getConsumerById(2);
        System.out.println(c);
    }

    private final static int NUM_PER_PAGE = 3;
    public PageInfo<Consumer> getAllConsumer(int currentPage) {
        PageHelper.startPage(currentPage, NUM_PER_PAGE);
        List<Consumer> allMessages = consumerDao.getAll(); // 查询出所有结果
        PageInfo<Consumer> pageInfo = new PageInfo<>(allMessages); // 在这里对查询结果进行分页
        return pageInfo;
    }
}
