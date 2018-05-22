/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  OperatorJAR.java   
 * @Package com.seassoon.suichao.encTest.jarfile   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月11日 下午1:34:56
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.seassoon.suichao.encTest.jarfile;

/**   
 * @ClassName:  OperatorJAR   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 徐建文 
 * @date:2018年5月11日 下午1:34:56  
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为 
 */

import java.util.*;   
import java.util.jar.*;  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
  
public class OperatorJAR { // 操作JAR文件的类  
    public static void readJARList(String fileName) throws IOException {// 显示JAR文件内容列表  
        JarFile jarFile = new JarFile(fileName); // 创建JAR文件对象  
        Enumeration en = jarFile.entries(); // 枚举获得JAR文件内的实体,即相对路径  
        System.out.println("文件名\t文件大小\t压缩后的大小");  
        while (en.hasMoreElements()) { // 遍历显示JAR文件中的内容信息  
            process(en.nextElement()); // 调用方法显示内容  
        }  
    }  
  
    private static void process(Object obj) {// 显示对象信息  
        JarEntry entry = (JarEntry) obj;// 对象转化成Jar对象  
        String name = entry.getName();// 文件名称  
        long size = entry.getSize();// 文件大小  
        long compressedSize = entry.getCompressedSize();// 压缩后的大小  
        System.out.println(name + "\t" + size + "\t" + compressedSize);  
    }  
  
    public static void readJARFile(String jarFileName, String fileName)  
            throws IOException {// 读取JAR文件中的单个文件信息  
        JarFile jarFile = new JarFile(jarFileName);// 根据传入JAR文件创建JAR文件对象  
        JarEntry entry = jarFile.getJarEntry(fileName);// 获得JAR文件中的单个文件的JAR实体  
        InputStream input = jarFile.getInputStream(entry);// 根据实体创建输入流  
        readFile(input);// 调用方法获得文件信息  
        jarFile.close();// 关闭JAR文件对象流  
    }  
  
    public static void readFile(InputStream input) throws IOException {// 读出JAR文件中单个文件信息  
        InputStreamReader in = new InputStreamReader(input);// 创建输入读流  
        BufferedReader reader = new BufferedReader(in);// 创建缓冲读流  
        String line;  
        while ((line = reader.readLine()) != null) {// 循环显示文件内容  
            System.out.println(line);  
        }  
        reader.close();// 关闭缓冲读流  
    }  
  
    public static void main(String args[]) throws IOException {// java程序主入口处  
        OperatorJAR j = new OperatorJAR();  
        System.out.println("1.输入一个JAR文件(包括路径和后缀)");  
//        Scanner scan = new Scanner(System.in);// 键盘输入值  
//        String jarFileName = scan.next();// 获得键盘输入的值  
        String jarFileName = "E:\\g工作\\x项目\\suicao\\加密\\73_128\\JarEncrypt2\\encTest-0.0.1-SNAPSHOT.jar";
        readJARList(jarFileName);// 调用方法显示JAR文件中的文件信息  
        System.out.println("2.查看该JAR文件中的哪个文件信息?");  
//        String fileName = scan.next();// 键盘输入值  
//        readJARFile(jarFileName, fileName);// 获得键盘输入的值  
    }  
}  
