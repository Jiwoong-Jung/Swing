package com.window.swing;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

class PanelEx01 implements ActionListener {
	MyDialog dialog;
	JButton jb1;
	JButton jb2;
	JTextField tf1;
	JFrame jf;
	String data[][] = new String[816][4];
	String data_search[][] = new String[816][4];
	String column[] = { "�ּ�", "Ȩ������", "��ȭ��ȣ","����" };
	PanelEx01() throws IOException {
		jf = new JFrame("��ȭ ����");
		JPanel jp = new JPanel();
		insertTable();
		JTable jt = new JTable(data, column);
		JScrollPane sp = new JScrollPane(jt);
		tf1 = new JTextField(7);
		jb1 = new JButton("�˻�");
		jb1.addActionListener(this);
		jb2 = new JButton("��ư2");
		jb2.addActionListener(this);
		jp.setLayout(new FlowLayout());
		jp.add(tf1);
		jp.add(jb1);
		jp.add(jb2);
		JPanel jp2 = new JPanel(new BorderLayout());
		jp2.add(jp, BorderLayout.SOUTH);
		jp2.add(sp, BorderLayout.CENTER);
		jf.setLocation(500, 500);
		jf.setSize(400, 300);
		jf.add(jp2);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb1) {
			System.out.println("�˻� ��.......");
			int j  = 0;
			for (int i=0; i < data.length; i++) {
				if (data[i][0].contains(tf1.getText())) {
					data_search[j][0] = data[i][0];
					data_search[j][1] = data[i][1];
					data_search[j][2] = data[i][2];
					data_search[j][3] = data[i][3];
					System.out.println(data_search[j][0]);
				}
			}
		} else if (e.getSource() == jb2) {
			System.out.println("jb2");
			dialog = new MyDialog(this.jf, "Test Dialog");
			dialog.setVisible(true);
		}
		
		
	}
	
	public void insertTable() throws IOException {
		System.out.println("===============����");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/culture.json"), "UTF-8"));

		char[] cbuf = new char[2048];
		int readCharNo;
		String data1 = "";
		int k = 0;
		while ((readCharNo = reader.read(cbuf)) != -1) {
			data1 = data1 + new String(cbuf, 0, readCharNo);
			++k;
//			System.out.println("--------------"+k);
		}
		reader.close();

		// ���� ū JSONObject�� �����ɴϴ�.
		JSONObject jObject = new JSONObject(data1);
		// �迭�� �����ɴϴ�.
		JSONArray jArray = jObject.getJSONArray("DATA");

		
		// �迭�� ��� �������� ����մϴ�.
		for (int i = 0; i < jArray.length(); i++) {
			String url = "";
			String title = "";
			String draft = "";
			String fac_desc = "";
			JSONObject obj = jArray.getJSONObject(i);
//			String title = obj.getString("addr");
			if(obj.isNull("addr")){
				title = "����";
			}else {
				title = obj.getString("addr");
			}
//			String url = obj.getString("homepage");
			if(obj.isNull("homepage")){
				url = "����";
			}else {
				url = obj.getString("homepage");
			}
//			String draft = obj.getString("phne");
			if(obj.isNull("phne")){
				draft = "����";
			}else {
				draft = obj.getString("phne");
			}
			if(obj.isNull("fac_desc")){
				fac_desc = "����";
			}else {
				fac_desc = obj.getString("fac_desc");
			}
			System.out.println("title(" + i + "): " + title);
			System.out.println("url(" + i + "): " + url);
			System.out.println("draft(" + i + "): " + draft);
			System.out.println("fac_desc(" + i + "): " + fac_desc);
			System.out.println();
			data[i][0] = title;
			data[i][1] = url;
			data[i][2] = draft;
			data[i][3] = fac_desc;
		}

	}
}

class MyDialog extends JDialog {
	JButton okButton = new JButton("OK");

	public MyDialog(JFrame frame, String title) {
		super(frame, title);
		this.setLayout(new FlowLayout());
		this.add(okButton);
		this.setSize(200, 100);

	}
	
}

public class Test2 {
	public static void main(String[] ar) throws IOException {
		new PanelEx01();
	}
}
