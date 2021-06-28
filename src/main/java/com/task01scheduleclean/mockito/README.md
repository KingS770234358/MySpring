#### 学习使用Junit(SpringBootTest) Mockito Jacoco进行单元测试

一、单元测试
1.1 单元测试简介
定义：单元测试（unit testing）是用来对 一个模块、一个函数 或者 一个类 来进行正确性检验的测试工作，
     在软件开发过程中要进行的最低级别的测试活动，软件的独立单元将在【与程序的其他部分相隔离的情况下】进行测试。
优点: 从长期来看，可以提高代码质量，减少维护成本，减少调试时间，降低重构难度。
缺点: 从短期来看，加大了工作量，对于进度紧张的项目中的开发人员来说，可能会成为不少的负担。

1.2 编写单元测试的实际
1.2.1 在具体实现代码之前。这是测试驱动开发（TDD）所提倡的；
1.2.2 与具体实现代码同步进行。先写少量功能代码，紧接着写单元测试（重复这两个过程，直到完成功能代码开发）。
      其实这种方案跟第一种已经很接近，基本上功能代码开发完，单元测试也差不多完成了。
1.2.3 编写完功能代码再写单元测试。事后编写的单元测试“粒度”都比较粗。
建议：推荐【单元测试与具体实现代码同步进行】这个方案的。只有对需求有一定的理解后才能知道什么是代码的正确性，
     才能写出有效的单元测试来验证正确性，而能写出一些功能代码则说明对需求有一定理解了。
     
1.3 单元测试代码覆盖率
在做单元测试时，代码覆盖率常常被拿来作为【衡量测试好坏】的指标，比如，代码覆盖率必须达到80％或90％。
1.4 覆盖方式
1.4.1 语句覆盖（行覆盖/段覆盖/基本块覆盖）
    度量被测代码中每个可执行语句是否被执行到了。它只管覆盖代码中的执行语句，却不考虑各种分支的组合等等。
    但是，换来的测试效果不明显，很难更多地发现代码中的问题。
```java
public class Test{
    int divide(int a, int b){
       return  a / b;
    }
}
```
TeseCase: a = 10, b = 5 。测试结果会告诉你，代码覆盖率达到了100％，并且所有测试案例都通过了。
然而遗憾的是，我们的语句覆盖率达到了所谓的100%，但是却没有发现最简单的Bug，比如，当我让b=0时，会抛出一个除零异常。
1.4.2 判定覆盖（分支覆盖/所有边界覆盖/基本路径覆盖）
    度量程序中每一个判定的分支是否都被测试到了.
1.4.3 条件覆盖
    它度量判定中的【每个子表达式结果true和false】是否被测试到了。
