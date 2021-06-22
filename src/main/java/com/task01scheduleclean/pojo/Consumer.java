package com.task01scheduleclean.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Consumer implements Serializable {
    private int id;
    private String consumerName;
    private String address;
    private String sex;
    private Date birthday;
    private List<Account> accountList;
    public Consumer(int id, String consumerName, String address, String sex, Date birthday){
        this.id = id;
        this.consumerName = consumerName;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
    }

}
