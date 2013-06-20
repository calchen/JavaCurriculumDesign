package com.design.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.cky.gui.LoginFrame;
import com.cky.main.User;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

/**
 * 文件名： Main.java 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 创建时间：2013-6-9
 * 上午10:29:32 最后修改：2013-6-9 上午10:29:32 类说明 ：
 */
public class Main extends JFrame {

	private JPanel contentPane;
	private int width = 800;
	private int height = 600;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
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
				LoginFrame.class.getResource("/com/cky/res/logo.jpg")));

		setTitle("超市仓库管理系统");
		setVisible(true);
		// 设置窗口大小不可变
		// setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu1 = new JMenu("文件");
		menuBar.add(fileMenu1);

		JMenu setMenu2 = new JMenu("设置");
		fileMenu1.add(setMenu2);

		JMenuItem DBSetMenuItem3 = new JMenuItem("数据库设置");
		setMenu2.add(DBSetMenuItem3);

		JMenuItem exitMenuItem2 = new JMenuItem("退出");
		fileMenu1.add(exitMenuItem2);

		JMenu helpMenu1 = new JMenu("帮助");
		menuBar.add(helpMenu1);

		JMenuItem aboutMenuItem2 = new JMenuItem("关于");
		helpMenu1.add(aboutMenuItem2);

		// setBounds(100, 100, 660, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// 标签页
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setToolTipText("");
		contentPane.add(jTabbedPane, BorderLayout.CENTER);

		JPanel goodsPanel = new GoodsPanel();
		jTabbedPane.addTab("货物管理", null, goodsPanel, "货物管理");
		jTabbedPane.setEnabledAt(0, true);
		goodsPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel warehousePanel = new WarehousePanel();
		jTabbedPane.addTab("仓库管理", null, warehousePanel, "仓库管理");
		jTabbedPane.setEnabledAt(1, true);
		warehousePanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel recordPanel = new RecordPanel();
		jTabbedPane.addTab("出入库记录", null, recordPanel, "出入库记录");
		jTabbedPane.setEnabledAt(2, true);
		recordPanel.setLayout(new GridLayout(2,1, 0, 0));
	
		JPanel userPanel = new UserPanel(new User("123","456","4658","员工"));
		jTabbedPane.addTab("用户管理", null, userPanel, "用户管理");
		jTabbedPane.setEnabledAt(3, true);

		

	}
}

class UserPanel extends JPanel {
	private JTable table;
	private JTextField passwdTextField;
	private JTextField IDTextField;
	private JTextField nameTextField;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;

