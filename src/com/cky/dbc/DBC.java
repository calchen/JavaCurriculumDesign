package com.cky.dbc;

import java.io.*;
import java.sql.*;
import javax.swing.*;

import com.cky.encryption.DBEncrypt;
import com.cky.gui.DBSetFrame;

/**
 * 文件名： DBC.java
 * 作   者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com
 * 创建时间：2013-6-8 上午11:24:06
 * 最后修改：2013-06-25 上午0:06:00
 * 类说明 ：实现程序与数据库的链接，查询，更新。
 */
public class DBC {
	private Connection ct = null;
	private Statement statement = null;
	private static String dbURL = "";
	private static String dbUserName = "";
	private static String dbPasswd = "";
	private static String dbconfig = "d:\\dbConfig.dat";

	// 用于读取dbConfig.dat中的数据库URL以及用户名密码
	public void loadDBInfo() {
		File file = new File(dbconfig);
		if (!file.exists()) {
			// 如果dbConfig.dat文件不存在就显示DBSetConfig窗口进行输入
			new DBSetFrame();
		} else {
			try {
				// 进行解密
				DBEncrypt dbEncrypt = new DBEncrypt();
				BufferedReader br = new BufferedReader(new FileReader(file));
				String hostName = dbEncrypt.deciphering(br.readLine());
				String port = dbEncrypt.deciphering(br.readLine());
				// 设置数据库URL、用户名、密码
				DBC.dbURL = "jdbc:mysql://" + hostName + ":" + port
						+ "/SUPERMARKETDB";
				DBC.dbUserName = dbEncrypt.deciphering(br.readLine());
				DBC.dbPasswd = dbEncrypt.deciphering(br.readLine());
				// 释放资源
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 建立连接
	private void connect() {
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 得到链接5
			ct = DriverManager.getConnection(dbURL, dbUserName, dbPasswd);
			statement = ct.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 释放资源
	public void disconnect() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			statement = null;
		}

		if (ct != null) {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ct = null;
		}
	}

	// 检查连接是否正常
	public boolean checkConnect() {
		Connection testConnect = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 得到链接
			testConnect = DriverManager.getConnection(dbURL, dbUserName,
					dbPasswd);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "数据库连接失败，请检查用户名和密码以及数据库服务！",
					"错误", JOptionPane.ERROR_MESSAGE);
			// 连接失败就显示设置界面
			new DBSetFrame();
			return false;
		} finally {
			try {
				testConnect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	// 数据库查询操作
	public ResultSet query(String sql) {
		ResultSet rs = null;
		connect();
		try {
			rs = statement.executeQuery(sql);
			// rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// this.disconnect();
		return rs;
	}

	// 数据库更新操作
	public boolean update(String sql) {

		connect();
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	// dbURL、dbUserName、dbPasswd、dbconfig的get、set方法
	public static String getDbURL() {
		return dbURL;
	}

	public static String getDbUserName() {
		return dbUserName;
	}

	public static String getDbPasswd() {
		return dbPasswd;
	}

	public static String getDBConfig() {
		return dbconfig;
	}

	public static void setDbURL(String dbURL) {
		DBC.dbURL = dbURL;
	}

	public static void setDbUserName(String dbUserName) {
		DBC.dbUserName = dbUserName;
	}

	public static void setDbPasswd(String dbPasswd) {
		DBC.dbPasswd = dbPasswd;
	}

	public static void setDbconfig(String dbconfig) {
		DBC.dbconfig = dbconfig;
	}
}
