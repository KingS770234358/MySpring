package com.mockitolearning;

import com.task01scheduleclean.pojo.Consumer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Mockito最简单的使用
 */
public class MockitoTest {
    /**
     * 1.验证某个对象的某个行为是否执行过
     * Mockito.verify(对象).行为();
     */
    @Test
    public void validBehaviorIsHappen(){
        // 模拟创建一个List对象
        List<Integer> mock =  Mockito.mock(List.class);
        // 调用mock对象的方法
        // 如果不执行 则报错：Wanted but not invoked: list.add(1); 测试无法通过
        mock.add(1);
        // 如果不执行 则报错：Wanted but not invoked: list.clear();测试无法通过
        mock.clear();
        System.out.println(verify(mock).add(1));
        Mockito.verify(mock).clear();
    }

    /**
     * 2.迭代器生成一系列的对象
     * Mockito.when(iterator.next()).thenReturn(XXX).thenReturn(AAA).thenReturn(BBB)...
     */
    @Test
    public void multiCallReturnDiffValue(){
        //mock一个Iterator类
        Iterator iterator = mock(Iterator.class);
        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        Mockito.when(iterator.next()).thenReturn("hello").thenReturn("world").thenReturn("!");
        //使用mock的对象
        // String result = iterator.next() + " " + iterator.next() + " " + iterator.next() + " " + iterator.next();
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        System.out.println("得到的字符串为：" + result);
        // 验证结果：如果不相同，则报错：
        // Expected :hello world world
        // Actual   :hello world world world
        Assert.assertEquals("hello world !",result);
    }
    /**
     * 3.模拟执行某个对象的某个方法时抛出某种异常
     * Mockito.doThrow(某种异常).when(某个对象).对象的某个方法()
     */
    @Test(expected = IOException.class) // 期望报IO异常
    public void mockException() {
        OutputStream mock = Mockito.mock(OutputStream.class);
        //模拟”【当】输出流关闭的时候抛出IOException“
        try {
            Mockito.doThrow(new IOException("输出流异常...")).when(mock).close(); // 这里也会抛出异常
            mock.close();
        } catch (IOException e) {
            // 捕获模拟抛出的异常
            System.out.println(e.getMessage());
        }
    }
    /**
     * 4.参数匹配
     * 只关心预设的输入和输出，与方法consumer.getSexByInt4MockitTest(xx)本身的逻辑无关
     */
    @Test
    public void matchParameters(){
        Consumer consumer = Mockito.mock(Consumer.class);
        // 预设根据不同的参数返回不同的结果
        Mockito.when(consumer.getSexByInt4MockitTest(1)).thenReturn("女");
        Mockito.when(consumer.getSexByInt4MockitTest(2)).thenReturn("男");
        Assert.assertEquals("女", consumer.getSexByInt4MockitTest(1));
        Assert.assertEquals("男", consumer.getSexByInt4MockitTest(2));
        // 对于没有预设的情况会返回默认值
        Assert.assertEquals(null, consumer.getSexByInt4MockitTest(0));
    }

    /**
     * 5.匹配任意参数
     * 只关心预设的输入和输出，与方法consumer.getSexByInt4MockitTest(xx)本身的逻辑无关
     */
    // 这里的list泛型是根据下面mock(List.class);的类型来的
    class IsValid extends ArgumentMatcher<List> {
        @Override
        public boolean matches(Object obj) {
            return obj.equals(1) || obj.equals(2);
        }
    }
    @Test
    public void matchAnyParameters(){
        List list = Mockito.mock(List.class);
        //匹配任意参数
        Mockito.when(list.get(Mockito.anyInt())).thenReturn(1); // 当list.get(任意Int时) 返回1
        // 当list.contains(x)时，将参数传入IsValid类的matches方法中，如果满足matches方法则返回true，不满足返回false
        Mockito.when(list.contains(Mockito.argThat(new IsValid()))).thenReturn(true);
        System.out.println(list.get(1));
        System.out.println(list.get(999));
        Assert.assertEquals(1,list.get(1));
        Assert.assertEquals(1,list.get(999));
        System.out.println(list.contains(1) + " " + list.contains(3));
        Assert.assertTrue(list.contains(1));
        Assert.assertTrue(!list.contains(3));
    }
    /**
     * 6.匹配自定义参数
     */
    class IsListofTwoElements extends ArgumentMatcher<List>{
        public boolean matches(Object list){
            return((List)list).size()==3;
        }
    }
    @Test
    public void matchCustomizedParameters(){
        //创建mock对象
        List<String> mock = mock(List.class);
        //argThat(Matches<T> matcher)方法用来应用自定义的规则，可以传入任何实现Matcher接口的实现类。
        Mockito.when(mock.addAll(Mockito.argThat(new IsListofTwoElements()))).thenReturn(true);
        System.out.println(mock.addAll(Arrays.asList("one","two","three")));
        Assert.assertTrue(mock.addAll(Arrays.asList("one","two","three")));
        System.out.println(mock.addAll(Arrays.asList("one","two","three","four")));
    }
    /**
     * 7.用spy监控真实对象,设置真实对象行为
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void spyOnRealObjects(){
        List list = new LinkedList();
        List spy = Mockito.spy(list);
        //下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
        //Mockito.when(spy.get(0)).thenReturn(3);
        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        Mockito.doReturn(999).when(spy).get(999);
        //预设size()期望值
        Mockito.when(spy.size()).thenReturn(100);
        //调用真实对象的api
        spy.add(1);
        spy.add(2);
        System.out.println(spy.size() + " " + spy.get(0) + " " + spy.get(1) + " " + spy.get(999));
        Assert.assertEquals(100,spy.size()); // Mockito.when(spy.size()).thenReturn(100);处预设
        Assert.assertEquals(1,spy.get(0));
        Assert.assertEquals(2,spy.get(1));
        Assert.assertEquals(999,spy.get(999)); // Mockito.doReturn(999).when(spy).get(999);处预设
    }
    /**
     * 8.调用真实对象的方法
     */
    class A {
        public String getName(){
            return "zhangsan";
        }
    }
    @Test
    public void callRealMethod() {
        A a = Mockito.mock(A.class);
        // 1.调用mock出来的a对象的方法
        //void 方法才能调用doNothing()
        Mockito.when(a.getName()).thenReturn("a的名字");
        System.out.println(a.getName());
        Assert.assertEquals("a的名字",a.getName());
        // 2.调用真实a对象的方法
        //等价于Mockito.when(a.getName()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(a).getName();
        System.out.println(a.getName());
        Assert.assertEquals("zhangsan",a.getName());
    }
    /**
     * 9.重置mock对象
     */
    @Test
    public void resetMock(){
        List list = mock(List.class);
        Mockito.when(list.size()).thenReturn(10);
        Mockito.when(list.get(Mockito.anyInt())).thenReturn(333);
        Assert.assertEquals(10,list.size());
        System.out.println(list.size());
        System.out.println(list.get(0));
        //重置mock，清除所有的【互动和预设】
        Mockito.reset(list);
        Assert.assertEquals(0,list.size());
        System.out.println(list.size());
        System.out.println(list.get(0));
    }
}
