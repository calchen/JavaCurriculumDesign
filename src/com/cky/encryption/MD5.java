package com.cky.encryption;
/**
 * 文件名： MD5.java
 * 作   者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com
 * 创建时间：2013-6-8 上午11:20:26
 * 最后修改：2013-6-8 上午11:20:26
 * 类说明 ：
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public String encrypt (String plainText ) { 
		String pd=null;
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes()); 
			byte b[] = md.digest(); 
	
			int i; 
	
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) 
					i+= 256; 
				if(i<16) 
					buf.append("0"); 
				
				buf.append(Integer.toHexString(i)); 
			} 
			pd=buf.toString();

		} catch (NoSuchAlgorithmException e) { 
		// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
		
		return pd;

	} 
}

