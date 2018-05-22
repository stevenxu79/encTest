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
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;

public class Encrypt5 {

	static byte[] encrypt(byte[] _buf) {
		byte[] b = new byte[_buf.length];
		for (int i = 0; i < _buf.length; i++) {
			b[i] = (byte) (_buf[i] ^ 0x07);
		}

		return b;
	}
	 //复制jar
    public static void copyJar(File src , File des) throws FileNotFoundException, IOException{
        JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(src)));
        Manifest manifest = jarIn.getManifest();
        JarOutputStream jarOut = null;
        if(manifest == null){
            jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)));
        }else{
            jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)),manifest);
        }
         
        byte[] bytes = new byte[1024];
        while(true){
            //重点
            ZipEntry entry = jarIn.getNextJarEntry();
            if(entry == null)break;
            jarOut.putNextEntry(entry);

            int len = jarIn.read(bytes, 0, bytes.length);
            while(len != -1){
                jarOut.write(bytes, 0, len);
                len = jarIn.read(bytes, 0, bytes.length);
            }
            System.out.println("Copyed: " + entry.getName());
//            jarIn.closeEntry();
//            jarOut.closeEntry();
            String a = new String();
            a.length();
        }
        jarIn.close();
        jarOut.finish();
        jarOut.close();
    }

	// 复制jar by JarFile
	public static void copyJarByJarFile(File src, File des) throws IOException {
		// 重点
		JarFile jarFile = new JarFile(src);
		Enumeration<JarEntry> jarEntrys = jarFile.entries();
		JarOutputStream jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)));
		byte[] bytes = new byte[1024];

		while (jarEntrys.hasMoreElements()) {
			JarEntry entryTemp = jarEntrys.nextElement();
			jarOut.putNextEntry(entryTemp);
			BufferedInputStream in = new BufferedInputStream(jarFile.getInputStream(entryTemp));
			int len = in.read(bytes, 0, bytes.length);
			while (len != -1) {
				jarOut.write(bytes, 0, len);
				len = in.read(bytes, 0, bytes.length);
			}
			in.close();
			jarOut.closeEntry();
			System.out.println("Copyed: " + entryTemp.getName());
		}

		jarOut.finish();
		jarOut.close();
		jarFile.close();
	}
	
	 /** 
     * 读取流 
     *  
     * @param inStream 
     * @return 字节数组 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception {  
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
	
	 //复制jar
    public static void copyJar_Enc(File src , File des) throws Exception{
        JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(src)));
        Manifest manifest = jarIn.getManifest();
        JarOutputStream jarOut = null;
        if(manifest == null){
            jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)));
        }else{
            jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)),manifest);
        }
//        jarOut.setMethod(ZipEntry.STORED);
        byte[] bytes = new byte[1024];
        while(true){
            //重点
            ZipEntry entry = jarIn.getNextJarEntry();
            if(entry == null)break;
            System.out.println("Copyed: " + entry.getName());
//            jarOut.putNextEntry(entry);
            
            System.err.println("entry.getMethod()="+entry.getMethod());
            if (entry.getMethod() == ZipEntry.STORED) {
            	jarOut.putNextEntry(entry);
            }else {
            	 ZipEntry ne = new ZipEntry(entry.getName());
            	 jarOut.putNextEntry(ne);
            }
           
//            ZipEntry ne = new ZipEntry(entry);
            
            /** ZipEntry.STORED */
