package com.cky.main;

import java.sql.*;
import java.util.*;

import com.cky.dbc.DBC;
import com.cky.gui.MainFrame;

/**
 * 文件名： Main.java 
 * 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 
 * 创建时间：2013-6-10 下午8:36:24 
 * 最后修改：2013-06-25 上午09:55 
 * 类说明 ：实现主要功能，提供相关操作方法
 */
public class Main {
	// 货物列表
	private ArrayList<Goods> goodslist = new ArrayList<Goods>();
	// 用户列表
	private ArrayList<User> userlist = new ArrayList<User>();
	// 仓库列表
	private ArrayList<Warehouse> warehouselist = new ArrayList<Warehouse>();
	// 记录列表
	private ArrayList<String[]> recordlist = new ArrayList<String[]>();

	// 入库出库损三个字段
	private static final String IN = "入库";
	private static final String OUT = "出库";
	private static final String DESTORY = "报损";

	private MainFrame gui = null;
	private User user = null;

	public Main(MainFrame gui, User user) {
		this.gui = gui;
		this.user = user;
		// 初始化3个列表
		loadGoodslist();
		loadWarehouselist();
		if (user.getType().equals("主管")) {
			loadUserlist();
		}

	}

	// 列表的get方法
	public ArrayList<Goods> getGoodslist() {
		return goodslist;
	}

	public ArrayList<User> getUserlist() {
		return userlist;
	}

	public ArrayList<Warehouse> getWarehouselist() {
		return warehouselist;
	}

	public ArrayList<String[]> getRecordlist() {
		return recordlist;
	}

