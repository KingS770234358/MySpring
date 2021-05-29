package com.myspring;

import com.myspring.Annotation.Autowired;
import com.myspring.Annotation.Component;
import com.myspring.Annotation.ComponentScan;
import com.myspring.Annotation.Scope;
import com.myspring.Aware.BeanNameAware;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
* 基于注解配置的Spring容器类
* */
public class MyAnnotationConfigApplicationContext {

    private Class configClass; // Spring容器的配置类

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>(); // 实现了BeanPostProcessor的类的列表
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(); // bean定义池
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>(); // 单例池

    /**
     * 构造函数
     * @param configClass 配置类
     */
    public MyAnnotationConfigApplicationContext(Class configClass) throws Exception {

        this.configClass = configClass; // 保存配置类

        // 1.@ComponentScan注解--->获得扫描路径--->扫描--->BeanDefinition--->BeanDefinitionMap
        // 1.1 获得所有实现beanPostProcessors的类，并存储
        // 1.2 将所有bean的beanDefinition放入beanDefinitionMap
        doScan(configClass);

        // 2.遍历BeanDefinitionMap，创建所有的单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if("singleton".equals(beanDefinition.getScope()) && !singletonObjects.containsKey(beanName)){
                Object o = createBean(beanName, beanDefinition); // 单例bean对象
                singletonObjects.put(beanName, o); // 放入单例对象
            }
        }
    }

    /**
     * 扫描配置类配置的路径下的所有Component
     * @param configClass
     * @throws Exception
     */
    private void doScan(Class configClass) throws Exception {
        // 解析配置类@ComponentScan @Bean 针对配置类上的注解以及配置类内部方法上的注解
        // 1.解析@ComponentScan注解--->获得扫描路径--->扫描
        // 1.1 获取@ComponentScan注解
        ComponentScan componentScan = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        if(componentScan == null)
            throw new Exception("请传入合法的Spring容器配置文件");

        // 1.2 通过componentScan注解的方法属性value获得配置的扫描路径
        String scanPath = componentScan.value(); // com.wqiang.service
        scanPath = scanPath.replace(".","/"); // 将 . 转化成 / 才能用于后面ClassLoader getResource
        // System.out.println("scanPath" + scanPath);

        // 1.3 根据得到的扫描路径 扫描指定路径下的带有@Component注解的类？ 类加载器！
        /**
         * BootStrap 启动类加载器  --->  加载类的路径jre/lib下的jar包和类
         * Ext       扩展类加载器  --->  加载类的路径jre/ext/lib下的jar包和类
         * App       应用类加载器  --->  加载类的路径classpath下的jar包和类
         *                             （控制台启动参数中有一个-classpath参数）target/classes/
         */
        ClassLoader classLoader = MyAnnotationConfigApplicationContext.class.getClassLoader(); // 当前容器的类加载器App
        // 通过类加载器去 指定目录/文件 获得资源 相对路径-相对上述的classpath而言
        URL resource = classLoader.getResource(scanPath); // 此处获得的resource是一个目录
        File file = new File(resource.getFile());
        if(file.isDirectory()){ // 当前资源文件是一个目录
            File[] files = file.listFiles();
            for (File f: files) {
                // System.out.println(files[i]);
                String fileName = f.getAbsolutePath();
                if(!fileName.endsWith(".class")) // 只有类文件才进行处理
                    continue;
                // 抽取 类的全限定名
                // xxx\com\wqiang\service\OrderService.class  === >
                // com.wqiang.service.OrderService
                String className = fileName.substring(fileName.indexOf("com"),fileName.indexOf(".class"));
                className = className.replace("\\","."); // "/"转成"."
                // System.out.println(className);
                try {
                    Class<?> clazz = classLoader.loadClass(className); // 通过类加载器加载对应的Class对象 类全限定名
                    if(clazz.isAnnotationPresent(Component.class)){ // 扫描当前扫描到的类是否有Component注解 带有Component注解的才会被识别成一个bean注册到容器中
                        // 当前类是一个bean
                        /**
                         * 工作一、
                         * 如果是BeanPostProcessor接口的实现类就要实例化，存起来（这里的实现不够严谨）
                         * 判断一个Class对象是不是某个接口的实现 BeanPostProcessor.class.isAssignableFrom
                         */
                        if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                            // 源码中是通过 getBean()方法获取BeanPostProcessor的实例（这样的逻辑可以在BeanPostProcessor中进行注入
                            BeanPostProcessor instance = (BeanPostProcessor)clazz.getDeclaredConstructor().newInstance();
                            beanPostProcessors.add(instance);
                        }
                        // System.out.println(beanPostProcessors.size());
                        /*
                         * 工作二、
                         * 创建每个bean的beanDefinition对象，存入BeanDefinitionMap，供之后的对象创建使用
                         * 其他的bean是否扫描到就创建？
                         * 判断bean的作用域：单例 原型
                         * 单例需要用map存储 ConcurrentHashMap <beanName, bean对象> 单例池
                         */
                        // BeanDefinition
                        Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);// beanName
                        String beanName = componentAnnotation.value();
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);
                        if(clazz.isAnnotationPresent(Scope.class)){
                            Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                            beanDefinition.setScope(scopeAnnotation.value());
                        }else{
                            //没有设置Scope 默认单例
                            beanDefinition.setScope("singleton");
                        }
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 根据BeanDefinition的clazz属性创建对象
     */
    public Object createBean(String beanName, BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getClazz();
        try {
            // 无参构造方法 反射构造对象
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // 只遍历属性，依赖注入
            for (Field declaredField : clazz.getDeclaredFields()) {
                if(declaredField.isAnnotationPresent(Autowired.class)){ // 该字段需要依赖注入
                    Autowired autowiredAnnotation = declaredField.getDeclaredAnnotation(Autowired.class);
                    // 简化 根据属性名 getBean()方法 到spring容器中搜索对应的bean
                    Object o = getBean(declaredField.getName());
                    if(o == null && autowiredAnnotation.require()==true)
                        throw new Exception("属性：" + declaredField.getName() + "必须注入");
                    declaredField.setAccessible(true); // !!一定要将对象属性的访问权限设置为true 否则无法访问
                    declaredField.set(instance, o); // 反射给instance对象的字段赋值 依赖注入
                }
            }

            // Aware接口回调
            if(instance instanceof BeanNameAware){ // 实现了BeanNameAware接口
                // 让实现Aware接口的类获取自己在容器中的beanName
                System.out.println("当前实例实现了BeanNameAware接口");
                ((BeanNameAware) instance).setBeanName(beanName); // 实现Aware接口的Bean一般是做一个持有容器的操作
            }

            // 调用 BeanPostProcessor的初始化前方法 对正在实例化的对象instance进行加工
            // 可以实现order接口或是添加@order注释来实现BeanPostProcessor执行顺序的排序
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 初始化 InitializingBean
            if(instance instanceof InitializingBean){ // 实现了InitializingBean接口
                try {
                    ((InitializingBean) instance).afterPropertySet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 调用 BeanPostProcessor的初始化后方法 对正在实例化的对象instance进行加工
            // 可以实现order接口或是添加@order注释来实现BeanPostProcessor执行顺序的排序
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据beanName从Spring容器中获取bean对象的方法
     * @param beanName
     * @return 指定bean对象
     */
    public Object getBean(String beanName) throws Exception {

        if(beanDefinitionMap.containsKey(beanName)){ // 合法的beanName

            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if("singleton".equals(beanDefinition.getScope())){

                // 创建过程中，需要注入的单例对象还未创建放入Ma（修改）
                if(!singletonObjects.containsKey(beanName)){
                    Object o = createBean(beanName, beanDefinition); // 单例bean对象
                    singletonObjects.put(beanName, o); // 放入单例对象
                }
                return singletonObjects.get(beanName);

            }else{
                return createBean(beanName, beanDefinition);
            }

        }else{ // 非法的beanName
            throw new Exception("不存在bean："+ beanName);
        }
    }
}