	public UserPanel(User user) {
		super();
		if (user.getType().equals("主管")) {
			table = new JTable();
			// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setColumnSelectionAllowed(true);
			table.setFillsViewportHeight(true);
			table.getTableHeader().setReorderingAllowed(false);

			table.setModel(new DefaultTableModel(new Object[][] { { null, null,
					null }, }, new String[] { "用户名", "姓名", "类型" }));

			JScrollPane rightScrollPane = new JScrollPane(table);
			rightScrollPane
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			rightScrollPane
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(rightScrollPane);

			JPanel leftPanel = new JPanel();
			this.add(leftPanel);
			leftPanel.setLayout(null);

			JLabel passwdLabel = new JLabel("密码");
			passwdLabel.setBounds(46, 144, 48, 15);
			leftPanel.add(passwdLabel);

			passwdTextField = new JPasswordField();
			passwdTextField.setBounds(125, 141, 137, 21);
			leftPanel.add(passwdTextField);
			passwdTextField.setColumns(10);

			JLabel IDLabel = new JLabel("用户名");
			IDLabel.setBounds(46, 38, 48, 15);
			leftPanel.add(IDLabel);

			IDTextField = new JTextField();
			IDTextField.setBounds(125, 35, 137, 21);
			leftPanel.add(IDTextField);
			IDTextField.setColumns(10);

			JLabel nameLabel = new JLabel("姓名");
			nameLabel.setBounds(46, 91, 54, 15);
			leftPanel.add(nameLabel);

			nameTextField = new JTextField();
			nameTextField.setBounds(125, 88, 137, 21);
			leftPanel.add(nameTextField);
			nameTextField.setColumns(10);

			JLabel typeLabel = new JLabel("类型");
			typeLabel.setBounds(46, 200, 54, 15);
			leftPanel.add(typeLabel);

			JComboBox typeComboBox = new JComboBox();
			typeComboBox.setModel(new DefaultComboBoxModel(new String[] { "员工",
					"主管" }));
			typeComboBox.setBounds(125, 197, 50, 21);
			leftPanel.add(typeComboBox);

			JButton addButton = new JButton("添加");
			addButton.setBounds(26, 278, 93, 23);
			leftPanel.add(addButton);

			JButton updateButton = new JButton("修改");
			updateButton.setBounds(145, 278, 93, 23);
			leftPanel.add(updateButton);

			JButton resetButton = new JButton("重置");
			resetButton.setBounds(264, 278, 93, 23);
			leftPanel.add(resetButton);

			this.setLayout(new GridLayout(1, 0, 0, 0));
		} else if (user.getType().equals("员工")) {

			JLabel IDLabel = new JLabel("用户名");
			IDLabel.setBounds(60, 44, 49, 15);
			this.add(IDLabel);

			JLabel showIDLabel = new JLabel(user.getID());
			showIDLabel.setBounds(144, 44, 131, 15);
			this.add(showIDLabel);

			JLabel nameLabel = new JLabel("姓名");
			nameLabel.setBounds(60, 94, 49, 15);
			this.add(nameLabel);

			JLabel showNameLabel = new JLabel(user.getName());
			showNameLabel.setBounds(144, 94, 131, 15);
			this.add(showNameLabel);

			JLabel oldPasswdLabel = new JLabel("当前密码");
			oldPasswdLabel.setBounds(60, 144, 48, 15);
			this.add(oldPasswdLabel);

			oldPasswordField = new JPasswordField();
			oldPasswordField.setBounds(144, 144, 131, 21);
			this.add(oldPasswordField);

			JLabel newPasswdLabel = new JLabel("新密码");
			newPasswdLabel.setBounds(60, 200, 49, 15);
			this.add(newPasswdLabel);

			newPasswordField = new JPasswordField();
			newPasswordField.setBounds(144, 200, 131, 21);
			this.add(newPasswordField);

			JButton updateButton = new JButton("修改");
			updateButton.setBounds(45, 256, 98, 23);
			this.add(updateButton);

			JButton resetButton = new JButton("重置");
			resetButton.setBounds(177, 256, 98, 23);
			this.add(resetButton);

			this.setLayout(null);
		}
	}
}


class WarehousePanel extends JPanel {
	private JTable table;
	private JTextField IDTextField;
	private JTextField locateTextField;
	private JTextField numTextField;

	public WarehousePanel() {
		super();
		table = new JTable();
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);

		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null }, }, new String[] { "仓库编号", "仓库容量", "仓库位置" }) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		JScrollPane leftScrollPane = new JScrollPane(table);
		leftScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		leftScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(leftScrollPane);

		JPanel rightPanel = new JPanel();
		this.add(rightPanel);
		rightPanel.setLayout(null);

		JLabel IDLabel = new JLabel("仓库编号");
		IDLabel.setBounds(62, 60, 48, 15);
		rightPanel.add(IDLabel);

		IDTextField = new JTextField();
		IDTextField.setBounds(141, 57, 137, 21);
		rightPanel.add(IDTextField);
		IDTextField.setColumns(10);

		JLabel numLabel = new JLabel("仓库容量");
		numLabel.setBounds(62, 116, 48, 15);
		rightPanel.add(numLabel);

		numTextField = new JTextField();
		numTextField.setBounds(141, 113, 137, 21);
		rightPanel.add(numTextField);
		numTextField.setColumns(10);

		JLabel locateLabel = new JLabel("仓库位置");
		locateLabel.setBounds(62, 172, 48, 15);
		rightPanel.add(locateLabel);

		locateTextField = new JTextField();
		locateTextField.setBounds(141, 169, 137, 21);
		rightPanel.add(locateTextField);
		locateTextField.setColumns(10);

		JButton updateButton = new JButton("修改");
		updateButton.setBounds(26, 254, 93, 23);
		rightPanel.add(updateButton);

		JButton addButton = new JButton("添加");
		addButton.setBounds(145, 254, 93, 23);
		rightPanel.add(addButton);

		JButton resetButton = new JButton("重置");
		resetButton.setBounds(264, 254, 93, 23);
		rightPanel.add(resetButton);
	}
}

