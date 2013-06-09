package com.cky.main;

/**
 * 文件名： User.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-8
 * 上午11:08:48 最后修改：2013-6-8 上午11:08:48 类说明 ：
 */
public class User {
	private String ID;
	private String passwd;
	private String type;

	public User(String iD, String passwd, String type) {
		super();
		ID = iD;
		this.passwd = passwd;
		this.type = type;
	}

	public String getID() {
		return ID;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getType() {
		return type;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setType(String type) {
		this.type = type;
	}

}
