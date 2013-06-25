package com.cky.main;

/**
 * 文件名： Goods.java 
 * 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 
 * 创建时间：2013-6-8 上午11:09:46 
 * 最后修改：2013-6-14 下午12:59:00
 * 类说明 ：货物类
 */
public class Goods {
	// 编号
	private String ID;
	// 批次
	private String batch;
	// 名称
	private String name;
	// 所在仓库
	private String warehouse;
	// 数目
	private int num;

	// 构造方法
	public Goods(String ID, String batch, String warehouse, String name, int num) {
		super();
		this.ID = ID;
		this.batch = batch;
		this.name = name;
		this.warehouse = warehouse;
		this.num = num;
	}

	// ID、batch、name、warehouse、num的get和set方法
	public String getID() {
		return ID;
	}

	public String getBatch() {
		return batch;
	}

	public String getName() {
		return name;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public int getNum() {
		return num;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
