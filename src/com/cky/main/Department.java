package com.cky.main;

/**
 * 文件名： Department.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-8
 * 上午11:10:03 最后修改：2013-6-8 上午11:10:03 类说明 ：
 */
public class Department {
	// 部门
	private String ID;
	// 名称
	private String name;
	// 人数
	private int num;
	// 负责人
	private String manager;

	public Department(String iD, String name, int num, String manager) {
		super();
		ID = iD;
		this.name = name;
		this.num = num;
		this.manager = manager;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getNum() {
		return num;
	}

	public String getManager() {
		return manager;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
