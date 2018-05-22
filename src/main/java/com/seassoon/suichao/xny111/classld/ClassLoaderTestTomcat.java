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

public class ClassLoaderTestTomcat {

	public static void main(String[] args) {
		String mLibPath="E:\\lib\\xny\\encSpringTest-0.0.1-SNAPSHOT";
		UClassLoader diskLoader1 = new UClassLoader(mLibPath);
		Class cls1 = null;
		try {
		//加载class文件
		 cls1 = diskLoader1.loadClass("com.seassoon.suichao.xny.Test1");
		System.out.println(cls1.getClassLoader().toString());
		if(cls1 != null){
		    try {
		        Object obj = cls1.newInstance();
		        //SpeakTest1 speak = (SpeakTest1) obj;
		        //speak.speak();
		        Method method = cls1.getDeclaredMethod("getStr",null);
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

		
	}

}