/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  ClassLoaderTest.java   
 * @Package com.seassoon.suichao.xny.classld   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月17日 下午5:28:40
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.seassoon.suichao.xny111.classld;

/**   
 * @ClassName:  ClassLoaderTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 徐建文 
 * @date:2018年5月17日 下午5:28:40  
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为 
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

	public static void main(String[] args) {
		DiskClassLoader1 diskLoader1 = new DiskClassLoader1("E:\\lib\\test");
		Class cls1 = null;
		try {
		//加载class文件
		 cls1 = diskLoader1.loadClass("com.seassoon.suichao.xny.classld.Test");
		System.out.println(cls1.getClassLoader().toString());
		if(cls1 != null){
		    try {
		        Object obj = cls1.newInstance();
		        //SpeakTest1 speak = (SpeakTest1) obj;
		        //speak.speak();
		        Method method = cls1.getDeclaredMethod("say",null);
		        //通过反射调用Test类的speak方法
		        method.invoke(obj, null);
		    } catch (InstantiationException | IllegalAccessException 
		            | NoSuchMethodException
		            | SecurityException | 
		            IllegalArgumentException | 
		            InvocationTargetException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		DiskClassLoader diskLoader = new DiskClassLoader("E:\\lib");
		System.out.println("Thread "+Thread.currentThread().getName()+" classloader: "+Thread.currentThread().getContextClassLoader().toString());
		new Thread(new Runnable() {

		    @Override
		    public void run() {
		        System.out.println("Thread "+Thread.currentThread().getName()+" classloader: "+Thread.currentThread().getContextClassLoader().toString());

		        // TODO Auto-generated method stub
		        try {
		            //加载class文件
		        //  Thread.currentThread().setContextClassLoader(diskLoader);
		            //Class c = diskLoader.loadClass("com.frank.test.SpeakTest");
		            ClassLoader cl = Thread.currentThread().getContextClassLoader();
//		            Class c = cl.loadClass("com.seassoon.suichao.xny.classld.Test");
		            Class c = diskLoader.loadClass("com.seassoon.suichao.xny.classld.Test");
		            System.out.println("Thread "+Thread.currentThread().getName()+" classloader: "+Thread.currentThread().getContextClassLoader().toString());
		            // Class c = Class.forName("com.frank.test.SpeakTest");
		            System.out.println(c.getClassLoader().toString());
		            if(c != null){
		                try {
		                    Object obj = c.newInstance();
		                    //SpeakTest1 speak = (SpeakTest1) obj;
		                    //speak.speak();
		                    Method method = c.getDeclaredMethod("say",null);
		                    //通过反射调用Test类的say方法
		                    method.invoke(obj, null);
		                } catch (InstantiationException | IllegalAccessException 
		                        | NoSuchMethodException
		                        | SecurityException | 
		                        IllegalArgumentException | 
		                        InvocationTargetException e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
		            }
		        } catch (ClassNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    }
		}).start();
	}

}