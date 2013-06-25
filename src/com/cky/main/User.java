package com.cky.main;

/**
 * 文件名： User.java 
 * 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 
 * 创建时间：2013-6-8 上午11:08:48 
 * 最后修改：2013-6-14 下午12:59:00
 * 类说明 ：用户类
 */
public class User {
	// 编号
	private String ID;
	// 名称
	private String name;
	// 密码
	private String passwd;
	// 类型
	private String type;

	// 构造方法
	public User(String ID, String name, String passwd, String type) {

		this.ID = ID;
		this.name = name;
		this.passwd = passwd;
		this.type = type;

	}

	// ID、name、passwd、type的get和set方法
	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getType() {
		return type;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setType(String type) {
		this.type = type;
	}
}
