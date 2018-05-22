package com.seassoon.suichao.encTest.jarfile;


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

public class Encrypt {

	native byte[] encrypt(byte[] _buf);

	static {
		System.loadLibrary("encrypt");
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

	public static void main(String[] args) throws Exception {
		Map<String, String> map = getArgMap(args);

		String src_name = map.get("-src");
		if (src_name == null) {
			System.out.println("usage: java Encrypt -src xxx.jar");
			return;
		}

		Encrypt coder = new Encrypt();

		String dst_name = map.get("-dst");
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

			String name = entry.getName();
			// if(name.endsWith(".class") && name.startsWith("BOOT-INF/classes/com/seassoon/suichao/xny/")){
			if (name.endsWith(".class") && name.startsWith("com/seassoon/suichao/xny/")) {
				System.out.println("encrypt " + name.replaceAll("/", "."));
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
				System.out.println("no encrypt " + name.replaceAll("/", "."));
				dst_jar.putNextEntry(entry);
				InputStream is = src_jar.getInputStream(entry);
				int len;
				while ((len = is.read(buf, 0, buf.length)) != -1) {
					// baos.write(buf, 0, len);
					dst_jar.write(buf, 0, len);
				}
				// byte[] bytes = baos.toByteArray();

				// dst_jar.write(bytes);
			}

			// baos.reset();
		}
		src_jar.close();

		dst_jar.close();
		dst_fos.close();
	}

}
