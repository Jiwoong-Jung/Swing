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
	String column[] = { "주소", "홈페이지", "전화번호","내용" };
	PanelEx01() throws IOException {
		jf = new JFrame("문화 정보");
		JPanel jp = new JPanel();
		insertTable();
		JTable jt = new JTable(data, column);
		JScrollPane sp = new JScrollPane(jt);
		tf1 = new JTextField(7);
		jb1 = new JButton("검색");
		jb1.addActionListener(this);
		jb2 = new JButton("버튼2");
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
			System.out.println("검색 중.......");
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
		System.out.println("===============시작");
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

		// 가장 큰 JSONObject를 가져옵니다.
		JSONObject jObject = new JSONObject(data1);
		// 배열을 가져옵니다.
		JSONArray jArray = jObject.getJSONArray("DATA");

		
		// 배열의 모든 아이템을 출력합니다.
		for (int i = 0; i < jArray.length(); i++) {
			String url = "";
			String title = "";
			String draft = "";
			String fac_desc = "";
			JSONObject obj = jArray.getJSONObject(i);
//			String title = obj.getString("addr");
			if(obj.isNull("addr")){
				title = "없음";
			}else {
				title = obj.getString("addr");
			}
//			String url = obj.getString("homepage");
			if(obj.isNull("homepage")){
				url = "없음";
			}else {
				url = obj.getString("homepage");
			}
//			String draft = obj.getString("phne");
			if(obj.isNull("phne")){
				draft = "없음";
			}else {
				draft = obj.getString("phne");
			}
			if(obj.isNull("fac_desc")){
				fac_desc = "없음";
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