class RecordPanel extends JPanel {
	private JTable table;
	private JTextField warehouseIDTextField;
	private JTextField goodsNameTextField;
	private JTextField batchTextField;
	private JTextField userTextField;
	public RecordPanel() {
		super();
		table = new JTable();
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		

		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null, null }, }, new String[] { "序号", "货物名",
				"货物批次", "仓库编号", "操作人", "类型", "日期" }

		) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		JScrollPane topScrollPane = new JScrollPane(table);
		topScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		topScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(topScrollPane);

		JPanel bottomPanel = new JPanel();
		this.add(bottomPanel);
		bottomPanel.setLayout(null);

		JLabel warehouseIDLabel = new JLabel("仓库编号");
		warehouseIDLabel.setBounds(46, 144, 48, 15);
		bottomPanel.add(warehouseIDLabel);

		warehouseIDTextField = new JTextField();
		warehouseIDTextField.setBounds(125, 141, 137, 21);
		bottomPanel.add(warehouseIDTextField);
		warehouseIDTextField.setColumns(10);

		JLabel goodsNameLabel = new JLabel("货物名");
		goodsNameLabel.setBounds(46, 38, 48, 15);
		bottomPanel.add(goodsNameLabel);

		goodsNameTextField = new JTextField();
		goodsNameTextField.setBounds(125, 35, 137, 21);
		bottomPanel.add(goodsNameTextField);
		goodsNameTextField.setColumns(10);
		
		JLabel batchLabel = new JLabel("货物批次");
		batchLabel.setBounds(46, 91, 54, 15);
		bottomPanel.add(batchLabel);
		
		batchTextField = new JTextField();
		batchTextField.setBounds(125, 88, 137, 21);
		bottomPanel.add(batchTextField);
		batchTextField.setColumns(10);
		
		JLabel userLabel = new JLabel("操作人");
		userLabel.setBounds(46, 197, 54, 15);
		bottomPanel.add(userLabel);
		
		userTextField = new JTextField();
		userTextField.setBounds(125, 194, 137, 21);
		bottomPanel.add(userTextField);
		userTextField.setColumns(10);
		
		JLabel typeLabel = new JLabel("类型");
		typeLabel.setBounds(372, 38, 54, 15);
		bottomPanel.add(typeLabel);
		
		JComboBox typeComboBox = new JComboBox();
		typeComboBox.setModel(new DefaultComboBoxModel(new String[] {"入库",
				"出库", "报损"}));
		typeComboBox.setBounds(433, 35, 50, 21);
		bottomPanel.add(typeComboBox);
		
		JLabel beginDateLabel = new JLabel("开始日期");
		beginDateLabel.setBounds(372, 91, 54, 15);
		bottomPanel.add(beginDateLabel);
		
		JComboBox beginYearComboBox = new JComboBox();
		beginYearComboBox.setModel(new DefaultComboBoxModel(new String[] {"2013"}));
		beginYearComboBox.setBounds(433, 88, 50, 21);
		bottomPanel.add(beginYearComboBox);
		
		JLabel beginYearLabel = new JLabel("年");
		beginYearLabel.setBounds(493, 91, 18, 15);
		bottomPanel.add(beginYearLabel);
		
		JComboBox beginMonthComboBox = new JComboBox();
		beginMonthComboBox.setModel(new DefaultComboBoxModel(new String[] {"12"}));
		beginMonthComboBox.setBounds(513, 88, 38, 21);
		bottomPanel.add(beginMonthComboBox);
		
		JLabel beginMonthLabel = new JLabel("月");
		beginMonthLabel.setBounds(561, 91, 18, 15);
		bottomPanel.add(beginMonthLabel);
		
		JComboBox beginDayComboBox = new JComboBox();
		beginDayComboBox.setModel(new DefaultComboBoxModel(new String[] {"30"}));
		beginDayComboBox.setBounds(580, 88, 38, 21);
		bottomPanel.add(beginDayComboBox);
		
		JLabel beginDayLabel = new JLabel("日");
		beginDayLabel.setBounds(628, 91, 18, 15);
		bottomPanel.add(beginDayLabel);
		
		JButton allButton = new JButton("全部");
		allButton.setBounds(372, 193, 93, 23);
		bottomPanel.add(allButton);
		
		JButton queryButton = new JButton("查询");
		queryButton.setBounds(493, 193, 93, 23);
		bottomPanel.add(queryButton);
		
		JButton resetButton = new JButton("重置");
		resetButton.setBounds(615, 193, 93, 23);
		bottomPanel.add(resetButton);
		
		JLabel endDateLabel = new JLabel("结束日期");
		endDateLabel.setBounds(372, 144, 54, 15);
		bottomPanel.add(endDateLabel);
		
		JComboBox endYearComboBox = new JComboBox();
		endYearComboBox.setBounds(433, 141, 50, 21);
		bottomPanel.add(endYearComboBox);
		
		JLabel endYearLabel = new JLabel("年");
		endYearLabel.setBounds(493, 144, 18, 15);
		bottomPanel.add(endYearLabel);
		
		JComboBox endMonthComboBox = new JComboBox();
		endMonthComboBox.setBounds(513, 141, 38, 21);
		bottomPanel.add(endMonthComboBox);
		
		JLabel endMonthLabel = new JLabel("月");
		endMonthLabel.setBounds(561, 144, 18, 15);
		bottomPanel.add(endMonthLabel);
		
		JComboBox endDayComboBox = new JComboBox();
		endDayComboBox.setBounds(580, 141, 38, 21);
		bottomPanel.add(endDayComboBox);
		
		JLabel endDayLabel = new JLabel("日");
		endDayLabel.setBounds(628, 144, 18, 15);
		bottomPanel.add(endDayLabel);

		
	}
}