//            ne.setMethod(ZipEntry.STORED); 
//            ne.setCompressedSize(entry.getSize());
//            ne.setSize(entry.getSize());
//            CRC32 crc = new CRC32();
//
//            JarFile  jarFile = new JarFile(src);
//            byte[] b = readStream(jarFile.getInputStream(entry));
//            crc.update(b);
//            ne.setCrc(crc.getValue());
            /** ZipEntry.STORED */ 
            
            
            int len = jarIn.read(bytes, 0, bytes.length);
            while(len != -1){
                jarOut.write(bytes, 0, len);
                len = jarIn.read(bytes, 0, bytes.length);
            }
           
            String a = new String();
            a.length();
            jarOut.closeEntry();
            
        }
        jarIn.close();
        jarOut.finish();
        jarOut.close();
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

	public static void main(String[] args) throws Exception {
		Map<String, String> map = getArgMap(args);

		// String src_name = map.get("-src");
		// String src_name = "E:\\g工作\\x项目\\suicao\\加密\\73_128\\JarEncrypt2\\encTest-0.0.1-SNAPSHOT.jar";
		// String src_name = "E:\\g工作\\x项目\\suicao\\加密\\JarEncrypt\\jars\\xnymanage\\xny-manage-0.0.1-SNAPSHOT.jar";
		String src_name = "E:\\STS_WORKSPACE\\SUICHAO\\suichao-ms-3\\encSpringTest\\target\\encSpringTest-0.0.1-SNAPSHOT.jar";
		if (src_name == null) {
			System.out.println("usage: java Encrypt -src xxx.jar");
			return;
		}

		Encrypt5 coder = new Encrypt5();

		// String dst_name = map.get("-dst");
		// String dst_name = "E:\\g工作\\x项目\\suicao\\加密\\73_128\\JarEncrypt2\\encTest-0.0.1-SNAPSHOT_enc2.jar";
		// String dst_name = "E:\\g工作\\x项目\\suicao\\加密\\JarEncrypt\\jars\\xnymanage\\xny-manage-0.0.1-SNAPSHOT_enc2.jar";
		String dst_name = null;
		if (dst_name == null || dst_name.equals(src_name))
			dst_name = src_name.substring(0, src_name.length() - 4) + "_encrypt.jar";

		System.out.printf("encode jar file: [%s ==> %s ]\n", src_name, dst_name);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		File src_file = new File(src_name);
		File dst_file = new File(dst_name);
		

		// FileOutputStream dst_fos = new FileOutputStream(dst_file);
		// JarOutputStream dst_jar = new JarOutputStream(dst_fos);

//		 JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(src_file)));
//	        Manifest manifest = jarIn.getManifest();
//	        JarOutputStream dst_jar = null;
//	        if(manifest == null){
//	        	dst_jar = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(dst_file)));
//	        }else{
//	        	dst_jar = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(dst_file)),manifest);
//	        }
		JarOutputStream dst_jar = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(dst_file)));
		JarFile src_jar = new JarFile(src_file);
		
		File dst_file1 = new File("E:\\STS_WORKSPACE\\SUICHAO\\suichao-ms-3\\encSpringTest\\target\\encSpringTest-0.0.1-SNAPSHOT_copy1.jar");
		copyJar(src_file, dst_file1);		
		
		
		File dst_file2 = new File("E:\\STS_WORKSPACE\\SUICHAO\\suichao-ms-3\\encSpringTest\\target\\encSpringTest-0.0.1-SNAPSHOT_copy2.jar");
