package com.cky.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cky.dbc.DBC;
import com.cky.gui.MainFrame;

/**
 * 文件名： Main.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-10
 * 下午8:36:24 最后修改：2013-6-10 下午8:36:24 类说明 ：
 */
public class Main {
	// 货物列表
	private ArrayList<Goods> goodslist = new ArrayList<Goods>();
	// 用户列表
	private ArrayList<User> userlist = new ArrayList<User>();
	// 仓库列表
	private ArrayList<Warehouse> warehouselist = new ArrayList<Warehouse>();

	private MainFrame gui = null;
	private User user = null;
	private DBC dbc = new DBC();
	
	public Main(MainFrame gui, User user) {
		this.gui = gui;
		this.user = user;
		loadGoodslist();
		loadWarehouselist();
		if(user.getType().equals("主管")) {
			loadUserlist();
		}

	}

	public ArrayList<Goods> getGoodslist() {
		return goodslist;
	}

	public ArrayList<User> getUserlist() {
		return userlist;
	}

	public ArrayList<Warehouse> getWarehouselist() {
		return warehouselist;
	}

	//初始化
	public void loadGoodslist() {
		String sql = "SELECT * FROM GOODS";
		ResultSet rs = dbc.query(sql);
		try {
			while(rs.next()) {
				String goodsid = rs.getString("GOODS_ID");
				String batch = rs.getString("GOODS_BATCH");
				String warehouseid = rs.getString("WAREHOUSE_ID");
				String name = rs.getString("GOODS_NAME");
				int num = rs.getInt("NUM");
				goodslist.add(new Goods(goodsid, batch, warehouseid, name, num));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadUserlist() {
		String sql = "SELECT * FROM USER";
		ResultSet rs = dbc.query(sql);
		try {
			while(rs.next()) {
				String ID=rs.getString("USER_ID"); 
				String name=rs.getString("USER_NAME");
				String passwd=rs.getString("USER_PASSWD");
				String type=rs.getString("USER_TYPE");
				userlist.add(new User(ID, name, passwd, type));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadWarehouselist() {
		String sql = "SELECT * FROM WAREHOUSE";
		ResultSet rs = dbc.query(sql);
		try {
			while(rs.next()) {
				String id = rs.getString("WAREHOUSE_ID");
				int size = rs.getInt("WAREHOUSE_SIZE");
				String locate = rs.getString("WAREHOUSE_LOCATION");
				warehouselist.add(new Warehouse(id, size, locate));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRecord() {
		
	}

	//添加元素
	public boolean addGoodslist(Goods goods) {
		String sql = "INSERT INTO GOODS " +
				"(GOODS_ID, GOODS_BATCH, WAREHOUSE_ID, GOODS_NAME, NUM) " +
				"VALUES ('"+
				goods.getID()+"', '"+
				goods.getBatch()+"', '"+
				goods.getWarehouse()+"', '"+
				goods.getName()+"', "+
				goods.getNum()+")";
		
		if(dbc.update(sql)){
			goodslist.clear();
			loadGoodslist();
			return true;
		}
		return false;

	}

	public boolean addUserlist(User user) {
		return false;
	}

	public boolean addWarehouselist(Warehouse warehouse) {
		return false;
	}
	
	//更新元素
	public boolean updateGoodslist(Goods goods) {
		return false;
	}

	public boolean updateUserlist(User user) {
		return false;
	}

	public boolean updateWarehouselist(Warehouse warehouse) {
		return false;
	}

	//删除元素
	public boolean deleteGoodslist(Goods goods) {
		return false;
	}

	public boolean deleteUserlist(User user) {
		return false;
	}

	public boolean deleteWarehouselist(Warehouse warehouse) {
		return false;
	}
}
