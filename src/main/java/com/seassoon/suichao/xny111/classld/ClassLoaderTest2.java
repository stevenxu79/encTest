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

public class ClassLoaderTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 创建自定义classloader对象。
		DiskClassLoader diskLoader = new DiskClassLoader("E:\\lib");
		try {
			// 加载class文件
			Class c = diskLoader.loadClass("com.seassoon.suichao.xny.classld.Test");

			if (c != null) {
				try {
					Object obj = c.newInstance();
					Method method = c.getDeclaredMethod("say", null);
					// 通过反射调用Test类的say方法
					method.invoke(obj, null);
					System.out.println("Thread " + Thread.currentThread().getName() + " classloader: " + Thread.currentThread().getContextClassLoader().toString());

					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							System.out.println("Thread " + Thread.currentThread().getName() + " classloader2: " + Thread.currentThread().getContextClassLoader().toString());
							DiskClassLoader2 diskLoader2 = new DiskClassLoader2("E:\\lib\\test");
							try {
								Class c2 = diskLoader2.loadClass("com.seassoon.suichao.xny.classld.Test");
								
									Object obj2 = c2.newInstance();
									Method method2 = c2.getDeclaredMethod("say", null);
									// 通过反射调用Test类的say方法
									method2.invoke(obj2, null);
								
							} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}).start();;
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}