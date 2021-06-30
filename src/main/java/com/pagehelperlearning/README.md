1. 首先引入pom.xml文件配置
<!-- mybatis -->
<dependency>
 <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>
<!--mybatis分页插件-->
<dependency>
     <groupId>com.github.pagehelper</groupId>
     <artifactId>pagehelper-spring-boot-starter</artifactId>
     <version>1.1.0</version>
</dependency>

2. 在application.properties/yaml/yml中进行配置
#mybatis
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.CarManage.dao
#pagehelper
pagehelper:
  helperDialect: mysql
  reasonable: true
  support-methods-arguments: true
  params-count: countSql