	// 列表初始化操作
	public void loadGoodslist() {
		String sql = "SELECT * FROM GOODS";
		DBC dbc = new DBC();
		ResultSet rs = dbc.query(sql);
		try {
			while (rs.next()) {
				String goodsid = rs.getString("GOODS_ID");
				String batch = rs.getString("GOODS_BATCH");
				String warehouseid = rs.getString("WAREHOUSE_ID");
				String name = rs.getString("GOODS_NAME");
				int num = rs.getInt("NUM");
				goodslist
						.add(new Goods(goodsid, batch, warehouseid, name, num));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.disconnect();
		}
	}

	public void loadUserlist() {
		String sql = "SELECT * FROM USER";
		DBC dbc = new DBC();
		ResultSet rs = dbc.query(sql);
		try {
			while (rs.next()) {
				String ID = rs.getString("USER_ID");
				String name = rs.getString("USER_NAME");
				String passwd = rs.getString("USER_PASSWD");
				String type = rs.getString("USER_TYPE");
				userlist.add(new User(ID, name, passwd, type));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.disconnect();
		}
	}

	public void loadWarehouselist() {
		String sql = "SELECT * FROM WAREHOUSE";
		DBC dbc = new DBC();
		ResultSet rs = dbc.query(sql);
		try {
			while (rs.next()) {
				String id = rs.getString("WAREHOUSE_ID");
				int size = rs.getInt("WAREHOUSE_SIZE");
				String locate = rs.getString("WAREHOUSE_LOCATION");
				warehouselist.add(new Warehouse(id, size, locate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.disconnect();
		}
	}

	// 添加元素
	public boolean addGoodslist(Goods goods, String type) {
		String sql = "INSERT INTO GOODS "
				+ "(GOODS_ID, GOODS_BATCH, WAREHOUSE_ID, GOODS_NAME, NUM) "
				+ "VALUES ('" + goods.getID() + "', '" + goods.getBatch()
				+ "', '" + goods.getWarehouse() + "', '" + goods.getName()
				+ "', " + goods.getNum() + ")";
		DBC dbc = new DBC();
		if (dbc.update(sql) && addRecord(goods, IN)) {
			goodslist.clear();
			loadGoodslist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;

	}

	public boolean addUserlist(User user) {
		String sql = "INSERT INTO USER "
				+ "(USER_ID, USER_NAME, USER_PASSWD, USER_TYPE) " + "VALUES ('"
				+ user.getID() + "', '" + user.getName() + "', '"
				+ user.getPasswd() + "', '" + user.getType() + "')";
		DBC dbc = new DBC();
		if (dbc.update(sql)) {
			userlist.clear();
			loadUserlist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	public boolean addWarehouselist(Warehouse warehouse) {
		String sql = "INSERT INTO WAREHOUSE (WAREHOUSE_ID, WAREHOUSE_SIZE, WAREHOUSE_LOCATION) "
				+ "VALUES ('"
				+ warehouse.getID()
				+ "', "
				+ warehouse.getSize()
				+ ", '" + warehouse.getLocation() + "')";
		DBC dbc = new DBC();
		if (dbc.update(sql)) {
			warehouselist.clear();
			loadWarehouselist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	// 修改元素
	public boolean updateGoodslist(Goods goods, String type) {
		// 采用先删除再插入的方式实现修改，以避免用户错误修改并减少对变化量的判断
		String sql1 = "DELETE FROM GOODS WHERE GOODS_ID='" + goods.getID()
				+ "' and GOODS_BATCH='" + goods.getBatch()
				+ "' and WAREHOUSE_ID='" + goods.getWarehouse() + "'";

		// 找到相同id,batch,warehouse的goods
		Goods goods2 = null;
		for (Goods i : goodslist) {
			if (i.getID().equals(goods.getID())
					&& i.getBatch().equals(goods.getBatch())
					&& i.getWarehouse().equals(goods.getWarehouse())) {
				goods2 = i;
				break;
			}
		}
		// 如果未找到之间返回false
		if (goods2 == null) {
			return false;
		}
		// 对操作类型进行判断
		if (type.equals(IN)) {
			goods2.setNum(goods2.getNum() + goods.getNum());
		} else {
			if (goods.getNum() <= goods2.getNum()) {
				goods2.setNum(goods2.getNum() - goods.getNum());
			} else {
				return false;
			}
		}

		String sql2 = "INSERT INTO GOODS (GOODS_ID, GOODS_BATCH, WAREHOUSE_ID, GOODS_NAME, NUM) VALUES ('"
				+ goods2.getID()
				+ "', '"
				+ goods2.getBatch()
				+ "', '"
				+ goods2.getWarehouse()
				+ "', '"
				+ goods2.getName()
				+ "', "
				+ goods2.getNum() + ")";
		DBC dbc = new DBC();
		if (dbc.update(sql1) && dbc.update(sql2) && addRecord(goods, type)) {
			goodslist.clear();
			loadGoodslist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	public boolean updateUserlist(User user) {
		// 采用先删除再插入的方式实现修改，以避免用户错误修改并减少对变化量的判断
		String sql1 = "DELETE FROM USER WHERE USER_ID='" + user.getID() + "'";
		String sql2 = "INSERT INTO USER (USER_ID, USER_NAME, USER_PASSWD, USER_TYPE) VALUES ('"
				+ user.getID()
				+ "', '"
				+ user.getName()
				+ "', '"
				+ user.getPasswd() + "', '" + user.getType() + "')";
		DBC dbc = new DBC();
		if (dbc.update(sql1) && dbc.update(sql2)) {
			userlist.clear();
			loadUserlist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	public boolean updateWarehouselist(Warehouse warehouse) {
		// 采用先删除再插入的方式实现修改，以避免用户错误修改并减少对变化量的判断
		String sql1 = "DELETE FROM WAREHOUSE WHERE WAREHOUSE_ID='"
				+ warehouse.getID() + "'";
		String sql2 = "INSERT INTO WAREHOUSE (WAREHOUSE_ID, WAREHOUSE_SIZE, WAREHOUSE_LOCATION) VALUES ('"
				+ warehouse.getID()
				+ "', "
				+ warehouse.getSize()
				+ ", '"
				+ warehouse.getLocation() + "')";
		DBC dbc = new DBC();
		if (dbc.update(sql1) && dbc.update(sql2)) {
			warehouselist.clear();
			loadWarehouselist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	// 删除元素
	public boolean deleteGoodslist(Goods goods, String type) {
		String sql = "DELETE FROM GOODS WHERE GOODS_ID='" + goods.getID()
				+ "' and GOODS_BATCH='" + goods.getBatch()
				+ "' and WAREHOUSE_ID='" + goods.getWarehouse() + "'";
		DBC dbc = new DBC();
		if (dbc.update(sql) && addRecord(goods, OUT)) {
			goodslist.clear();
			loadGoodslist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	public boolean deleteUserlist(User user) {
		String sql = "DELETE FROM USER WHERE USER_ID='" + user.getID() + "'";
		DBC dbc = new DBC();
		if (dbc.update(sql)) {
			userlist.clear();
			loadUserlist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	public boolean deleteWarehouselist(Warehouse warehouse) {
		// 为保证数据库完整性要删除对应的仓库以及仓库内所有的货物
		String sql1 = "DELETE FROM GOODS WHERE WAREHOUSE_ID='"
				+ warehouse.getID() + "'";
		String sql2 = "DELETE FROM WAREHOUSE WHERE WAREHOUSE_ID='"
				+ warehouse.getID() + "'";
		for (Goods i : goodslist) {
			if (i.getWarehouse().equals(warehouse.getID()) && i.getNum() != 0) {
				return false;
			}
		}
		DBC dbc = new DBC();
		if (dbc.update(sql1) && dbc.update(sql2)) {
			warehouselist.clear();
			loadWarehouselist();
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return false;
	}

	// 记录的相关操作
	// 显示所有记录
	public void allRecord() {
		String sql = "SELECT * FROM WAREHOUSE_RECORD";
		DBC dbc = new DBC();
		ResultSet rs = dbc.query(sql);
		recordlist.clear();
		try {
			while (rs.next()) {
				String[] str = new String[8];
				str[0] = String.valueOf(rs.getInt("ID"));
				str[1] = rs.getString("GOODS_ID");
				str[2] = rs.getString("GOODS_BATCH");
				str[3] = rs.getString("WAREHOUSE_ID");
				str[4] = rs.getString("USER_ID");
				str[5] = rs.getString("TYPE");
				str[6] = String.valueOf(rs.getInt("NUM"));
				str[7] = rs.getTimestamp("DATETIME").toString();
				recordlist.add(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbc.disconnect();
		}

	}

	// 添加一条记录
	public boolean addRecord(Goods goods, String type) {
		Calendar calendar = Calendar.getInstance();
		// 日期时间
		String date = "" + calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + " "
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":"
				+ calendar.get(Calendar.SECOND);
		String sql = "INSERT INTO WAREHOUSE_RECORD (ID, WAREHOUSE_ID, GOODS_ID, GOODS_BATCH, USER_ID, DATETIME, TYPE, NUM) VALUES (NULL, '"
				+ goods.getWarehouse()
				+ "', '"
				+ goods.getID()
				+ "', '"
				+ goods.getBatch()
				+ "', '"
				+ user.getID()
				+ "', '"
				+ date
				+ "', '" + type + "', " + goods.getNum() + ");";
		DBC dbc = new DBC();
		if (dbc.update(sql)) {
			dbc.disconnect();
			return true;
		}
		dbc.disconnect();
		return true;
	}

	// 按条件查询记录
	public boolean queryRecordlist(String[] re) {
		String sql = "SELECT * FROM WAREHOUSE_RECORD WHERE ";
		if (!re[0].equals("")) {
			sql += "GOODS_ID='" + re[0] + "' AND ";
		}
		if (!re[1].equals("")) {
			sql += "GOODS_BATCH='" + re[1] + "' AND ";
		}
		if (!re[2].equals("")) {
			sql += "WAREHOUSE_ID='" + re[2] + "' AND ";
		}
		if (!re[3].equals("")) {
			sql += "USER_ID='" + re[3] + "' AND ";
		}
		sql += "TYPE='" + re[4] + "' AND DATETIME BETWEEN '" + re[5]
				+ "' AND '" + re[6] + "'";
		DBC dbc = new DBC();
		ResultSet rs = dbc.query(sql);
		recordlist.clear();
		try {
			while (rs.next()) {
				String[] str = new String[8];
				str[0] = String.valueOf(rs.getInt("ID"));
				str[1] = rs.getString("GOODS_ID");
				str[2] = rs.getString("GOODS_BATCH");
				str[3] = rs.getString("WAREHOUSE_ID");
				str[4] = rs.getString("USER_ID");
				str[5] = rs.getString("TYPE");
				str[6] = String.valueOf(rs.getInt("NUM"));
				str[7] = rs.getTimestamp("DATETIME").toString();
				recordlist.add(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (recordlist.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

}
