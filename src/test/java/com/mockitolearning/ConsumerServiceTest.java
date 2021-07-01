package com.mockitolearning;

import com.task01scheduleclean.SchedulerApplication;
import com.task01scheduleclean.dao.AccountDao;
import com.task01scheduleclean.dao.ConsumerDao;
import com.task01scheduleclean.pojo.Account;
import com.task01scheduleclean.pojo.Consumer;
import com.task01scheduleclean.service.ConsumerService;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Matchers.anyInt;


@RunWith(SpringRunner.class) // 指定测试类使用的运行器为SpringRunner 也可以使用MockitoJUnitRunner
// 指定要运行的SpringBoot应用，同时指定该应用监听的端口，这样可以接收http请求
@SpringBootTest(classes = {SchedulerApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConsumerServiceTest {

    /**
     * 1. 当只有@InjectMocks注解的情况下，该对象中所有用到的方法都是mock的，
     *    如果其中调用了 某个被注入的引用属性的某个方法， 但该引用属性没有具体设置则会抛出空指针异常
     *    如此处consumerService中的accountDao
     *    因此需要将其中所有用到的 被注入的引用属性 都mock
     *
     *
     * 2. 当只有@Autowired注解的情况下，该对象中所有用到的方法都是真实的，此处设置的mock预设都会失效
     * 3. 当同时有@InjectMocks和@Autowired注解的情况下：
     * @InjectMocks 注解在上方，且 consumerDao 为 @Mock 所修饰：使用的都是真实方法，mock无效
     * @InjectMocks 注解在上方，且 consumerDao 为 @Autowired 所修饰：
     * consumerDao为真实对象，无法进行mock的预设，会抛出异常
     * @InjectMocks 注解在下方，且 consumerDao 为 @Mock 所修饰：使用的都是真实方法，mock无效
     * @InjectMocks 注解在下方，且 consumerDao 为 @Autowired 所修饰：
     * consumerDao为真实对象，无法进行mock的预设，会抛出异常
     *
     * 4. 保留部分真实方法，只模拟部分方法
     * 如 类A 依赖 类B 和 类C，此时只mock C的方法，可以
     * - @Autowired标注类A ，然后@Mock标注类C，在@Before中使用A对于C的setter方法将 mock的C类设置到真实的A类中
     * 或者
     * - @InjectMocks标注类A，然后@Autowired标注类B，在@Before中使用A对于B的setter方法将真实的B类设置到mock的A类中
     *   这步要多声明1个 @Mock标注的C 才可以对C进行 mock预设
     */
    /**
     * 保留部分真实方法，只模拟部分方法 方案一、
     */
//    @Autowired
//    private ConsumerService consumerService;
//
//    @Mock
//    private ConsumerDao consumerDao; // 要测试的类当中用到的其他类 预设/模拟这些类的某些行为

    /**
     * 保留部分真实方法，只模拟部分方法 方案二、 多声明1个 @Mock标注的C 才可以对C进行 mock预设
     */
    @InjectMocks
    private ConsumerService consumerService;

    @Mock
    private ConsumerDao consumerDao;

    @Mock
    private AccountDao accountDao; // 要测试的类当中用到的其他类 预设/模拟这些类的某些行为

    //@Autowired
    //private WebApplicationContext wac; // 要求SpringBoot要引入web的依赖

    // mock api 模拟http请求，构建方式在setUp()方法中
    //private MockMvc mockMvc;

    // 预期异常
    @Rule
    public ExpectedException exceptionExpected = ExpectedException.none();

    @Before
    public void setUp(){
        // 如果不使用MockitoJunitRunner，则需要initMocks
        // 集成Web环境测试（此种方式并不会集成真正的web环境，而是通过相应的Mock API进行模拟测试，无须启动服务器）
        MockitoAnnotations.initMocks(this);
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();   //构造MockMvc
        /**
         * 保留部分真实方法，只模拟部分方法 方案一、
         */
        // consumerService.setConsumerDao(consumerDao);
        /**
         * 保留部分真实方法，只模拟部分方法 方案二、 多声明1个 @Mock标注的C 才可以对C进行 mock预设
         */
        // consumerService.setAccountDao(accountDao);
    }
    @After
    public void tearDown(){
    }

    @Test
    public void notMockDao(){
//        Mockito.when(mockitoTest.c(Mockito.anyInt())).thenReturn(2333);
    }

    @Test
    public void accountAPITest(){
        consumerService.firstMockitoTest();
    }

    @Test
    public void firstMockitoTest(){
        // 使用any()会匹配不到consumerDao对象，consumerDao对象抛出空指针异常
        Mockito.when(consumerDao.delete(anyInt())).thenReturn(2333);
        // Mockito.doCallRealMethod().when(accountDao).getAccountByConsumerId(anyInt());
        // Mockito.when(accountDao.getAccountByConsumerId(anyInt())).thenReturn(null);
        int sum = 0;
        sum += consumerService.delete(new int[]{1, 2});
        System.out.println("总合：" + sum);
    }

//    @Test
//    public void mockMVCTest() throws Exception {
//        String content = "hello world";
//        mockMvc.perform( MockMvcRequestBuilders
//                        .post("/parkingsearch")
//                        .accept(MediaType.APPLICATION_JSON_UTF8).content(new String(content.getBytes("GB2312"), "utf-8"))
//                        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.resCode").value(0))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.resData.data").isArray())
//                        .andExpect(MockMvcResultMatchers.status().isOk());
//    }
}
