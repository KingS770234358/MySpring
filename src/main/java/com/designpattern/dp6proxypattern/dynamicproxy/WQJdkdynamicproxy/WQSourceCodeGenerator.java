//package com.designpattern.dp6proxypattern.dynamicproxy.WQJdkdynamicproxy;
//
//import javax.tools.JavaCompiler;
//import javax.tools.StandardJavaFileManager;
//import javax.tools.ToolProvider;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.util.Iterator;
//
//public class WQSourceCodeGenerator {
//
//    public static final String ln = "\r\n"; // 换行符
//
//    // 生成代理对象
//    public static Object newProxyInstance(WQClassLoader classLoader, Class<?>[] interfaces, WQInvocationHandler h){
//
//        // 1.动态生成源代码.java文件
//        String src = generateSrc(interfaces); // 生成接口的源代码文件
//        // .java文件输出磁盘
//        String filePath = WQSourceCodeGenerator.class.getResource("").getPath();// classPath
//        // $开头的.class文件，一般都是自动生成的
//        // $Proxy0继承了Proxy类，同时还实现了Person接口，而且重写了findLove（）等方法。
//        File f = new File(filePath + "$Proxy0.java");
//        try {
//            FileWriter fw = new FileWriter(f);
//            fw.write(src);
//            fw.flush();
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 2.将生成的.java文件编译成.class文件
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        StandardJavaFileManager manager =
//                compiler.getStandardFileManager(null, null,null);
//        Iterable iterable = manager.getJavaFileObjects(f);
//        JavaCompiler.CompilationTask task =
//                compiler.getTask(null, manager, null, null, null, iterable);
//        task.call();
//        try {
//            manager.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 3.把生成的.class文件装载到JVM中
//        Class proxyClass = classLoader.findClass("$Proxy0");
//        try {
//            Constructor c = proxyClass.getConstructor(WQInvocationHandler.class);
//            f.delete();
//        // 4.返回字节码重组后的代理对象
//        c.newInstance(h);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String generateSrc(Class<?>[] interfaces){
//        StringBuffer sb = new StringBuffer();
//        sb.append()
//    }
//}
//