判定覆盖和条件覆盖的区别举例:
```java
public class Test{
    int foo(int a, int b){
        if (a < 10 || b < 10){ // 判定
            return 0; // 分支一
        }else{
            return 1; // 分支二
        }
    }
}
```
判定覆盖：考虑判定结果为true和false两种情况
```
TestCaes1: a = 5, b ＝ 任意数字      //覆盖了分支一
TestCaes2: a = 15, b = 15          //覆盖了分支二
```
条件覆盖：考虑判定中的每个条件表达式结果
```
TestCase1: a = 5, b = 5         true,  true //覆盖了分支一
TestCase2: a = 15, b = 15       false, false //覆盖了分支二
```
需要特别注意的是：条件覆盖【不是将判定中的每个条件表达式的结果进行排列组合】，而是只要
【每个条件表达式的结果true和false测试到了就OK了】。
因此，可以这样推论：【完全的条件覆盖并不能保证完全的判定覆盖。】
```
TestCase1: a = 5, b = 15        true,  false   //覆盖了分支一
TestCase1: a = 15, b = 5        false, true    //覆盖了分支一
```
表达式1的true和false都测试到了；表达式2的true和false也都测试到了（不是排列组合！）
因此是完整的条件覆盖，但是不是完整的判定覆盖。
1.4.4 路径覆盖（断言覆盖）
度量了是否函数的每一个分支都被执行了。就是所有可能的分支都执行一遍，有多个分支嵌套时，需要对多个分支进行排列组合。
测试路径随着分支的数量指数级别增加。
1.4.5 四种单元测试覆盖的比较
```
int foo(int a, int b){
    int nReturn = 0;
    if (a < 10){    // 分支一
        nReturn += 1;
    }
    if (b < 10){    // 分支二
        nReturn += 10;
    }
    return nReturn;
}
```
a. 语句覆盖（每句都测试到）
```
TestCase a = 5, b = 5      nReturn = 11
```
b.判定覆盖
```
TestCase1 a = 5,  b = 5    nReturn = 11
TestCase2 a = 15, b = 15   nReturn = 0 
```
c.条件覆盖
```
TestCase1 a = 5,  b = 5    nReturn = 11   测试了表达式1的true  表达式2的true
TestCase2 a = 15, b = 15   nReturn = 0    测试了表达式1的false 表达式2的false
```
​上面三种覆盖率结果看起来都都达到了100％！但nReturn的结果一共有四种可能的返回值：0，1，10，11，
而上面的针对每种覆盖率设计的测试案例只覆盖了部分返回值，并没有测试完全。
d.路径覆盖 将所有可能的返回值都测试到了（排列组合）
```
TestCase1 a = 5,   b = 5     nReturn = 0
TestCase2 a = 15,  b = 5     nReturn = 1
TestCase3 a = 5,   b = 15    nReturn = 10
TestCase4 a = 15,  b = 15    nReturn = 11
```

二、Jacoco
2.1 idea中支持三种插件来查看代码覆盖率：Coverage、JaCoCo、Emma
Emma            通过对编译后的 Java 字节码文件进行插桩，在测试执行过程中收集覆盖率信息，
                并通过支持多种报表格式对覆盖率结果进行展示。
Cobertura       配置内容很丰富
Jacoco          Emma 团队开发，可以认为是Emma的升级版
Clover(商用)     最早的JAVA测试代码覆盖率工具之一，收费

2.2 Jacoco优势
①Jacoco是一个开源的覆盖率工具，社区比较活跃，官网也在不断的维护更新。
②它针对的开发语言是java，可嵌入到Ant、Maven中，或者作为Idea和Eclipse的插件使用。
③很多第三方的工具提供了对Jacoco的集成，如sonar(代码质量管理的开源平台)、Jenkins等。
④可以使用JavaAgent (实现字节码层面的代码修改) 技术监控Java程序。

2.3 Jacoco基本概念
①行覆盖率       度量被测程序的每行代码是否被执行，判断标准行中是否至少有一个指令被执行。
②类覆盖率       度量计算class类文件是否被执行。
③分支覆盖率     度量if和switch语句的分支覆盖情况，计算一个方法里面的总分支数，确定执行和不执行的 分支数量。
④方法覆盖率     度量被测程序的方法执行情况，是否执行取决于方法中是否有至少一个指令被执行。
⑤指令覆盖       计数单元是单个java二进制代码指令，指令覆盖率提供了代码是否被执行的信息，度量完全独立源码格式。
⑥圈复杂度       代码复杂度的衡量标准,在（线性）组合中，计算在一个方法里面所有可能路径的最小数目，缺失的复杂度同样
               表示测试案例没有完全覆盖到这个模块。 简单来说就是覆盖所有的可能情况最少使用的测试用例数。

2.4 Jacoco原理
(1) JaCoCo在Byte Code时使用的ASM技术修改字节码方法，可以修改Jar文件、class文件字节码文件。
(2) JaCoCo通过注入来修改和生成java字节码，使用的是ASM库。
(3) JaCoCo同时支持on-the-fly(及时)和offline(离线)的两种插桩模式。
On-the-fly插桩：
    JVM通过-javaagent参数指定特定的jar文件启动Instrumentation代理程序，代理程序在装载class文件前判断
    是否已经转换修改了该文件，若没有则将探针（统计代码）插入class文件，最后在JVM执行测试代码的过程中完成对
    覆盖率的分析。
