## 自己编写一个Spring小样
1.目录结构

2.spring框架注解 - ComponentScan、Component、Autowired、Scope

3.接口 
  回调接口Aware - BeanNameAware
  生命周期接口 - BeanPostProcessor、InitializingBean、InstantiationAwareBeanPostProcessor

4.类 
  bean定义类 - BeanDefinition
  容器类 - MyAnnotationConfigApplicationContext

5.应用层
  业务接口 - UserService 
  业务类 - UserServiceImpl、OrderService、UnscannableClass
  spring相关类 - ApplicationContextConfig、WqiangBeanPostProcessor(Aop-Jdk动态代理)

6.主程序入口
  Test类

## 学习设计模式

## Idea使用Git
1.首先在settings中设置Git所在的目录
2.setting中设置GitHub的账号密码
3.菜单栏->VCS->import into version control->Create Git Repository 让项目成为一个Git项目（在项目根目录创建.git文件夹）
4.选中非白色的文件（红色 蓝色）右键/VCS_Git->add/commit file（左上角相对的两个蓝色箭头可以对比文件的不同之处）
  ->点击Commit
5.配置push的目标brunch：
  找到IDEA界面右下角的Git Branch选项，设置local brunch为远程的目的brunch（左键单击已存在的brunch名，rename即可，GitHub上默认
  分支为main）
6.找到IDEA界面右下角的Git Branch选项，右键local brunch下的目标brunch->push->
  弹窗中点击define remote为远程起名，并且配置远程的URL，这个URL就是GitHub上面项目的URL
  比如 https://github.com/KingS770234358/MySpring
  OK -> push/force push即可（当push rejected的时候 force push->force push anyway）

7.修改文件内容后，右键->git->commit file就可以把修改同步到GitHub上
  

删除git配置，setting->version control 删除配置，同时删除本地文件（上面生成的.git文件夹）