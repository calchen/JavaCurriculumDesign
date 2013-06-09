package com.cky.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.cky.dbc.DBC;
import com.cky.encryption.MD5;
import com.cky.main.User;

/**
 * 文件名： LoginFrame.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-8
 * 上午11:19:22 最后修改：2013-6-8 上午11:19:22 类说明 ：登录界面
 */
public class LoginFrame extends JFrame {
	private int width = 289;
	private int height = 227;

	private String type = "employer";
	private static JFrame jFrame;
	private JPanel contentPane;
	private JTextField userNameTextField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jFrame = new LoginFrame();
					// 检查数据库
					new DBC().loadDBInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
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

		// 设置窗口大小并在屏幕中间显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dm = toolkit.getScreenSize();
		setBounds((((int) dm.getWidth() - width) / 2),
				(((int) dm.getHeight() - height) / 2), width, height);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				LoginFrame.class.getResource("/logo.jpg")));

		setTitle("超市仓库管理系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		// 设置窗口大小不可变
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setBounds(100, 100, 289, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel userNameLabel = new JLabel("用户名：");
		userNameLabel.setBounds(39, 29, 54, 15);
		contentPane.add(userNameLabel);

		JLabel passwdLabel = new JLabel("密  码：");
		passwdLabel.setBounds(39, 64, 54, 15);
		contentPane.add(passwdLabel);

		userNameTextField = new JTextField();
		userNameTextField.setBounds(103, 26, 146, 21);
		contentPane.add(userNameTextField);
		userNameTextField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(103, 61, 146, 21);
		contentPane.add(passwordField);

		JRadioButton employerRadioButton = new JRadioButton("员工");
		employerRadioButton.setBounds(62, 99, 49, 23);
		contentPane.add(employerRadioButton);
		employerRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				type = "employer";
			}
		});

		JRadioButton managerRadioButton = new JRadioButton("主管");
		managerRadioButton.setBounds(173, 99, 54, 23);
		contentPane.add(managerRadioButton);
		managerRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				type = "manager";
			}
		});

		ButtonGroup group = new ButtonGroup();
		group.add(managerRadioButton);
		group.add(employerRadioButton);

		JButton loginButton = new JButton("登陆");
		loginButton.setBounds(68, 146, 146, 23);
		contentPane.add(loginButton);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login(userNameTextField.getText(),
						String.valueOf(passwordField.getPassword()));
			}
		});
	}

	private void login(String uerName, String passwd) {
		DBC dbc = new DBC();
		if (dbc.checkConnect()) {
			passwd = new MD5().encrypt(passwd);
			String sql = "SELECT * FROM USER_TABLE WHERE EMPLOYEE_TABLE_ID='"
					+ uerName + "'";
			ResultSet result = dbc.query(sql);
			String realpasswd = null, type = null;

			try {
				while (result.next()) {
					realpasswd = result.getString("PASSWD");
					type = result.getString("TYPE");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (realpasswd.equals(passwd)) {
				new MainFrame(new User(uerName, realpasswd, type));
				jFrame.dispose();
			} else {
				if (uerName.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入用户名", "错误",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// 密码错误提示窗口
					JOptionPane.showMessageDialog(null,
							"用户名或密码错误！如忘记密码请联系管理员，联系电话：12345678901", "错误",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		dbc.disconnect();
	}
}