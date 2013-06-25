package com.cky.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

import com.cky.dbc.DBC;
import com.cky.encryption.DBEncrypt;

/**
 * 文件名： DBSetFrame.java 
 * 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 
 * 创建时间：2013-6-8 上午11:19:33 
 * 最后修改：2013-6-20 下午11:20:23 
 * 类说明 ：用于设置数据相关参数
 */
public class DBSetFrame extends JFrame {
	private JTextField hostNameTextField;
	private JTextField portTextField;
	private JTextField userNametextField;
	private JPasswordField passwordField;
	private JPanel contentPane;
	private int width = 338;
	private int height = 240;
	private JFrame jFrame = null;

	public DBSetFrame() {
		// 设置默认风格
		try {
			// Windows风格
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			// Java默认风格
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel")
			// ;

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		jFrame = this;
		// 设置窗口大小并在屏幕中间显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dm = toolkit.getScreenSize();
		setBounds((((int) dm.getWidth() - width) / 2),
				(((int) dm.getHeight() - height) / 2), width, height);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				LoginFrame.class.getResource("/com/cky/res/logo.jpg")));

		setTitle("数据库设置");
		setVisible(true);
		// 设置窗口大小不可变
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel DBConfigPanel = new JPanel();
		DBConfigPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "数据库设置",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		DBConfigPanel.setBounds(10, 10, 295, 147);
		contentPane.add(DBConfigPanel);
		DBConfigPanel.setLayout(null);

		JLabel hostNameLabel = new JLabel("计算机名");
		hostNameLabel.setBounds(10, 29, 54, 15);
		DBConfigPanel.add(hostNameLabel);

		hostNameTextField = new JTextField("127.0.0.1");
		hostNameTextField.setBounds(67, 26, 147, 21);
		DBConfigPanel.add(hostNameTextField);
		hostNameTextField.setColumns(10);

		JLabel portLabel = new JLabel("端  口");
		portLabel.setBounds(10, 69, 54, 15);
		DBConfigPanel.add(portLabel);

		portTextField = new JTextField("3306");
		portTextField.setBounds(67, 66, 66, 21);
		DBConfigPanel.add(portTextField);
		portTextField.setColumns(10);

		JLabel userNameLabel = new JLabel("用户名");
		userNameLabel.setBounds(10, 109, 54, 15);
		DBConfigPanel.add(userNameLabel);

		userNametextField = new JTextField();
		userNametextField.setBounds(67, 107, 83, 21);
		DBConfigPanel.add(userNametextField);
		userNametextField.setColumns(10);

		JLabel userPasswordLabel = new JLabel("密码");
		userPasswordLabel.setBounds(160, 110, 33, 15);
		DBConfigPanel.add(userPasswordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(187, 107, 83, 21);
		DBConfigPanel.add(passwordField);

		JButton cancelButton = new JButton("取消");
		cancelButton.setBounds(183, 167, 93, 23);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jFrame.dispose();
			}
		});
		contentPane.add(cancelButton);

		JButton yesButton = new JButton("确定");
		yesButton.setBounds(45, 167, 93, 23);
		yesButton.addActionListener(new ActionListener() {
			private String hostName;
			private String port;
			private String name;
			private String password;
			private String dbURL;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = new File(DBC.getDBConfig());
				if (file.exists()) {
					file.delete();
				}
				BufferedWriter bw;
				try {
					DBEncrypt dbEncrypt = new DBEncrypt();

					hostName = hostNameTextField.getText();
					port = portTextField.getText();
					name = userNametextField.getText();
					password = String.valueOf(passwordField.getPassword());
					dbURL = "jdbc:mysql://" + hostName + ":" + port
							+ "/SUPERMARKETDB";
					// 设置数据库URL、用户名、密码
					DBC.setDbURL(dbURL);
					DBC.setDbUserName(name);
					DBC.setDbPasswd(password);

					// 检查输入的信息是否正确
					if (new DBC().checkConnect()) {
						// 如果信息正确就加密并写入文件
						bw = new BufferedWriter(new FileWriter(file));
						file.createNewFile();
						bw.write(dbEncrypt.encrypt(hostName));
						bw.newLine();
						bw.write(dbEncrypt.encrypt(port));
						bw.newLine();
						bw.write(dbEncrypt.encrypt(name));
						bw.newLine();
						bw.write(dbEncrypt.encrypt(password));
						bw.flush();
						bw.close();
					}
					jFrame.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(yesButton);
	}
}
