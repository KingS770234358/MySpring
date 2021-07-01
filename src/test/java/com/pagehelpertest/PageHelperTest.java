package com.pagehelpertest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pagehelperlearning.PageHelperMain;
import com.pagehelperlearning.pojo.Consumer;
import com.pagehelperlearning.service.ConsumerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class) // 指定测试类使用的运行器为SpringRunner 也可以使用MockitoJUnitRunner
// 指定要运行的SpringBoot应用，同时指定该应用监听的端口，这样可以接收http请求
@SpringBootTest(classes = {PageHelperMain.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PageHelperTest {

    @Before
    public void before(){
    }
    @After
    public void after(){
    }

    @Autowired
    private ConsumerService consumerService;

//    private final static int FIRST_PAGE = 1;
//    private final static int SECOND_PAGE = 2;
    private final static int NUM_PER_PAGE = 3;
    @Test
    public void test() throws InterruptedException {
        int currentPage = 1;
        int limit = 0;
        while(true){
            PageInfo<Consumer> pc = consumerService.getAllConsumer(currentPage);
            // limit = pc.getPageSize(); // getPageSize()返回的是每页的大小 是固定的
            limit = pc.getList().size(); // 每页实际的数据量 - reasonable: true / false 设置会有不同效果
            if(limit <= 0) break;
            for (Consumer c : pc.getList()) {
                System.out.println(c);
            }
            System.out.println("===================第" + currentPage + "页======================");
            Thread.sleep(3000);
            currentPage ++;
        }
    }
}
