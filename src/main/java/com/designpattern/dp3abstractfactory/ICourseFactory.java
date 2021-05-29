package com.designpattern.dp3abstractfactory;

/***
 * 抽象的工厂接口
 * 可以生产该工厂所代表的产品族中的所有产品
 * 这里例子具体指 INote 和 IVideo
 */
public interface ICourseFactory {
    INote createNote();
    IVideo createVideo();
}
