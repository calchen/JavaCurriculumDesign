package com.cky.main;

/**
 * 文件名： Employer.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-8
 * 上午11:09:29 最后修改：2013-6-8 上午11:09:29 类说明 ：
 */
public class Employer {
	// 编号
	private String ID;
	// 姓名
	private String name;
	// 性别
	private String sex;
	// 职位
	private String position;
	// 上司
	private String boss;
	// 部门
	private String department;
	// 薪水
	private double salary;

	public Employer(String ID, String name, String sex, String position,
			String boss, String department, double salary) {
		this.ID = ID;
		this.name = name;
		this.sex = sex;
		this.position = position;
		this.boss = boss;
		this.department = department;
		this.salary = salary;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public String getPosition() {
		return position;
	}

	public String getBoss() {
		return boss;
	}

	public String getDepartment() {
		return department;
	}

	public double getSalary() {
		return salary;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
