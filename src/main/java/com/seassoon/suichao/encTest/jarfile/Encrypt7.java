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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;

public class Encrypt7 {

	static byte[] encrypt(byte[] _buf) {
		byte[] b = new byte[_buf.length];
		for (int i = 0; i < _buf.length; i++) {
			b[i] = (byte) (_buf[i] ^ 0x07);
		}

		return b;
	}

	/** 
	* 读取流 
	*  
	* @param inStream 
	* @return 字节数组 
	 * @throws IOException 
	* @throws Exception 
	*/
	public static byte[] readStream(InputStream inStream) throws IOException  {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

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

	/**
	 * 判断是否需要加密
	 * @Title: isEncrypt   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param name
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isEncrypt(String name) {
		String projectStr = "com/seassoon/suichao/xny/";
		String springStr = "SimpleMetadataReader";
		String springJar = "spring-core";
		if (name != null) {
			if (name.endsWith(".class")) {
				if (name.indexOf(projectStr) != -1 || name.indexOf(springStr) != -1) {
					System.err.println("encrypt is true!!");
					return true;
				}
			}
			if (name.endsWith(".jar")) {
				if (name.indexOf(springJar) != -1) {
					return true;
				}
			}

		}
		return false;

	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	public static void inputStreamToFile(InputStream ins, File file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}

	public static void encJar(String src_name, String dst_name) throws IOException {
		if (src_name == null) {
			System.out.println("usage: java Encrypt -src xxx.jar");
			return;
		}
		if (dst_name == null || dst_name.equals(src_name))
			dst_name = src_name.substring(0, src_name.length() - 4) + "_encrypt.jar";

		System.out.printf("encode jar file: [%s ==> %s ]\n", src_name, dst_name);

		File src_file = new File(src_name);
		File dst_file = new File(dst_name);

		JarFile src_jar = new JarFile(src_file);
		Enumeration<JarEntry> jarEntrys = src_jar.entries();

		JarOutputStream dst_jar = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(dst_file)));

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
			if (isEncrypt(name)) {
				System.out.println("encrypt " + name);
				if (name.endsWith(".class")) {

					try {
						// InputStream is = src_jar.getInputStream(entry);
						BufferedInputStream is = new BufferedInputStream(src_jar.getInputStream(entry));
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int len;
						while ((len = is.read(buf, 0, buf.length)) != -1) {
							baos.write(buf, 0, len);
						}
						byte[] bytes_tmp = baos.toByteArray();
						System.err.println("bytelen1=" + bytes_tmp.length);
						byte[] bytes = encrypt(bytes_tmp);// 加密CLASS
						System.err.println("bytelen2=" + bytes.length);
						JarEntry ne = new JarEntry(name);
						dst_jar.putNextEntry(ne);
						dst_jar.write(bytes);

						// baos.reset();
						baos.close();
						is.close();
						dst_jar.closeEntry();

					} catch (Exception e) {
						System.out.println("encrypt error happend~");
						e.printStackTrace();
					}

				} else if (name.endsWith(".jar")) {
					// 获得实体流
					InputStream is = src_jar.getInputStream(entry);

					// 创建本地临时目录和文件
					long dt = new Date().getTime();
					String tmpDir = "tmp/" + dt;
					createDir(tmpDir);

					String tmpFile = name.substring(name.lastIndexOf("/") + 1);
					String tmp_src_name = tmpDir + "/" + tmpFile;
					System.err.println("tmp_src_name=" + tmp_src_name);
					File tmpJarFile_src = new File(tmp_src_name);
					// 将实体流写入本地临时文件
					inputStreamToFile(is, tmpJarFile_src);
					is.close();
					
					//递归调用jar包加密方法,获得嵌套的加密报
					String tmp_dst_name = tmp_src_name.substring(0, tmp_src_name.length() - 4) + "_encrypt.jar";
					encJar(tmp_src_name, tmp_dst_name);
					// 读取jar包数据写入大JAR文件
					File small_jar = new File(tmp_dst_name);
					InputStream input = new FileInputStream(small_jar);
					byte[] byt = readStream(input);

					JarEntry ne = new JarEntry(name);

					/** ZipEntry.STORED */
					ne.setMethod(ZipEntry.STORED);
					ne.setCompressedSize(small_jar.length());
					ne.setSize(small_jar.length());
					CRC32 crc = new CRC32();
					crc.update(getFileBytes(small_jar));
					ne.setCrc(crc.getValue());
					/** ZipEntry.STORED */

					dst_jar.putNextEntry(ne);
				

					dst_jar.write(byt);
					input.close();
					dst_jar.closeEntry();
				}

			} else {
				System.out.println("no encrypt " + name);
				// System.out.println("no encrypt " + name.replaceAll("/", "."));
				dst_jar.putNextEntry(entry);
				BufferedInputStream is = new BufferedInputStream(src_jar.getInputStream(entry));
				int len;
				while ((len = is.read(buf, 0, buf.length)) != -1) {
					// baos.write(buf, 0, len);
					dst_jar.write(buf, 0, len);
				}

				is.close();
				dst_jar.closeEntry();
			}

		}
		
		dst_jar.finish();
		dst_jar.close();
		src_jar.close();

	}

	
	
	/**
	 * 
	 * 方法名：getFileBytes<br>
	 * 描述：获取文件的bytes<br>
	 * 创建时间：2016-12-14 下午2:23:33<br>
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException<br>
	 */
	public static byte[] getFileBytes(File file) throws FileNotFoundException, IOException {
		byte[] buffer;
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
		byte[] b = new byte[1000];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		fis.close();
		bos.close();
		buffer = bos.toByteArray();
		return buffer;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String, String> map = getArgMap(args);

		// String src_name = map.get("-src");
		// String src_name = "E:\\g工作\\x项目\\suicao\\加密\\73_128\\JarEncrypt2\\encTest-0.0.1-SNAPSHOT.jar";
		// String src_name = "E:\\g工作\\x项目\\suicao\\加密\\JarEncrypt\\jars\\xnymanage\\xny-manage-0.0.1-SNAPSHOT.jar";
		String src_name = "E:\\STS_WORKSPACE\\SUICHAO\\suichao-ms-3\\encSpringTest\\target\\encSpringTest-0.0.1-SNAPSHOT.jar";
		if (src_name == null) {
			System.out.println("usage: java Encrypt -src xxx.jar");
			return;
		}
		encJar(src_name, null);
	}
}
