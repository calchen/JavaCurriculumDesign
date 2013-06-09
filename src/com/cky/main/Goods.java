package com.cky.main;

/**
 * 文件名： Goods.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-8
 * 上午11:09:46 最后修改：2013-6-8 上午11:09:46 类说明 ：
 */
public class Goods {
	private String ID;
	private String batch;
	private String name;
	private int num;

	public Goods(String iD, String batch, String name, int num) {
		super();
		ID = iD;
		this.batch = batch;
		this.name = name;
		this.num = num;
	}

	public String getID() {
		return ID;
	}

	public String getBatch() {
		return batch;
	}

	public String getName() {
		return name;
	}

	public int getNum() {
		return num;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