Offline模式：
  ​  先对字节码文件进行插桩，然后执行插桩后的字节码文件，生成覆盖信息并导出报告。
On-the-fly和offline比较：
    On-the-fly无需提前进行字节码插桩，无需停机(offline需要停机)，可以实时获取覆盖率。
存在如下情况不适合on-the-fly，需要采用offline提前对字节码插桩：
​(1) 运行环境不支持java agent。
​(2) 部署环境不允许设置JVM参数。
(3) 字节码需要被转换成其他的虚拟机如Android Dalvik VM。
(4) 动态修改字节码过程中和其他agent冲突。
​(5) 无法自定义用户加载类。

2.5 Jacoco使用
2.5.1 Maven配置
· pom.xml文件中添加依赖
```xml
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.2</version>
</dependency>
```
· 配置plugins插件信息
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.2</version>
    <configuration>
        <destFile>target/coverage-reports/jacoco-unit.exec</destFile>
        <dataFile>target/coverage-reports/jacoco-unit.exec</dataFile>
        <includes>
            <include>**/service/**</include>
            <include>**/controller/**</include>
            <!--<include>**/service/impl/*.class</include>-->
        </includes>
        <!-- rules里面指定覆盖规则 -->
        <rules>
            <rule implementation="org.jacoco.maven.RuleConfiguration">
                <element>BUNDLE</element>
                <limits>　　
                    <!-- 指定方法覆盖到50% COVEREDRATIO 统计的是覆盖率 -->
                    <limit implementation="org.jacoco.report.check.Limit">
                        <counter>METHOD</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.50</minimum>
                    </limit>
                    <!-- 指定分支覆盖到50% -->
                    <limit implementation="org.jacoco.report.check.Limit">
                        <counter>BRANCH</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.50</minimum>
                    </limit>
                    <!-- 指定类覆盖到100%，不能遗失任何类 MISSEDCOUNT 统计的是丢失率 -->
                    <limit implementation="org.jacoco.report.check.Limit">
                    <counter>CLASS</counter>
                    <value>MISSEDCOUNT</value>
                    <maximum>0</maximum>
                    </limit>
                </limits>
            </rule>
        </rules>
    </configuration>
    <executions>
        <execution>
            <id>jacoco-initialize</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <!--这个check:对代码进行检测，控制项目构建成功还是失败-->
        <execution>
            <id>check</id>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
        <!--这个report:对代码进行检测，然后生成index.html在 target/site/index.html中可以查看检测的详细结果-->
        <execution>
            <id>jacoco-site</id>
            <phase>package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
上面配置主要做了一下的构建过程：
(1) 项目已jar包方式打包，引入junit和jacoco。
(2) Build时执行instrument、report、check。
(3) 覆盖率生成到target/jacoco.exec
· 当JaCoCo插件配置好以后，要获得 JaCoCo的统计数据，就要执行mvn install 命令。执行完以后，
target/site/jacoco/目录下会生成一个index.html文件，这是统计数据总览页面，可以在浏览器打开查看。
注：如果没生成site目录，则需要手动通过Jacoco插件点击jacoco：report生成index.html
2.5.2 Jacoco报告分析
1.报告文档分析
Jacoco 包含了多种尺度的覆盖率计数器,包含：
①指令级（Instructions,C0 coverage）
    Jacoco 计算的最小单位就是字节码指令。指令覆盖率表明了在所有的指令中，哪些被执行过以及哪些没有被执行。
    这项指数完全独立于源码格式并且在任何情况下有效，不需要类文件的调试信息。
②行（Lines）
    该项指数在有调试信息的情况下计算。
③分支（Branches,C1 coverage）
    Jacoco 对所有的 if 和 switch 指令计算了分支覆盖率。这项指标会统计所有的分支数量，并同时支出哪些分支被执行，
    哪些分支没有被执行。这项指标也在任何情况都有效。异常处理不考虑在分支范围内。
方法（Non-abstract Methods）
    每一个非抽象方法都至少有一条指令。若一个方法至少被执行了一条指令，就认为它被执行过。因为 Jacoco 直接对字节码进行操作，
    所以有些方法没有在源码显示（比如某些构造方法和由编译器自动生成的方法）也会被计入在内。
圈复杂度（Cyclomatic Complexity）
    Jacoco 为每个非抽象方法计算圈复杂度，并也会计算每个类、包、组的复杂度。根据 McCabe 1996 的定义，
    圈复杂度可以理解为覆盖所有的可能情况最少使用的测试用例数。这项参数也在任何情况下有效。
类（Classes）
    每个类中只要有一个方法被执行，这个类就被认定为被执行。同 Methods 一样，有些没有在源码声明的方法被执行，也认定该类被执行。
2.详细文档分析
绿色的为分支覆盖充分，标黄色的为部分分支覆盖，标红色的为未执行该分支。
在有调试信息的情况下，分支点可以被映射到源码中的每一行，并且被高亮表示。
· 红色钻石：无覆盖，没有分支被执行。
· 黄色钻石：部分覆盖，部分分支被执行。
· 绿色钻石：全覆盖，所有分支被执行。
通过这个报告的结果就可以知道代码真实的执行情况，便于我们分析评估结果，并且可以提高代码的测试覆盖率。

三、Mockito
3.1 技术背景/简介
   在实际项目中写单元测试的过程中我们会发现需要测试的类有很多依赖，这些依赖项又会有依赖，导致在单元测试代码里几乎无法完成构建，尤其是
当依赖项尚未构建完成时会导致单元测试无法进行。Mockito是mocking框架，它让你用简洁的API做测试。简单来说，所谓的mock就是创建一个类的虚假的对象，
在测试环境中，用来替换掉真实的对象。Mockito 的优点是通过在执行后校验哪些函数已经被调用，消除了对期望行为（expectations）的需要。
其它的 mocking 库需要在执行前记录期望行为（expectations），而这导致了丑陋的初始化代码。
△Mockito区别于其他模拟框架的地方主要是允许开发者在没有【建立“预期”】时验证被测系统的行为。
3.2 Mockito的使用
3.2.1 添加maven依赖
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-all</artifactId>
    <version>1.9.5</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.11</version>
    <scope>test</scope>
</dependency>
```
3.2.2 import到代码中
```java
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
```
3.2.3 常用Mockito模拟方法
Mockito.mock(classToMock)                                           模拟对象
Mockito.verify(mock)                                                验证行为是否发生
Mockito.when(methodCall).thenReturn(value1).thenReturn(value2)	    发时第一次返回value1，第n次都返回value2
Mockito.doThrow(toBeThrown).when(mock).[method]	                    模拟抛出异常
Mockito.mock(classToMock,defaultAnswer)	                            使用默认Answer模拟对象
Mockito.when(methodCall).thenReturn(value)	                        参数匹配，执行方法得到返回值value
Mockito.doReturn(toBeReturned).when(mock).[method]	                参数匹配（直接执行不判断）
Mockito.when(methodCall).thenAnswer(answer))	                    预期回调接口生成期望值
Mockito.doAnswer(answer).when(methodCall).[method]	                预期回调接口生成期望值（直接执行不判断）
Mockito.spy(Object)	                                                用spy监控真实对象,设置真实对象行为
Mockito.doNothing().when(mock).[method]	                            不做任何返回
Mockito.doCallRealMethod().when(mock).[method]                      等价于Mockito.when(mock.[method]).thenCallRealMethod();
                                                                    调用真实的方法
reset(mock)	                                                        重置mock



