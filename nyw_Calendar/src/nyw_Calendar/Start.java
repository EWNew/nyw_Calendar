package nyw_Calendar;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// �����ͻ�������
		NetClient net = NetClient.getInstance();
		if (net.isConnect)
			System.out.println("���������������");
		else {
			System.out.println("���������������");
			JOptionPane.showMessageDialog(null, "���������������,���ӱ��ض�ȡ����");
		}
		// ��������
		Timer timer = new Timer();
		timer.schedule(new BlingTask(), 0, 1000);

		// ��������
		MainWindow main = new MainWindow();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// System.out.println("end");
	}

}
