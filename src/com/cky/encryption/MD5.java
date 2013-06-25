package com.cky.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件名： MD5.java
 * 作   者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com
 * 创建时间：2013-6-8 上午11:20:26
 * 最后修改：2013-06-25 上午09:13:00
 * 类说明 ：实现md5加密
 */
public class MD5 {
	
	public String encrypt (String str ) { 
		String str1="";
		try {
			// 使用MessageDigest提供信息摘要算法的功能
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			StringBuffer buffer = new StringBuffer("");
			for (int i = 0, j = 0; j < b.length; j++) {
				i = b[j];
				if (i < 0)
					i += 256;
				if (i < 16)
					buffer.append("0");

				buffer.append(Integer.toHexString(i));
			}
			str1 = buffer.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str1;
	} 
}

