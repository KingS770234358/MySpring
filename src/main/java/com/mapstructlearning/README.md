#### 1.Lombok
官网：https://projectlombok.org/
作用：简化pojo类代码的书写（getter、setter、toString、equals、hashCode、Constructor等等）

一、pojo 和 java bean
pojo（plain old/ordinary java object，简单java对象）
==不包括业务逻辑且能够控制自己内部n和属性访问的java对象==
不同于Controller（SpringCloud）/facade（RPC）、XXXDTO/XXBO、Service、DAO/PAO（persist）层等对象。
            
        |--->xXRequest--->xXDTO(封装参数)-------->|
        |                                        |
Client——|                                    xXDo/xXPO
        |                                        |
        <---xXVo（视图层） <---xXBo/xXDTO<---------|
       Controller/Facade    业务层Service     数据访问层/持久层
pojo vs javabeans
javabeans是pojo的真子集
pojo                                          javabeans
不需要有无参构造函数                             必须有无参的构造函数
属性的访问权限无限制==>外部可以通过属性名访问        private=>使用getter和setter访问
不包含业务逻辑                                  不包含业务逻辑
二、配置
1. pom.xml中导入lombok依赖（引入lombok的jar包）
2. settings->build,Execution,Deployment->compile->annotation processors->勾选enable annotation processing --- jsr269都需要勾选
3. 安装 lombok 插件，settings->plugins->lombok安装
三、使用
△可以在IDEA的Structure窗口中可以查看当前pojp的结构，包含属性和方法等
△也可以用maven编译后，打开target中编译好的.class文件查看生成的构造方法
·@NoArgsConstructor  // 无参构造函数
·@AllArgsConstructor(access = AccessLevel.PACKAGE) // 包含所有属性的构造函数，可以通过access属性设置构造方法的访问级别，默认public
·@RequiredArgsConstructor // 构造函数必须的传参（部分参数的构造函数）
```java
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
```
·@Getter
·@Setter
```java
    @Setter(value = AccessLevel.NONE) // 通过在指定属性上指定@Setter注解的value属性 不生成该属性的Setter方法
    @Getter(value = AccessLevel.NONE) // 通过在指定属性上指定@Getter注解的value属性 不生成该属性的Getter方法
    private String address;
    private String name;
    public String getName(){ // 自己编写的Getter方法级别更高，会覆盖@Getter注解生成的Getter方法。
        return "我自己写的" + this.name;    
    }
```
·@ToString
原有的toString打印的是类名及其hashCode
```java
// of属性指定将哪些属性加入到toString方法中，
// exclude属性指定将哪些属性排除到toString方法之外
// callSuper属性指定是否拼接父类的toString方法结果
@ToString(of={"id", "name", "phone"}, exclude = {"birthday"}, callSuper = true)
```
·@EqualAndHashCode
使用of属性指定根据哪些属性计算hashCode
```java
@EqualsAndHashCode(of = {"id"})
```
·@Data =  @Getter + @Setter + @ToString + @EqualsAndHashCode 的默认实现
 用的是所有的属性
 必要场景下可以使用@EqualsAndHashCode等特定注解重写指定的方法以排除掉部分属性
