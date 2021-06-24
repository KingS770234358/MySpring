package com.task01scheduleclean.job;

import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Consumer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MySchedulerJob {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Scheduled(cron="*/1 * * * * ?")
    public void jobTest(){
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        ConsumerDao consumerDao = sqlSession.getMapper(ConsumerDao.class);// 使用SqlSession获取Dao的代理对象
//        List<Consumer> all = consumerDao.getAll();
//        System.out.println(all.get(0));

        /*
        String filePath = "E:\\workspace\\MySpring\\src\\main\\resources\\schedulerTest.txt";
        File f = new File(filePath);
        try{
            if(!f.exists()){
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath, true);
            fw.write("任务调度测试 " + new Date().toString() + "\r\n");
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        */
    }
}
