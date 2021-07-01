package com.mapstructlearning.pojo;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Bean;

import java.util.Date;
/**
 * 该类主要用于Lombok的学习演示示例
 */
@Getter
@Setter
// @NoArgsConstructor  // 无参构造函数
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 包含所有属性的构造函数
@RequiredArgsConstructor // 构造函数必须的传参（部分参数的构造函数）
@ToString// (of = {"id", "name", "phone"}, exclude = {"birthday"}, callSuper = true)
@EqualsAndHashCode(of = {"id"})
@Data
@Accessors(fluent = true)
@Builder
public class UserInfo {

    private Long id;
    private String name;
    private String phone;
    private Date birthday;
    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    private String address;

    /**
     * @RequiredArgsConstructor 提供的构造函数包含两中参数
     * 一个是被@NonNull标注的属性
     * 一个是被final关键字修饰的属性
     * 当有final变量的时候，不能有无参构造函数（或是用@NoArgsConstructor注解）
     */
    @NonNull // 配合@RequiredArgsConstructor使用，被@NonNull标注的属性在包含部分参数构造数中
    private String nonNullProp;
    // final：变量定义的时候初始化，或者构造函数中初始化
    private final String finalProp;

}
