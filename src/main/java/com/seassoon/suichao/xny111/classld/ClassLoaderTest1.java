/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  ClassLoaderTest.java   
 * @Package com.seassoon.suichao.xny.classld   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月17日 下午3:15:39
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.seassoon.suichao.xny111.classld;

/**   
 * @ClassName:  ClassLoaderTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 徐建文 
 * @date:2018年5月17日 下午3:15:39  
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为 
 */

public class ClassLoaderTest1 {
	public static void main(String[] args) {
		ClassLoader cl = Test2.class.getClassLoader();

//        System.out.println("ClassLoader is:"+cl.toString());        
//        cl = int.class.getClassLoader();
//        System.out.println("ClassLoader is:"+cl.toString());
        System.out.println("ClassLoader is:"+cl.toString());
        System.out.println("ClassLoader\'s parent is:"+cl.getParent().toString());
	}
}
