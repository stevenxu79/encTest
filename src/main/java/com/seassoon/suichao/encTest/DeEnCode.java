/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  DeEnCode.java   
 * @Package com.seassoon.suichao.encTest   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月23日 下午4:18:46
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.seassoon.suichao.encTest;

/**   
 * @ClassName:  DeEnCode   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 徐建文 
 * @date:2018年5月23日 下午4:18:46  
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为 
 */

import java.nio.charset.Charset;  
 
 
public class DeEnCode {  
 
   private static final String key0 = "FECOI()*&<MNCXZPKL";  
   private static final Charset charset = Charset.forName("UTF-8");  
//   private static byte[] keyBytes = key0.getBytes(charset);  
   private static char[] key=key0.toCharArray();
   public static String encode(String enc) {
		
		byte[] b = enc.getBytes(charset);
		int len=key.length;
		for (int i = 0, size = b.length; i < size; i++) {
//			for (byte keyBytes0 : keyBytes) {
			for(int j=0;j<len;j++) {
				b[i] = (byte) (b[i] ^ key[j]);
			}
			
		}
		return new String(b);
	}

	public static String decode(String dec) {
		byte[] e = dec.getBytes(charset);
//		byte[] dee = e;
		int len=key.length;
		for (int i = 0, size = e.length; i < size; i++) {
			for(int j=0;j<len;j++) {
				e[i] = (byte) (e[i] ^ key[j]);
			}
			
//			for(int j=len;j>0;j--) {
//				e[i] = (byte) (e[i] ^ key[j-1]);
//			}
//			for (byte keyBytes0 : keyBytes) {
//				e[i] = (byte) (dee[i] ^ keyBytes0);
//			}
		}
		return new String(e);
	}
 
   public static void main(String[] args) {  
       String s="you are right";  
       String enc = encode(s);  
       String dec = decode(enc);  
       System.out.println("ENC:"+enc);  
       System.out.println("DEC:"+dec);  
   }  
}  
