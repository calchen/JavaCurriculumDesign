package com.cky.dbc;

/**
 * 文件名： DBC.java
 * 作   者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com
 * 创建时间：2013-6-8 上午11:24:06
 * 最后修改：2013-6-8 上午11:24:06
 * 类说明 ：
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import com.cky.encryption.DBEncrypt;
import com.cky.gui.DBSetFrame;

public class DBC {

	private Connection ct = null;
	private Statement statement = null;
	private static String dbURL = "";
	private static String dbUserName = "";
	private static String dbPasswd = "";
	private static String dbconfig = "d:\\dbConfig.dat";

	// 用于读取com.wangjing.dbc.dbConfig.dat中的数据库URK以及用户名密码
	public void loadDBInfo() {
		File file = new File(dbconfig);
		//System.out.println(file.exists());
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
				DBC.dbURL = "jdbc:mysql://" + hostName + ":" + port
						+ "/SUPERMARKETDB";
				DBC.dbUserName = dbEncrypt.deciphering(br.readLine());
				DBC.dbPasswd = dbEncrypt.deciphering(br.readLine());
				br.close();

				// 测试用
				// System.out.println(DBC.dbURL);
				// System.out.println(DBC.dbUserName);
				// System.out.println(DBC.dbPasswd);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 建立连接
	private void connect() {
		// System.out.println(dbUserName+dbPasswd+dbURL);
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

	public static String getDbURL() {
		return dbURL;
	}

	public static String getDbUserName() {
		return dbUserName;
	}

	public static String getDbPasswd() {
		return dbPasswd;
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

	public static String getDBConfig() {
		return dbconfig;
	}

	public static void setDbconfig(String dbconfig) {
		DBC.dbconfig = dbconfig;
	}
	
}