class GoodsPanel extends JPanel {
	private JTable table;
	private JTextField nameTextField;
	private JTextField IDTextField;
	private JTextField batchTextField;
	private JTextField locateTextField;
	private JTextField numTextField;

	public GoodsPanel() {
		super();
		JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);

		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null }, }, new String[] { "货物编号", "货物名", "货物批次",
				"存储位置", "数量" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		JScrollPane leftScrollPane = new JScrollPane(table);
		leftScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		leftScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(leftScrollPane);

		JPanel rightPanel = new JPanel();
		this.add(rightPanel);
		rightPanel.setLayout(null);

		JLabel IDLabel = new JLabel("货物编号");
		IDLabel.setBounds(30, 33, 48, 15);
		rightPanel.add(IDLabel);

		IDTextField = new JTextField();
		IDTextField.setBounds(118, 30, 139, 21);
		rightPanel.add(IDTextField);
		IDTextField.setColumns(10);

		JLabel nameLabel = new JLabel("货物名");
		nameLabel.setBounds(30, 94, 36, 15);
		rightPanel.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setBounds(118, 91, 139, 21);
		rightPanel.add(nameTextField);
		nameTextField.setColumns(10);

		JLabel batchLabel = new JLabel("货物批次");
		batchLabel.setBounds(30, 155, 48, 15);
		rightPanel.add(batchLabel);

		batchTextField = new JTextField();
		batchTextField.setBounds(118, 152, 139, 21);
		rightPanel.add(batchTextField);
		batchTextField.setColumns(10);

		JLabel locateLabel = new JLabel("存储位置");
		locateLabel.setBounds(30, 216, 48, 15);
		rightPanel.add(locateLabel);

		locateTextField = new JTextField();
		locateTextField.setBounds(118, 213, 139, 21);
		rightPanel.add(locateTextField);
		locateTextField.setColumns(10);

		JLabel numLabel = new JLabel("数量");
		numLabel.setBounds(30, 277, 24, 15);
		rightPanel.add(numLabel);

		numTextField = new JTextField();
		numTextField.setBounds(118, 274, 139, 21);
		rightPanel.add(numTextField);
		numTextField.setColumns(10);

		JLabel typeLabel = new JLabel("操作类型");
		typeLabel.setBounds(30, 338, 48, 15);
		rightPanel.add(typeLabel);

		JComboBox typeComboBox = new JComboBox();
		typeComboBox.setBounds(118, 335, 50, 21);
		typeComboBox.setModel(new DefaultComboBoxModel(new String[] { "入库",
				"出库", "报损" }));
		rightPanel.add(typeComboBox);

		JButton updateButton = new JButton("修改");
		updateButton.setBounds(26, 403, 93, 23);
		rightPanel.add(updateButton);

		JButton resetButton = new JButton("重置");
		resetButton.setBounds(264, 403, 93, 23);
		rightPanel.add(resetButton);

		JButton addButton = new JButton("添加");
		addButton.setBounds(145, 403, 93, 23);
		rightPanel.add(addButton);
	}
}
