/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  Encrypt.java   
 * @Package com.seassoon.suichao.encTest.jarfile   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月11日 上午11:10:36
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.seassoon.suichao.encTest.jarfile;

/**   
 * @ClassName:  Encrypt   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 徐建文 
 * @date:2018年5月11日 上午11:10:36  
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为 
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class Encrypt3 {

	

	// 获取参数
	static Map<String, String> getArgMap(String[] args) {
		Map<String, String> map = new HashMap<>();
		String key = null, val = null;
		for (String tmp : args) {
			if (tmp.startsWith("-")) {
				if (key != null)
					map.put(key, val);
				key = tmp;
				val = null;
			} else {
				val = tmp;
			}
		}
		if (key != null) {
			map.put(key, val);
		}

		return map;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = getArgMap(args);

//		String src_name = map.get("-src");
		String src_name = "E:\\g工作\\x项目\\suicao\\加密\\73_128\\JarEncrypt2\\encTest-0.0.1-SNAPSHOT.jar";
//		String src_name = "E:\\g工作\\x项目\\suicao\\加密\\JarEncrypt\\jars\\xnymanage\\xny-manage-0.0.1-SNAPSHOT.jar";
		if (src_name == null) {
			System.out.println("usage: java Encrypt -src xxx.jar");
			return;
		}

		Encrypt3 coder = new Encrypt3();

//		String dst_name = map.get("-dst");
		String dst_name = "E:\\g工作\\x项目\\suicao\\加密\\73_128\\JarEncrypt2\\encTest-0.0.1-SNAPSHOT_enc2.jar";
//		String dst_name = "E:\\g工作\\x项目\\suicao\\加密\\JarEncrypt\\jars\\xnymanage\\xny-manage-0.0.1-SNAPSHOT_enc2.jar";
		
		if (dst_name == null || dst_name.equals(src_name))
			dst_name = src_name.substring(0, src_name.length() - 4) + "_encrypt.jar";

		System.out.printf("encode jar file: [%s ==> %s ]\n", src_name, dst_name);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		File dst_file = new File(dst_name);
		File src_file = new File(src_name);

		FileOutputStream dst_fos = new FileOutputStream(dst_file);
		JarOutputStream dst_jar = new JarOutputStream(dst_fos);

		JarFile src_jar = new JarFile(src_file);
		for (Enumeration<JarEntry> enumeration = src_jar.entries(); enumeration.hasMoreElements();) {
			JarEntry entry = enumeration.nextElement();

			InputStream is = src_jar.getInputStream(entry);
			int len;
			while ((len = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, len);
			}
			byte[] bytes = baos.toByteArray();

			String name = entry.getName();
			System.err.println("name="+name+",size="+entry.getSize());
//			if(name.endsWith(".class") && name.startsWith("com/seassoon/suichao/encTest/")){
			if(name.endsWith(".class") && name.startsWith("BOOT-INF/classes/com/seassoon/suichao/")){
				System.out.println("encrypt " + name.replaceAll("/", "."));
				try {
//					bytes = coder.encrypt(bytes);
				} catch (Exception e) {
					System.out.println("encrypt error happend~");
					e.printStackTrace();
				}
			}

			JarEntry ne = new JarEntry(name);
			dst_jar.putNextEntry(ne);
			dst_jar.write(bytes);
			baos.reset();
		}
		src_jar.close();

		dst_jar.close();
		dst_fos.close();
	}

}
