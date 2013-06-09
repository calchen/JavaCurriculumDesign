package com.cky.encryption;
/**
 * 文件名： DBEncrypt.java
 * 作   者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com
 * 创建时间：2013-6-8 上午11:20:54
 * 最后修改：2013-6-8 上午11:20:54
 * 类说明 ：
 */
public class DBEncrypt {

	public String encrypt(String str) {
		char [] a=str.toCharArray();
		
		for(int i=0;i<str.length();i++) {
			a[i]=(char)~a[i];
		}
		str=String.valueOf(a);
		return str;
	}
	
	public String deciphering(String str) {
		char [] a=str.toCharArray();
		
		for(int i=0;i<str.length();i++) {
			a[i]=(char)~a[i];
		}
		str=String.valueOf(a);
		return str;
	}
}