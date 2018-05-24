/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  EncDec.java   
 * @Package com.jni   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月22日 下午3:06:36
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.jni;

/**   
 * @ClassName:  EncDec   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 徐建文 
 * @date:2018年5月22日 下午3:06:36  
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为 
 */

public class EncDec {
	native byte[] encrypt(byte[] _buf);
	native byte[] decrypt(byte[] _buf);
	static {
		System.loadLibrary("encrypt");
	}
	public static void main(String[] args) {
		
	}
}
