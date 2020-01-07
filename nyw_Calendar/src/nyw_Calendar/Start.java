package nyw_Calendar;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 启动客户端网络
		NetClient net = NetClient.getInstance();
		if (net.isConnect)
			System.out.println("网络服务器已连接");
		else {
			System.out.println("无网络服务器连接");
			JOptionPane.showMessageDialog(null, "无网络服务器连接,将从本地读取数据");
		}
		// 启动闹钟
		Timer timer = new Timer();
		timer.schedule(new BlingTask(), 0, 1000);

		// 建立界面
		MainWindow main = new MainWindow();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// System.out.println("end");
	}

}