·@Accessors
chain属性
```java
// 将每个Setter的返回值都设置为对象本身-this，以支持链式编程
// 前提是该属性的Setter没有被@Setter(value = AccessLevel.NONE)排除
@Accessors(chain = true) 
```
```java
    UserInfo p7 = new UserInfo(2L, "感刚刚", "22222", new Date(), "fz", "nonNull222", "final222Prop");
    p7.setId(3L).setBirthday(new Date()).setName("ssss");
```
fluent属性
```java
// fluent = true，默认支持了chain = true，支持链式编程；
// 同时 可以直接使用 对象名.属性名(属性值)的方式 直接对属性进行设置
// 可以直接使用 对象名.属性名()的方式 直接对属性进行访问
@Accessors(fluent = true)
```
```java
    UserInfo p7 = new UserInfo(2L, "感刚刚", "22222", new Date(), "fz", "nonNull222", "final222Prop");
    p7.name("wq").birthday(new Date()).id(3L);
    p7.name();
```
·@Builder
该注解与@Accessors注解实现的功能类似，但是是基于 建造者模式 实现的
该注解没有实现toString()方法
```java
@Builder // 在类的内部生成该类的 建造器 对象
```
```java
    // 1.获得@Builder注解为该类生成的构造器对象
    UserInfo.UserInfoBuilder userInfoBuilder = UserInfo.builder();
    // 2.该构造器对象基于 @Accessor(fluent=true)的方式对对象属性进行设置
    userInfoBuilder.id(1L).address("sz");
    // 3.属性值设置完毕后，调用构造器的build()方法完成最后的 实例化
    UserInfo u = userInfoBuilder.build();
```
对日志提供的支持
·@Log 
ctrl点击@Log注解进入可以看到 它是针对项目里以java.util.logging.Logger作为日志对象的
```java
 // @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html">java.util.logging.Logger</a>
```
·@Slf4j 针对项目里以slf4j作为日志的
不使用@Slf4j注解的情况下实现日志
```java
public class UserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);
    public List<UserInfo> getAll() {
        LOGGER.info("进入getAll方法");
        return new ArrayList<>();
    }
}
```
使用@Slf4j注解实现日志
```java
@Slf4j
public class UserInfoService {
    //private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);
    public List<UserInfo> getAll() {
        log.info("进入getAll方法"); // 默认生成 变量名为 log的 日志对象
        return new ArrayList<>();
    }
}
```
@Log4j
@Log4j2
四、原理及应用
通过SPI 服务提供接口 机制实现   @Code 注解 
编译期间操作字节码
asm语法树 感知代码的变化 和 属性等的生成
jsr的故事：代码简洁之道，
jsr                   描述                                             相关实现
jsr107                缓存规范                                          Spring基于此实现缓存体系
jsr250                java平台Common Annotations，如@PostConstruct      Spring
jsr269                Pluggable Annotation Processing API              lombok, mapstruct
                     （插件化注解处理API）                 
jsr303,jsr349,jsr380  bean validation（bean校验）                       hibernate-validator

#### 2.Java 规范 jsr
· 是什么？
一堆不相关的java package组成了java ee规范，都是api，没有实现，由不同的厂商具体实现。
· 在哪里？
javax开头的包都是常用的java ee规范（不限于）
javax.sql ---- mysql, sqlServer, oracle
javax.servlet --- tomcat, jetty...等web服务器
javax.persistence --- hibernate
javax.transaction --- 分布式事务
javax.xml --- jdk带了自己的实现 jaxp: java api for processing
对于jdk没有自带的规范需要自己引入，比如beanvalidation等...
·由谁？如何制定？
-由jcp内部的成员制定，中国代表：Alibaba
jcp(java community process)官网：jcp.org/en/home/index
-如何制定？
jcp是一个开源的国际组织，由社区成员提出jsr(java specification requests, java规范提案)
·从javax走向jakarta
参考：https://blogs.oracle.com/theaquarium/opening-up-java-ee
oracle收购sum公司，java属于oracle，oracle迫于java开源社区的压力，决定 java ee规范 捐献给eclipse基金会
但不允许java ee规范继续使用javax的包命名方式，eclipse基金会讨论决定将javax改名为Jakarta开头，上述那些规范api以后都会使用Jakarta命名=
·注意区别 eclipse基金会的Jakarta与Apache的Jakarta，后者于2011年退休
Apache的Jakarta官网：https://jakarta.apache.org/
Apache的Jakarta下的一些项目：ant、log4j、maven、struts、commons-*.jar、jmeter、poi、Lucene、===tomcat===

#### 3.Mapstruct
Lombok解决的是单个pojo的书写
MapStruct解决不同pojo之间的转
1.MapStruct简介
·官网：https://mapstruct.org/
·文档：https://mapstruct.org/documentation/reference-guide/
·使用版本：1.3.1Final
·使用场景：不同pojo之间的转换
·不同的解决方案：
框架                                              描述
mapstruct                                        基于jsr269实现在编译期间生成代码，性能高，控制精细，解耦
orika                                            控制精细，解耦
org.springframework.beans.BeanUtils              简单易用，但不能对属性进行定制处理               
2.不使用MapStruct框架的缺点
·多而杂的代码与业务逻辑耦合
·重复的劳动（代码复用率低）
3.MapStruct的使用（各种注解
3.1 pom.x1引入MapStruct依赖
3.2 新建pojo转换类（接口、抽象类都行，jdk1.8后推荐接口），标注Mapper注解
```java
/**
 * car相关的pojo之间的转化
 */
@Mapper // 这里是MapStruct的Mapper
public interface CarConvert {
    /**
     * CarConvert转换对象
     */
    // 实际的开发中可以注册到容器中 不写
    CarConvert INSTANCE = Mappers.getMapper(CarConvert.class);
    /**
     * CarDto --> CarVo
     * 默认规则：
     * ·属性如果同类型、同名 自动映射复制
     * ·属性如果同名，会自动进行类型转换：
     *     1.8种基本类型和他们对应的包装类型之间
     *     2.8种基本类型（和他们对应的包装类）和String之间
     *     3.日期类型和String之间
     *     参考官网...
     */
    CarVO convertCarDTO2CarVO(CarDTO carDTO);
}

```

