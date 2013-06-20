package com.design.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class WpsklJTabbedPane {

	JFrame jf;
	JTabbedPane jtp;
	Dimension screena;
	Dimension screenb;

	public WpsklJTabbedPane() {

		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1, 4));
		jf = new JFrame("JScrollPane");
		jf.setVisible(true);
		jf.setSize(100, 200);
		screenb = jf.getSize();
		int a = screenb.width;
		int b = screenb.height;
		screena = Toolkit.getDefaultToolkit().getScreenSize();
		int aa = screena.width;
		int bb = screena.height;
		jf.setLocation((aa - a) / 2, (bb - b) / 2);
		
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		JPanel jp4 = new JPanel();
		jtp = new JTabbedPane();
		jp1.setBackground(Color.yellow);
		jp2.setBackground(Color.black);
		jp3.setBackground(Color.green);
		jp4.setBackground(Color.blue);
		jtp.add(jp1, "one");
		jtp.add(jp2, "two");
		jtp.add(jp3, "three");
		jtp.add(jp4, "four");
		jp.add(jtp);
		jf.getContentPane().add(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new WpsklJTabbedPane();
	}
}