//		copyJarByJarFile(src_file, dst_file2);
		copyJar_Enc(src_file, dst_file2);
		
		
		// System.out.println("文件名\t文件大小\t压缩后的大小");
		for (Enumeration<JarEntry> enumeration = src_jar.entries(); enumeration.hasMoreElements();) {
			JarEntry entry = enumeration.nextElement();

			// 文件名称
			String name = entry.getName();
			// 文件大小
			long size = entry.getSize();
			// 压缩后的大小
			long compressedSize = entry.getCompressedSize();
			// System.out.println(name + "\t" + size + "\t" + compressedSize);

			// if(name.endsWith(".class") && name.startsWith("BOOT-INF/classes/com/seassoon/suichao/xny/")){
			// if (name.endsWith(".class") && name.startsWith("com/seassoon/suichao/xny/")) {
			if (isEncrypt(name)) {
				System.out.println("encrypt " + name);
				if (name.endsWith(".class")) {
					try {
						InputStream is = src_jar.getInputStream(entry);
						int len;
						while ((len = is.read(buf, 0, buf.length)) != -1) {
							baos.write(buf, 0, len);
						}
						byte[] bytes_tmp = baos.toByteArray();
						System.err.println("bytelen1=" + bytes_tmp.length);
						byte[] bytes = coder.encrypt(bytes_tmp);
						System.err.println("bytelen2=" + bytes.length);
						JarEntry ne = new JarEntry(name);
						dst_jar.putNextEntry(ne);
						dst_jar.write(bytes);

						baos.reset();
					} catch (Exception e) {
						System.out.println("encrypt error happend~");
						e.printStackTrace();
					}
				} else {
					// BOOT-INF/lib/spring-core-4.3.13.RELEASE.jar
					// String jarFilePath="jar:file:"+src_name+"!/"+name;
					// JarUtil.readJARList(jarFilePath);

					CreateFileUtil.createDir("tmp");
					String tmpJarFileSrcPath = "tmp/" + name.substring(name.lastIndexOf("/") + 1);
					System.err.println("tmpJarFilePath=" + tmpJarFileSrcPath);

					File tmpJarFile_src = new File(tmpJarFileSrcPath);

					InputStream is = src_jar.getInputStream(entry);
					CreateFileUtil.inputStreamToFile(is, tmpJarFile_src);
					is.close();
					// JarUtil.readJARList(tmpJarFile);

					ByteArrayOutputStream tmp_baos = new ByteArrayOutputStream();
					byte[] tmp_buf = new byte[1024];
					String tmp_dst_name = tmpJarFileSrcPath.substring(0, tmpJarFileSrcPath.length() - 4) + "_encrypt.jar";
					File tmp_dst_file = new File(tmp_dst_name);
					// FileOutputStream tmp_dst_fos = new FileOutputStream(tmp_dst_file);
					// JarOutputStream tmp_dst_jar = new JarOutputStream(tmp_dst_fos);
					JarOutputStream tmp_dst_jar = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(tmp_dst_file)));

					JarFile tmp_jar_file = new JarFile(tmpJarFile_src);

					String tmp_dst_name2 = tmpJarFileSrcPath.substring(0, tmpJarFileSrcPath.length() - 4) + "_encrypt2.jar";
					File tmp_dst_file2 = new File(tmp_dst_name2);
					copyJarByJarFile(tmpJarFile_src, tmp_dst_file2);

					String tmp_dst_name3 = tmpJarFileSrcPath.substring(0, tmpJarFileSrcPath.length() - 4) + "_encrypt3.jar";
					File tmp_dst_file3 = new File(tmp_dst_name3);
					copyJar(tmpJarFile_src, tmp_dst_file3);
//					copyJarByJarFile_Enc(tmpJarFile_src, tmp_dst_file3);

					System.out.println("临时文件名\t文件大小\t压缩后的大小");
					for (Enumeration<JarEntry> tmp_enumeration = tmp_jar_file.entries(); tmp_enumeration.hasMoreElements();) {
						JarEntry tmp_entry = tmp_enumeration.nextElement();
						// 文件名称
						String tmp_name = tmp_entry.getName();
						// 文件大小
						long tmp_size = tmp_entry.getSize();
						// 压缩后的大小
						long tmp_compressedSize = tmp_entry.getCompressedSize();
						System.out.println(tmp_name + "\t" + tmp_size + "\t" + tmp_compressedSize);
						if (isEncrypt(tmp_name)) {
							System.out.println("encrypt tmp_name " + tmp_name);

							try {
								// InputStream tmp_is = tmp_jar_file.getInputStream(tmp_entry);
								// int tmp_len;
								// while ((tmp_len = tmp_is.read(tmp_buf, 0, tmp_buf.length)) != -1) {
								// tmp_baos.write(tmp_buf, 0, tmp_len);
								// }

								BufferedInputStream in = new BufferedInputStream(tmp_jar_file.getInputStream(tmp_entry));
								int len = in.read(tmp_buf, 0, tmp_buf.length);
								while (len != -1) {
									tmp_baos.write(tmp_buf, 0, len);
									len = in.read(tmp_buf, 0, tmp_buf.length);
								}

								byte[] bytes_tmp = tmp_baos.toByteArray();
								System.err.println("tmp bytelen1=" + bytes_tmp.length);
								byte[] bytes = coder.encrypt(bytes_tmp);
								System.err.println("tmp bytelen2=" + bytes.length);
								JarEntry ne = new JarEntry(tmp_name);
								tmp_dst_jar.putNextEntry(ne);
//								 tmp_dst_jar.putNextEntry(tmp_entry);
								tmp_dst_jar.write(bytes);

								tmp_baos.reset();

								in.close();
								tmp_dst_jar.closeEntry();
							} catch (Exception e) {
								System.out.println("encrypt error happend~");
								e.printStackTrace();
							}

						} else {
							System.out.println("no encrypt tmp " + tmp_name);

							// System.out.println("no encrypt tmp " + tmp_name.replaceAll("/", "."));
							tmp_dst_jar.putNextEntry(tmp_entry);
							// JarEntry ne = new JarEntry(tmp_name);
							// tmp_dst_jar.putNextEntry(ne);
							// InputStream tmp_is = tmp_jar_file.getInputStream(tmp_entry);
							// int tmp_len;
							// while ((tmp_len = tmp_is.read(tmp_buf, 0, tmp_buf.length)) != -1) {
							// // baos.write(buf, 0, len);
							// tmp_dst_jar.write(buf, 0, tmp_len);
							// }
							// tmp_is.close();

							BufferedInputStream in = new BufferedInputStream(tmp_jar_file.getInputStream(tmp_entry));
							int len = in.read(tmp_buf, 0, tmp_buf.length);
							while (len != -1) {
								tmp_dst_jar.write(tmp_buf, 0, len);
								len = in.read(tmp_buf, 0, tmp_buf.length);
							}
							in.close();
							tmp_dst_jar.closeEntry();

						}
					}
					tmp_dst_jar.finish();
					tmp_dst_jar.close();
					// tmp_dst_fos.close();

					tmp_jar_file.close();

					// 读取jar包数据写入大JAR文件
					
					File small_jar = new File(tmp_dst_name);
					InputStream input = new FileInputStream(small_jar);
					byte[] byt = JarUtil.readStream(input);
					
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
//					 dst_jar.putNextEntry(entry);
					 
					// InputStream input = new FileInputStream(tmp_dst_file);
					// byte[] byt = new byte[input.available()];
					// input.read(byt);
				
					dst_jar.write(byt);
					input.close();
					dst_jar.closeEntry();
				}

			} else {
				// System.out.println("no encrypt " + name.replaceAll("/", "."));
				dst_jar.putNextEntry(entry);
				// dst_jar.putNextEntry(new JarEntry (name));
				// JarEntry ne = new JarEntry(name);
				// dst_jar.putNextEntry(ne);

				// InputStream is = src_jar.getInputStream(entry);
				// int len;
				// while ((len = is.read(buf, 0, buf.length)) != -1) {
				// // baos.write(buf, 0, len);
				// dst_jar.write(buf, 0, len);
				// }

				BufferedInputStream in = new BufferedInputStream(src_jar.getInputStream(entry));
				int len = in.read(buf, 0, buf.length);
				while (len != -1) {
					dst_jar.write(buf, 0, len);
					len = in.read(buf, 0, buf.length);
				}
				in.close();
				// dst_jar.closeEntry();
			}

			// baos.reset();
		}

//		 jarIn.close();
		dst_jar.finish();
		dst_jar.close();
		// dst_fos.close();
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
	public static byte[] getFileBytes(File file) throws FileNotFoundException,
	        IOException {
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
}