3.3自定义属性映射规则
·可以在target/generated-sources中查看MapStruct生成的实际执行pojo转换的Convert类
·source或target多余或缺少对方的属性是不会报错的
·修改后如果没有效果就重新maven编译一下
```java
    @Mappings(value = {
            // source是待转换类中的属性        target是目标类中的属性
            @Mapping(source = "totalPrice", target = "totalPrice", numberFormat = "#.00"),
            @Mapping(source = "publishDate", target = "publishDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
            // 忽略对目标类中的指定属性的映射
            @Mapping(target = "color", ignore = true),            
            // 会自动搜索 convertDriverDTO2DriverVO 方法
            @Mapping(source = "driverDTO", target = "driverVO")    
    })
    CarVO convertCarDTO2CarVO(CarDTO carDTO);

    /**
     * 自定义映射处理
     * 完成上述@Mapping无法完成的一些映射
     */
    @AfterMapping
    default // 让@Mapper方法调用完之后 调用该方法
    void carDto2carVoAfter(CarDTO carDTO, @MappingTarget CarVO carVO) {
        // @MappingTarget 表示传来的CarVO是已经被赋值过的
        List<PartDTO> partDTOS = carDTO.getPartDTOs();
        carVO.setHasPart((partDTOS != null && !partDTOS.isEmpty()) ? true : false);
    }
```
3.4 批量映射
集合中的pojo映射
```java
    /**
     * 批量映射处理
     */
    List<CarVO> convertCarDTOs2CarVOs(List<CarDTO> carDTOS);
    @Test
    public void mapStructBatchMappings2(){
        CarDTO carDTO = buildCarDTO();
        List<CarDTO> carDTOS = new ArrayList<>();
        carDTOS.add(carDTO); // mapstruct 的映射源 SOURCE
        List<CarVO> carVOS = CarConvert.INSTANCE.convertCarDTOs2CarVOs(carDTOS);
        System.out.println(carVOS);
    }
```
3.5使MapStruct默认的映射行为失效
场景：
当xxxVO -> yyyVO时，有许多的属性不希望按照默认映射规则直接映射，此时
需要写多个ignore=true。
如有10个属性，9个属性不希望按照默认映射规则直接映射，则需要写9个ignore=true
为了防止这种情况，可以使用@BeanMapping注解，ignoreByDefault = true使默认的映射规则失效，
而只映射那些配置了@Mappings/@Mapping的属性
```java
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "partId", target = "partId")
    PartVO convertPartDTO2PartVOWithoutDefault(PartDTO partDTO);
```
同一个源类映射到同一个目标类可以对应多条映射规则，方法名不同即可。
3.6避免同样的配置写多份@InheritConfiguration
```java
    /**
     * @InheritConfiguration 继承@Mappings映射配置，当有多个相同的类型转换时，
     * 使用name属性指定特定的方法对应的映射配置 避免同样的配置写多份
     * 场景： 写一个 用 原类的所有属性 更新 目标类的属性 的方法
     */
    @InheritConfiguration(name = "convertPartDTO2PartVOWithoutDefault3")
    void updatePartVO(PartDTO partDTO, @MappingTarget PartVO partVO);
```
3.7逆向继承配置@InheritInverseConfiguration
现有A->B的映射配置
逆向继承配置可实现B->A
```java
    @InheritInverseConfiguration(name = "convertPartDTO2PartVO")
    PartDTO convertPartVO2PartDTO(PartVO partVO);
```
@InheritConfiguration 与 @InheritInverseConfiguration都只继承@Mappings/@Mapping两个注解的配置
而不继承@BeanMapping 
3.8 Spring中使用MapStruct ---- @Mapper(componentModel = "spring")
```java
    @Mapper(componentModel = "spring") // 这里是MapStruct的Mapper 实质上就是给该interface加了@Component注解
    public interface CarConvert {
```
这样在其他地方就可以使用@Autowired注解将CarConvert注入到属性中；
也可以在CarConvert中使用@Autowired注解进行属性的注入。

