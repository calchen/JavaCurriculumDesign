package com.cky.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import com.cky.encryption.MD5;
import com.cky.main.*;


/**
 * 文件名： MainFrame.java 
 * 作 者： 陈恺垣 E-mail:chenkaiyuan1993@gmail.com 
 * 创建时间：2013-6-8 上午11:19:13 
 * 最后修改：2013-06-25 上午09:46:00
 * 类说明 ：主界面，包含另外四个JPanel实现货物管理、仓库管理、记录查询以及员工管理
 */
public class MainFrame extends JFrame {
	private JPanel contentPane;
	private int width = 800;
	private int height = 600;
	private Main main;

	public MainFrame(User user) {
		main = new Main(this, user);
		// 设置默认风格
		try {
			// Windows风格
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		// 设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				LoginFrame.class.getResource("/com/cky/res/logo.jpg")));
		// 设置标题
		setTitle("超市仓库管理系统");
		// 设置可见
		setVisible(true);
		// 设置默认关闭
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 菜单栏
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu1 = new JMenu("文件");
		menuBar.add(fileMenu1);

		JMenu setMenu2 = new JMenu("设置");
		fileMenu1.add(setMenu2);

		JMenuItem DBSetMenuItem3 = new JMenuItem("数据库设置");
		setMenu2.add(DBSetMenuItem3);
		DBSetMenuItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new DBSetFrame();
			}
		});

		JMenuItem exitMenuItem2 = new JMenuItem("退出");
		fileMenu1.add(exitMenuItem2);
		exitMenuItem2.addActionListener(new ActionListener() {
			// 退出操作
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu helpMenu1 = new JMenu("帮助");
		menuBar.add(helpMenu1);

		JMenuItem aboutMenuItem2 = new JMenuItem("关于");
		helpMenu1.add(aboutMenuItem2);
		aboutMenuItem2.addActionListener(new ActionListener() {
			// 关于操作
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"姓名：陈恺垣  学号：20112308039 专业：计算机科学与技术", "关于",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// 底层panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// 标签页
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setToolTipText("");
		contentPane.add(jTabbedPane, BorderLayout.CENTER);

		// 四个JPanel分别实现四个功能
		JPanel goodsPanel = new GoodsPanel(main);
		jTabbedPane.addTab("货物管理", null, goodsPanel, "货物管理");
		jTabbedPane.setEnabledAt(0, true);
		goodsPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel warehousePanel = new WarehousePanel(main);
		jTabbedPane.addTab("仓库管理", null, warehousePanel, "仓库管理");
		jTabbedPane.setEnabledAt(1, true);
		warehousePanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel recordPanel = new RecordPanel(main);
		jTabbedPane.addTab("出入库记录", null, recordPanel, "出入库记录");
		jTabbedPane.setEnabledAt(2, true);
		recordPanel.setLayout(new GridLayout(2, 1, 0, 0));

		// JPanel userPanel = new UserPanel(new User("123","456","4658","员工"));
		JPanel userPanel = new UserPanel(user, main);
		jTabbedPane.addTab("用户管理", null, userPanel, "用户管理");
		jTabbedPane.setEnabledAt(3, true);
	}

}

// 货物管理Panel
class GoodsPanel extends JPanel {
	private JTable table;
	private JTextField nameTextField;
	private JTextField IDTextField;
	private JTextField batchTextField;
	private JTextField locateTextField;
	private JTextField numTextField;
	private JComboBox typeComboBox;

	private Main main;

	public GoodsPanel(final Main main) {
		super();
		this.main = main;
		JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		table = new JTable() {
			// 不可编辑
			@Override
			public boolean isCellEditable(int paramInt1, int paramInt2) {
				return false;
			}

		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(getTableModel());
		table.addMouseListener(new MouseAdapter() {
			// 点击表格同步显示
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row > -1) {
					IDTextField.setText(String.valueOf(table.getValueAt(row, 0)));
					nameTextField.setText(String.valueOf(table.getValueAt(row,
							1)));
					batchTextField.setText(String.valueOf(table.getValueAt(row,
							2)));
					locateTextField.setText(String.valueOf(table.getValueAt(
							row, 3)));
					numTextField.setText(String.valueOf(table
							.getValueAt(row, 4)));
				}
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

		typeComboBox = new JComboBox();
		typeComboBox.setBounds(118, 335, 50, 21);
		String[] type = new String[] { "入库", "出库", "报损" };
		typeComboBox.setModel(new DefaultComboBoxModel(type));
		rightPanel.add(typeComboBox);

		JButton updateButton = new JButton("操作");
		updateButton.setBounds(26, 403, 93, 23);
		rightPanel.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			// 入库、出库、报损操作
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ID = IDTextField.getText();
				String batch = batchTextField.getText();
				String warehouse = locateTextField.getText();
				String name = nameTextField.getText();
				int num = Integer.parseInt(0 + numTextField.getText());
				String type = typeComboBox.getSelectedItem().toString();

				Goods goods = new Goods(ID, batch, warehouse, name, num);
				if (main.updateGoodslist(goods, type)) {
					table.setModel(getTableModel());

					IDTextField.setText("");
					batchTextField.setText("");
					locateTextField.setText("");
					nameTextField.setText("");
					numTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的货物编号、货物名、货物批次、存储位置、数量以及操作类型！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		JButton deleteButton = new JButton("删除");
		deleteButton.setBounds(264, 403, 93, 23);
		rightPanel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			// 删除操作
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ID = IDTextField.getText();
				String batch = batchTextField.getText();
				String warehouse = locateTextField.getText();
				String name = nameTextField.getText();
				int num = Integer.parseInt(0 + numTextField.getText());
				String type = typeComboBox.getSelectedItem().toString();

				Goods goods = new Goods(ID, batch, warehouse, name, num);
				if (main.deleteGoodslist(goods, type)) {
					table.setModel(getTableModel());

					IDTextField.setText("");
					batchTextField.setText("");
					locateTextField.setText("");
					nameTextField.setText("");
					numTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的货物编号、货物名、货物批次、存储位置、数量以及操作类型！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton addButton = new JButton("添加");
		addButton.setBounds(145, 403, 93, 23);
		rightPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			// 添加操作
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = IDTextField.getText();
				String batch = batchTextField.getText();
				String warehouse = locateTextField.getText();
				String name = nameTextField.getText();
				int num = Integer.parseInt(0 + numTextField.getText());
				String type = typeComboBox.getSelectedItem().toString();

				Goods goods = new Goods(ID, batch, warehouse, name, num);
				if (main.addGoodslist(goods, type)) {
					table.setModel(getTableModel());

					IDTextField.setText("");
					batchTextField.setText("");
					locateTextField.setText("");
					nameTextField.setText("");
					numTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的货物编号、货物名、货物批次、存储位置、数量以及操作类型！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// 获取JTable的model
	private DefaultTableModel getTableModel() {
		String[] str = new String[] { "货物编号", "货物名", "货物批次", "存储位置", "数量" };
		DefaultTableModel model = new DefaultTableModel(null, str);
		if (main.getGoodslist().size() == 0) {
			model.addRow(new String[] { null, null, null, null, null });
		} else {
			for (Goods i : main.getGoodslist()) {
				model.addRow(new String[] { i.getID(), i.getName(),
						i.getBatch(), i.getWarehouse(),
						String.valueOf(i.getNum()) });
			}
		}

		return model;
	}
}

// 仓库管理Panel
class WarehousePanel extends JPanel {
	private JTable table;
	private JTextField IDTextField;
	private JTextField locateTextField;
	private JTextField numTextField;

	private Main main;

	public WarehousePanel(final Main main) {
		super();
		this.main = main;

		table = new JTable() {
			// 不可编辑
			@Override
			public boolean isCellEditable(int paramInt1, int paramInt2) {
				return false;
			}

		};
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(getTableModel());
		table.addMouseListener(new MouseAdapter() {
			// 点击表格同步显示
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				int row = table.getSelectedRow();
				if (row > -1) {
					IDTextField.setText(String.valueOf(table.getValueAt(row, 0)));
					numTextField.setText(String.valueOf(table
							.getValueAt(row, 1)));
					locateTextField.setText(String.valueOf(table.getValueAt(
							row, 2)));
				}
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
		updateButton.addActionListener(new ActionListener() {
			// 修改操作
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = IDTextField.getText();
				String locate = locateTextField.getText();
				int num = Integer.parseInt(numTextField.getText());

				Warehouse warehouse = new Warehouse(id, num, locate);
				if (main.updateWarehouselist(warehouse)) {
					table.setModel(getTableModel());

					IDTextField.setText("");
					locateTextField.setText("");
					numTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的仓库编号、仓库容量以及仓库位置！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton addButton = new JButton("添加");
		addButton.setBounds(145, 254, 93, 23);
		rightPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			// 添加操作
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = IDTextField.getText();
				String locate = locateTextField.getText();
				int num = Integer.parseInt(numTextField.getText());

				Warehouse warehouse = new Warehouse(id, num, locate);
				if (main.addWarehouselist(warehouse)) {
					table.setModel(getTableModel());

					IDTextField.setText("");
					locateTextField.setText("");
					numTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的仓库编号、仓库容量以及仓库位置！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton deleteButton = new JButton("删除");
		deleteButton.setBounds(264, 254, 93, 23);
		rightPanel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			// 删除操作
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = IDTextField.getText();
				String locate = locateTextField.getText();
				int num = Integer.parseInt(numTextField.getText());

				Warehouse warehouse = new Warehouse(id, num, locate);
				if (main.deleteWarehouselist(warehouse)) {
					table.setModel(getTableModel());

					IDTextField.setText("");
					locateTextField.setText("");
					numTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的仓库编号、仓库容量以及仓库位置！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// 获取JTable的model
	private DefaultTableModel getTableModel() {
		DefaultTableModel model = new DefaultTableModel(null, new String[] {
				"仓库编号", "仓库容量", "仓库位置" });
		if (main.getWarehouselist().size() == 0) {
			model.addRow(new String[] { null, null, null });
		} else {
			for (Warehouse i : main.getWarehouselist()) {
				model.addRow(new String[] { i.getID(),
						String.valueOf(i.getSize()), i.getLocation() });
			}
		}

		return model;
	}
}

// 出入库记录Panel
class RecordPanel extends JPanel {
	private JTable table;
	private JTextField warehouseIDTextField;
	private JTextField goodsIDTextField;
	private JTextField batchTextField;
	private JTextField userTextField;
	private JComboBox typeComboBox;
	private JComboBox beginYearComboBox;
	private JComboBox beginMonthComboBox;
	private JComboBox beginDayComboBox;
	private JComboBox endYearComboBox;
	private JComboBox endMonthComboBox;
	private JComboBox endDayComboBox;

	private Main main;

	public RecordPanel(final Main main) {
		super();
		this.main = main;
		table = new JTable() {
			// 不可编辑
			@Override
			public boolean isCellEditable(int paramInt1, int paramInt2) {
				return false;
			}

		};
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(getTableModel());

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

		JLabel goodsNameLabel = new JLabel("货物编号");
		goodsNameLabel.setBounds(46, 38, 48, 15);
		bottomPanel.add(goodsNameLabel);

		goodsIDTextField = new JTextField();
		goodsIDTextField.setBounds(125, 35, 137, 21);
		bottomPanel.add(goodsIDTextField);
		goodsIDTextField.setColumns(10);

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

		JLabel typeLabel = new JLabel("操作类型");
		typeLabel.setBounds(372, 38, 54, 15);
		bottomPanel.add(typeLabel);

		typeComboBox = new JComboBox();
		typeComboBox.setModel(new DefaultComboBoxModel(new String[] { "入库",
				"出库", "报损" }));
		typeComboBox.setBounds(433, 35, 50, 21);
		bottomPanel.add(typeComboBox);

		JLabel beginDateLabel = new JLabel("开始日期");
		beginDateLabel.setBounds(372, 91, 54, 15);
		bottomPanel.add(beginDateLabel);

		beginYearComboBox = new JComboBox();
		beginYearComboBox.setModel(dateJComboBoxModel()[0]);
		beginYearComboBox.setBounds(433, 88, 50, 21);
		bottomPanel.add(beginYearComboBox);

		JLabel beginYearLabel = new JLabel("年");
		beginYearLabel.setBounds(493, 91, 18, 15);
		bottomPanel.add(beginYearLabel);

		beginMonthComboBox = new JComboBox();
		beginMonthComboBox.setModel(dateJComboBoxModel()[1]);
		beginMonthComboBox.setBounds(513, 88, 38, 21);
		bottomPanel.add(beginMonthComboBox);

		JLabel beginMonthLabel = new JLabel("月");
		beginMonthLabel.setBounds(561, 91, 18, 15);
		bottomPanel.add(beginMonthLabel);

		beginDayComboBox = new JComboBox();
		beginDayComboBox.setModel(dateJComboBoxModel()[2]);
		beginDayComboBox.setBounds(580, 88, 38, 21);
		bottomPanel.add(beginDayComboBox);

		JLabel beginDayLabel = new JLabel("日");
		beginDayLabel.setBounds(628, 91, 18, 15);
		bottomPanel.add(beginDayLabel);

		// 使用监听器保证日期正确显示
		beginYearComboBox.addActionListener(dateJComboBoxActionListener(
				beginYearComboBox, beginMonthComboBox, beginDayComboBox));

		beginMonthComboBox.addActionListener(dateJComboBoxActionListener(
				beginYearComboBox, beginMonthComboBox, beginDayComboBox));

		JLabel endDateLabel = new JLabel("结束日期");
		endDateLabel.setBounds(372, 144, 54, 15);
		bottomPanel.add(endDateLabel);

		endYearComboBox = new JComboBox();
		endYearComboBox.setModel(dateJComboBoxModel()[0]);
		endYearComboBox.setBounds(433, 141, 50, 21);
		bottomPanel.add(endYearComboBox);

		JLabel endYearLabel = new JLabel("年");
		endYearLabel.setBounds(493, 144, 18, 15);
		bottomPanel.add(endYearLabel);

		endMonthComboBox = new JComboBox();
		endMonthComboBox.setModel(dateJComboBoxModel()[1]);
		endMonthComboBox.setBounds(513, 141, 38, 21);
		bottomPanel.add(endMonthComboBox);

		JLabel endMonthLabel = new JLabel("月");
		endMonthLabel.setBounds(561, 144, 18, 15);
		bottomPanel.add(endMonthLabel);

		endDayComboBox = new JComboBox();
		endDayComboBox.setModel(dateJComboBoxModel()[2]);
		endDayComboBox.setBounds(580, 141, 38, 21);
		bottomPanel.add(endDayComboBox);

		JLabel endDayLabel = new JLabel("日");
		endDayLabel.setBounds(628, 144, 18, 15);
		bottomPanel.add(endDayLabel);

		// 使用监听器保证日期正确显示
		endYearComboBox.addActionListener(dateJComboBoxActionListener(
				endYearComboBox, endMonthComboBox, endDayComboBox));
		endMonthComboBox.addActionListener(dateJComboBoxActionListener(
				endYearComboBox, endMonthComboBox, endDayComboBox));

		JButton allButton = new JButton("全部");
		allButton.setBounds(372, 193, 93, 23);
		bottomPanel.add(allButton);
		allButton.addActionListener(new ActionListener() {
			// 显示全部记录
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.allRecord();
				table.setModel(getTableModel());

				goodsIDTextField.setText("");
				batchTextField.setText("");
				warehouseIDTextField.setText("");
				userTextField.setText("");
				typeComboBox.setSelectedIndex(0);
				beginYearComboBox.setSelectedIndex(0);
				beginMonthComboBox.setSelectedIndex(0);
				beginDayComboBox.setSelectedIndex(0);
				endYearComboBox.setSelectedIndex(0);
				endMonthComboBox.setSelectedIndex(0);
				endDayComboBox.setSelectedIndex(0);
			}
		});

		JButton queryButton = new JButton("查询");
		queryButton.setBounds(493, 193, 93, 23);
		bottomPanel.add(queryButton);
		queryButton.addActionListener(new ActionListener() {
			// 按条件查询
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] str = new String[7];
				str[0] = goodsIDTextField.getText();
				str[1] = batchTextField.getText();
				str[2] = warehouseIDTextField.getText();
				str[3] = userTextField.getText();
				int row = typeComboBox.getSelectedIndex();
				if (row == 0) {
					str[4] = "入库";
				} else if (row == 1) {
					str[4] = "出库";
				} else {
					str[4] = "报损";
				}
				Calendar beginCalendar = dateJComboBoxToCalendar(
						beginYearComboBox, beginMonthComboBox, beginDayComboBox);
				String beginDate = "" + beginCalendar.get(Calendar.YEAR) + "-"
						+ (beginCalendar.get(Calendar.MONTH) + 1) + "-"
						+ beginCalendar.get(Calendar.DAY_OF_MONTH)
						+ " 00:00:00";
				str[5] = beginDate;
				Calendar endCalendar = dateJComboBoxToCalendar(
						beginYearComboBox, beginMonthComboBox, beginDayComboBox);
				String endDate = "" + endCalendar.get(Calendar.YEAR) + "-"
						+ (endCalendar.get(Calendar.MONTH) + 1) + "-"
						+ endCalendar.get(Calendar.DAY_OF_MONTH) + " 23:59:59";
				str[6] = endDate;
				if (main.queryRecordlist(str)) {
					table.setModel(getTableModel());
				} else {
					JOptionPane.showMessageDialog(null,
							"请输入正确的货物编号、货物名、货物批次、仓库编号、操作人、类型以及时间！", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton resetButton = new JButton("重置");
		resetButton.setBounds(615, 193, 93, 23);
		bottomPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener() {
			// 重置操作
			@Override
			public void actionPerformed(ActionEvent arg0) {
				goodsIDTextField.setText("");
				batchTextField.setText("");
				warehouseIDTextField.setText("");
				userTextField.setText("");
				typeComboBox.setSelectedIndex(0);
				beginYearComboBox.setSelectedIndex(0);
				beginMonthComboBox.setSelectedIndex(0);
				beginDayComboBox.setSelectedIndex(0);
				endYearComboBox.setSelectedIndex(0);
				endMonthComboBox.setSelectedIndex(0);
				endDayComboBox.setSelectedIndex(0);
			}
		});

	}

	// 日期下拉列表监听器
	private ActionListener dateJComboBoxActionListener(
			final JComboBox yearJComboBox, final JComboBox monthJComboBox,
			final JComboBox dayJComboBox) {
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[][] arr = new int[][] {
						{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
						{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
				int flag = 1;
				int year = yearJComboBox.getSelectedIndex() + 2013;
				int month = monthJComboBox.getSelectedIndex();

				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					flag = 0;
				}
				String[] str = new String[arr[flag][month]];
				for (int i = 0; i < arr[flag][month]; i++) {
					str[i] = i + 1 + "";
				}

				dayJComboBox.setModel(new DefaultComboBoxModel(str));
			}
		};
		return actionListener;
	}

	// 日期下拉列表的Model
	private DefaultComboBoxModel[] dateJComboBoxModel() {
		DefaultComboBoxModel[] boxModel = new DefaultComboBoxModel[3];

		String[] JComStrings = new String[20];
		for (int i = 0; i < JComStrings.length; i++) {
			JComStrings[i] = 2013 + i + "";
		}
		boxModel[0] = new DefaultComboBoxModel(JComStrings);

		boxModel[1] = new DefaultComboBoxModel(new String[] { "1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12" });

		boxModel[2] = new DefaultComboBoxModel(new String[] { "1", "2", "3",
				"4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
				"25", "26", "27", "28", "29", "30", "31" });

		return boxModel;
	}

	// 将时间的JComJComboBox转成Calendar
	private Calendar dateJComboBoxToCalendar(JComboBox yearJComboBox,
			JComboBox monthJComboBox, JComboBox dayJComboBox) {
		Calendar date = Calendar.getInstance();
		int year = Integer.parseInt(yearJComboBox.getSelectedItem().toString());
		int month = Integer.parseInt(monthJComboBox.getSelectedItem()
				.toString());
		int day = Integer.parseInt(dayJComboBox.getSelectedItem().toString());
		date.set(year, month - 1, day);
		return date;
	}

	// 获取JTable的model
	private DefaultTableModel getTableModel() {
		DefaultTableModel model = new DefaultTableModel(null, new String[] {
				"序号", "货物编号", "货物批次", "仓库编号", "操作人", "类型", "数目", "日期" });
		if (main.getRecordlist().size() == 0) {
			model.addRow(new String[] { null, null, null, null, null, null,
					null, null });
		} else {
			for (String[] i : main.getRecordlist()) {
				model.addRow(new String[] { i[0], i[1], i[2], i[3], i[4], i[5],
						i[6], i[7] });
			}
		}
		return model;
	}
}

// 用户管理Panel
class UserPanel extends JPanel {
	private JTable table;
	private JPasswordField passwdTextField;
	private JTextField IDTextField;
	private JTextField nameTextField;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JComboBox typeComboBox;
	private Main main;

	public UserPanel(final User user, final Main main) {
		super();
		this.main = main;
		// 员工分成两种类型分别有不同的界面
		if (user.getType().equals("主管")) {
			// 主管管理界面
			table = new JTable() {
				// 不可编辑
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table.setFillsViewportHeight(true);
			table.getTableHeader().setReorderingAllowed(false);
			table.setModel(getTableModel());
			table.addMouseListener(new MouseAdapter() {
				// 点击表格同步显示
				@Override
				public void mouseClicked(MouseEvent paramMouseEvent) {
					int row = table.getSelectedRow();
					if (row > -1) {
						IDTextField.setText(String.valueOf(table.getValueAt(
								row, 0)));
						nameTextField.setText(String.valueOf(table.getValueAt(
								row, 1)));
						String type = String.valueOf(table.getValueAt(row, 2));
						if (type.equals("员工")) {
							typeComboBox.setSelectedIndex(0);
						} else if (type.equals("主管")) {
							typeComboBox.setSelectedIndex(1);
						}
					}
				}
			});

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

			typeComboBox = new JComboBox();
			typeComboBox.setModel(new DefaultComboBoxModel(new String[] { "员工",
					"主管" }));
			typeComboBox.setBounds(125, 197, 50, 21);
			leftPanel.add(typeComboBox);

			JButton addButton = new JButton("添加");
			addButton.setBounds(26, 278, 93, 23);
			leftPanel.add(addButton);
			addButton.addActionListener(new ActionListener() {
				// 添加操作
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = IDTextField.getText();
					String name = nameTextField.getText();
					String passwd = String.valueOf(passwdTextField
							.getPassword());
					passwd = new MD5().encrypt(passwd);
					String type = typeComboBox.getSelectedItem().toString();

					User user = new User(id, name, passwd, type);
					if (main.addUserlist(user)) {
						table.setModel(getTableModel());

						IDTextField.setText("");
						nameTextField.setText("");
						passwdTextField.setText("");
						typeComboBox.setSelectedIndex(0);
					} else {
						JOptionPane.showMessageDialog(null, "请输入正确用户名、姓名、密码！",
								"警告", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			JButton deleteButton = new JButton("修改");
			deleteButton.setBounds(145, 278, 93, 23);
			leftPanel.add(deleteButton);
			deleteButton.addActionListener(new ActionListener() {
				// 修改操作
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = IDTextField.getText();
					String name = nameTextField.getText();
					String passwd = String.valueOf(passwdTextField
							.getPassword());
					passwd = new MD5().encrypt(passwd);
					String type = typeComboBox.getSelectedItem().toString();

					User user = new User(id, name, passwd, type);
					if (main.updateUserlist(user)) {
						table.setModel(getTableModel());

						IDTextField.setText("");
						nameTextField.setText("");
						passwdTextField.setText("");
						typeComboBox.setSelectedIndex(0);
					} else {
						JOptionPane.showMessageDialog(null, "请输入正确用户名、姓名、密码！",
								"警告", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			JButton resetButton = new JButton("删除");
			resetButton.setBounds(264, 278, 93, 23);
			leftPanel.add(resetButton);
			resetButton.addActionListener(new ActionListener() {
				// 删除操作
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = IDTextField.getText();
					String name = nameTextField.getText();
					String passwd = String.valueOf(passwdTextField
							.getPassword());
					passwd = new MD5().encrypt(passwd);
					String type = typeComboBox.getSelectedItem().toString();

					User user = new User(id, name, passwd, type);
					if (main.deleteUserlist(user)) {
						table.setModel(getTableModel());

						IDTextField.setText("");
						nameTextField.setText("");
						passwdTextField.setText("");
						typeComboBox.setSelectedIndex(0);
					} else {
						JOptionPane.showMessageDialog(null, "请输入正确用户名、姓名、密码！",
								"警告", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			this.setLayout(new GridLayout(1, 0, 0, 0));
		} else if (user.getType().equals("员工")) {
			// 员工管理界面
			this.setLayout(null);
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
			updateButton.addActionListener(new ActionListener() {
				// 密码修改操作
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String oldPasswd = String.valueOf(oldPasswordField
							.getPassword());
					MD5 md5 = new MD5();
					oldPasswd = md5.encrypt(oldPasswd);
					if (oldPasswd.equals(user.getPasswd())) {
						String newPasswd = String.valueOf(newPasswordField
								.getPassword());
						newPasswd = md5.encrypt(newPasswd);
						User newUser = new User(user.getID(), user.getName(),
								newPasswd, user.getType());
						if (main.updateUserlist(newUser)) {
							table.setModel(getTableModel());

							oldPasswordField.setText("");
							newPasswordField.setText("");
						} else {
							JOptionPane.showMessageDialog(null,
									"请输入正确的当前密码与新密码！", "警告",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});

			JButton resetButton = new JButton("重置");
			resetButton.setBounds(177, 256, 98, 23);
			this.add(resetButton);
			resetButton.addActionListener(new ActionListener() {
				// 重置操作
				@Override
				public void actionPerformed(ActionEvent arg0) {
					oldPasswordField.setText("");
					newPasswordField.setText("");
				}
			});

		}
	}

	// 获取JTable的model
	private DefaultTableModel getTableModel() {
		DefaultTableModel model = new DefaultTableModel(null, new String[] {
				"用户名", "姓名", "类型" });
		if (main.getWarehouselist().size() == 0) {
			model.addRow(new String[] { null, null, null });
		} else {
			for (User i : main.getUserlist()) {
				model.addRow(new String[] { i.getID(), i.getName(), i.getType() });
			}
		}

		return model;
	}
}