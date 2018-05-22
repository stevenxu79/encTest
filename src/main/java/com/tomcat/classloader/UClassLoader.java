/**  
 * All rights Reserved, Designed By www.seassoon.com
 * @Title:  UClassLoader.java   
 * @Package com.tomcat.classloader   
 * @Description:TODO(用一句话描述该文件做什么)   
 * @author: 徐建文
 * @date:   2018年5月21日 上午11:32:52
 * @version V2.0
 * @Copyright: 2018 www.seassoon.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海思贤信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业行为
 */
package com.tomcat.classloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.catalina.loader.WebappClassLoader;
import org.apache.tomcat.Jar;
import org.apache.tomcat.util.bcel.classfile.ClassFormatException;
import org.apache.tomcat.util.bcel.classfile.ClassParser;
import org.apache.catalina.startup.ContextConfig;
import org.apache.tomcat.util.scan.JarFactory;

/**
 * 自定义的classloader
 * 可以解密文件并加载
 */
public class UClassLoader extends WebappClassLoader {

	static String springClass = "SimpleMetadataReader";
	static String springClass2 = "LocalVariableTableParameterNameDiscoverer";
	static String notStr = "$";
	static String projectStr = "com/seassoon/suichao/xny/";
	static String springJar = "spring-core";

	static byte[] decrypt(byte[] _buf) {
		byte[] b = new byte[_buf.length];
		for (int i = 0; i < _buf.length; i++) {
			b[i] = (byte) (_buf[i] ^ 0x07);
		}

		return b;
	}

	public static boolean checkDecrypt(String name) {

		if (name != null) {
			if (name.indexOf(notStr) == -1) {
				if (name.indexOf(projectStr) != -1 || name.indexOf(springClass) != -1 || name.indexOf(springClass2) != -1) {
					System.err.println("encrypt is true!!");
					return true;
				}
			}

		}
		return false;

	}

	/**
	 * 默认构造器
	 */
	public UClassLoader() {
		super();
	}

	/**
	 * 默认构造器
	 * @param parent
	 */
	public UClassLoader(ClassLoader parent) {
		super(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.catalina.loader.WebappClassLoader#findClass(java.lang.String)
	 */
	public Class<?> findClass(String name) throws ClassNotFoundException {

		if (checkDecrypt(name)) {
			return findClassEncrypt(name);
		} else {
			return super.findClass(name);
		}
	}

	/**
	 * 查找class
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	private Class<?> findClassEncrypt(String name) throws ClassNotFoundException {
		byte[] classBytes = null;
		try {
			System.out.println("++++++" + name);
			classBytes = loadClassBytesEncrypt(name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
		if (cl == null)
			throw new ClassNotFoundException(name);
		return cl;
	}

	private byte[] loadClassBytesEncrypt(String name) throws IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] buf;
		if (name.indexOf(springClass) != -1 || name.indexOf(springClass2) != -1) {
			System.out.println("deCrypt name=" + name);
			buf = loadClassBytesEncryptJar(name);
		} else if (name.indexOf(projectStr) != -1) {
			System.out.println("deCrypt2 name=" + name);
			buf = loadClassBytesEncryptClass(name);
		}

		buf = buffer.toByteArray();
		return buf;
	}

	/**
	 * 加载加密后的class字节流
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private byte[] loadClassBytesEncryptClass(String name) throws IOException {
		String basepath = "/home/xujw/tomcat/apache-tomcat-8.5.31/webapps/encSpringTest-0.0.1-SNAPSHOT_encrypt/WEB-INF/classes/";// 项目物理地址
		String cname = basepath + name.replace('.', '/') + ".xujw";
		System.out.println(cname);
		FileInputStream in = new FileInputStream(cname);
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				buffer.write((byte) (ch - 2));
			}
			in.close();
			byte[] src = buffer.toByteArray();
			buffer.flush();
			byte[] ret = decrypt(src);
			return ret;
		} finally {
			in.close();
		}
	}

	private byte[] loadClassBytesEncryptJar(String cname) throws IOException {
		String basepath = "file:/home/xujw/tomcat/apache-tomcat8.5.31/webapps/encSpringTest-0.0.1-SNAPSHOT_encrypt/WEB-INF/lib/spring-core-4.3.13.RELEASE.jar";// 项目物理地址
		File src_file = new File(basepath);
		JarFile src_jar = new JarFile(src_file);
		Enumeration<JarEntry> jarEntrys = src_jar.entries();
		byte[] buf = new byte[1024];
		while (jarEntrys.hasMoreElements()) {
			JarEntry entry = jarEntrys.nextElement();
			// 文件名称
			String name = entry.getName();
			// 文件大小
			long size = entry.getSize();
			// 压缩后的大小
			long compressedSize = entry.getCompressedSize();
			System.out.println(name + "\t" + size + "\t" + compressedSize);

			if (name.endsWith(".xujw") && name.indexOf(cname) != -1) {
				int idx = name.indexOf(".xujw");
				String nameTmp = name.substring(0, idx);
				if (cname.equals(nameTmp)) {
					byte[] bytes_tmp;
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					BufferedInputStream is = new BufferedInputStream(src_jar.getInputStream(entry));
					int len;
					while ((len = is.read(buf, 0, buf.length)) != -1) {
						baos.write(buf, 0, len);
					}
					is.close();
					bytes_tmp = baos.toByteArray();
					System.err.println("read len2=" + bytes_tmp.length);
					bytes_tmp = decrypt(bytes_tmp);
					baos.close();
					return bytes_tmp;
				}

			}

		}
		return null;

	}

}
