package com.mapstructlearning;

import com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxy.Person;
import com.mapstructlearning.pojo.UserInfo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        // @AllArgsConstructor注解
        UserInfo p = new UserInfo(1L, "wq", "19311113132", new Date(), "sz", "nonNull", "finalProp");
        // @RequiredArgsConstructor注解
        UserInfo p2 = new UserInfo("nonNull", "finalProp");
        // @Setter注解
        // p.setId(1L);
        p.id(1L);
        System.out.println(p);
        UserInfo p3 = new UserInfo(1L, "wq", "19311113132", new Date(), "sz", "nonNull", "finalProp");

        Set<UserInfo> pSet = new HashSet<>();
        pSet.add(p);
        pSet.add(p3);
        // 由于p 和p3 所有的属性都相同，根据默认的hashCode计算方式得到的hashCode一致，被集合视为相同的东西
        System.out.println(pSet.size());

        UserInfo p4 = new UserInfo(1L, "感刚刚", "22222", new Date(), "fz", "nonNull222", "final222Prop");
        UserInfo p5 = new UserInfo(1L, "wq", "19311113132", new Date(), "sz", "nonNull", "finalProp");
        // 使用@EqualsAndHashCode注解指定hashCode根据哪些属性生成后，这里指定根据UserInfo的id属性生成
        // 即使p4 p5的属性不一样，由于他们的id一样，根据@EqualsAndHashCode注解指定的hashCode生成方法，
        // p4 p5 的hashCode一样，被集合视为同一个对象
        pSet.clear();
        pSet.add(p4);
        pSet.add(p5);
        System.out.println(pSet.size());
        // 在指定以id生成hashCode的前提下，只有id不同，hashCode才会不同，生成的对象才会被集合视为不同的对象
        UserInfo p6 = new UserInfo(2L, "感刚刚", "22222", new Date(), "fz", "nonNull222", "final222Prop");
        pSet.clear();
        pSet.add(p4);
        pSet.add(p6);
        // p4 和 p6除了id以外，其他字段完全相同 被集合视为不同的对象
        System.out.println(pSet.size());
        UserInfo p7 = new UserInfo(2L, "感刚刚", "22222", new Date(), "fz", "nonNull222", "final222Prop");
        p7.name("wq").birthday(new Date()).id(3L);
        p7.name();
        UserInfo.UserInfoBuilder userInfoBuilder = UserInfo.builder();
        userInfoBuilder.id(1L).address("sz");
        UserInfo u = userInfoBuilder.build();
    }
}
