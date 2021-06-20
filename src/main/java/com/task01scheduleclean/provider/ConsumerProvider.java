package com.task01scheduleclean.provider;

//import com.github.pagehelper.util.StringUtil;
import com.task01scheduleclean.pojo.Consumer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;


public class ConsumerProvider {
    /*
    public String queryconsumer(Consumer consumer) {
        StringBuffer sql = new StringBuffer("select * from consumers where 1=1 ");
        if(StringUtil.isNotEmpty(consumer.getName())) {
            sql.append(String.format("and consumername like '%s'", "%"+consumer.getName()+"%"));
        }

        return sql.toString();
    }

    public String batchInsert(Map map) {
        List<Consumer> consumerList = (List<Consumer>)map.get("consumerList");
        StringBuffer sql = new StringBuffer("insert into consumers (consumername,password) values ");

        for(Consumer consumer : consumerList) {
            sql.append(String.format("('%s', '%s'),", consumer.getName(), consumer.getBirthday()));
        }

        sql = sql.deleteCharAt(sql.length() -1);
        System.out.println(sql.toString());
        return sql.toString();
    }

    public String updateconsumer(@Param("U")Consumer consumer) {
        SQL sql = new SQL(){{
            UPDATE("consumers");

            if (StringUtil.isNotEmpty(consumer.getName())){
                SET("nick_name = #{U.nickName}");
            }

            WHERE("id = #{U.id}");
        }};

        return sql.toString();
    }
     */
}
