package com.window.swing;

import javax.swing.JFrame;

public class Test1 {
	public static void main(String[] ar) {
		JFrame jf = new JFrame("테스트 윈도우");
		jf.setSize(400, 300);
		jf.setLocation(500, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
