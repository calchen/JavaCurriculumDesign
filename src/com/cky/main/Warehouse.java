package com.cky.main;
/**
 * 文件名： Warehouse.java
 * 作   者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com
 * 创建时间：2013-6-14 下午12:40:48
 * 最后修改：2013-6-14 下午12:59:00
 * 类说明 ：仓库类
 */
public class Warehouse {
	//编号
	private String ID;
	//容量
	private int size;
	//位置
	private String location;

	public Warehouse(String ID, int size, String location) {
		super();
		this.ID = ID;
		this.size = size;
		this.location = location;
	}

	public String getID() {
		return ID;
	}

	public int getSize() {
		return size;
	}

	public String getLocation() {
		return location;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
