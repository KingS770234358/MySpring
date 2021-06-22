package com.task01scheduleclean.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account implements Serializable { // 实现Serializable接口 以使用二级缓存
    private Integer id;
    private Double money;
    // 多对一/一对一 一个账户只能属于一个用户
    private int consumerId; // 消费者Id
    private Consumer consumer;
}
