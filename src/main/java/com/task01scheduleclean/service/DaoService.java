package com.task01scheduleclean.service;

import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class DaoService {

    @Autowired
    private ConsumerDao consumerDao;

    @Transactional
    public void insert(List<Consumer> consumers) throws InterruptedException {
        for(Consumer c : consumers){
            int insertRes =  consumerDao.insert(c);
            System.out.println("插入完毕，等待事务提交...");
            Thread.sleep(10000);
        }
    }
    @Transactional
    public int delete(int[] cIds) {
        int cnt = 0;
        Instant startTime = Instant.now();
        for(int cId : cIds){
            cnt += consumerDao.delete(cId);
        }
        Instant endTime = Instant.now();
        // 45.97
        System.out.println("开始时间：" + startTime + " " + "结束时间：" + endTime + " " + "运行时间：" + Duration.between(startTime, endTime));
        return cnt;
    }
}
