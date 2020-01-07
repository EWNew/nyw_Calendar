package nyw_Calendar;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;

public class MidPanel extends JPanel {
	private JLabel timeLabel;
	Date date;
	JLabel day[] = new JLabel[42];
	private JPanel secondPanel = new JPanel();
	private JPanel firstPanel = new JPanel();

	MidPanel() {
		this.setLayout(new BorderLayout());
		this.add(firstPanel, BorderLayout.NORTH);
		this.add(secondPanel, BorderLayout.CENTER);
		// FirstPanel
		SimpleDateFormat ft = new SimpleDateFormat("yyyy��" + "MM��");
		date = new Date();
		timeLabel = new JLabel(ft.format(date));
		firstPanel.add(timeLabel);
		JButton upMonth[] = new JButton[2];
		upMonth[0] = new JButton("��һ��");
		upMonth[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				date.setMonth(date.getMonth() - 1);
				timeLabel.setText(ft.format(date));
				for (int i = 0; i < 42; i++)
					day[i].setBackground(null);
				setShow();
			}
		});
		upMonth[1] = new JButton("��һ��");
		upMonth[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				date.setMonth(date.getMonth() + 1);
				timeLabel.setText(ft.format(date));
				for (int i = 0; i < 42; i++)
					day[i].setBackground(null);
				setShow();
			}
		});
		firstPanel.add(upMonth[0]);
		firstPanel.add(upMonth[1]);

		// SecondPanel
		secondPanel.setLayout(new GridLayout(7, 7));
		JButton top[] = new JButton[7];
		for (int i = 0; i < 7; i++) {
			top[i] = new JButton();
			top[i].setEnabled(false);
			top[i].setBorder(BorderFactory.createLineBorder(Color.black));
			secondPanel.add(top[i]);
		}
		top[0].setText("��");
		top[1].setText("һ");
		top[2].setText("��");
		top[3].setText("��");
		top[4].setText("��");
		top[5].setText("��");
		top[6].setText("��");
		for (int i = 0; i < 42; i++) {
			day[i] = new JLabel("0", JLabel.CENTER);
			day[i].setOpaque(true);
			day[i].setBackground(null);
			secondPanel.add(day[i]);
		}
		setShow();
	}

	void setShow() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int j = 1, i = cal.get(Calendar.DAY_OF_WEEK) - 1;
		cal.roll(Calendar.DATE, -1);// rollֻ�����ڶ�Ӧ��ʱ�����ԣ��˴�ֻ������DATE,MONTH��ֵ���䡣
		int maxday = cal.get(Calendar.DATE); // ͨ������set��roll�������õ�ָ�����µ����һ�졣
		cal.roll(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		for (int t = i - 1, m = cal.get(Calendar.DATE); t >= 0; t--, m--)// �������²���
		{
			day[t].setText(m + "");
			day[t].setBorder(BorderFactory.createLineBorder(Color.gray));
			day[t].setForeground(Color.gray);
		}
		for (; j <= maxday; i++, j++)// ���ñ���
		{
			day[i].setText(j + "");
			day[i].setBorder(BorderFactory.createLineBorder(Color.blue));
			day[i].setForeground(Color.black);
			if (date.getMonth() == new Date().getMonth() && new Date().getDate() == j) 
				day[i].setBackground(Color.pink);
		}
		for (; i < 42; i++, j++)// ���ñ��º�
		{
			day[i].setText(j - maxday + "");
			day[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			day[i].setForeground(Color.gray);
		}
	}

	public void dyeNow()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int j = 1, i = cal.get(Calendar.DAY_OF_WEEK) - 1;
		cal.roll(Calendar.DATE, -1);// rollֻ�����ڶ�Ӧ��ʱ�����ԣ��˴�ֻ������DATE,MONTH��ֵ���䡣
		int maxday = cal.get(Calendar.DATE); // ͨ������set��roll�������õ�ָ�����µ����һ�졣
		for (; j <= maxday; i++, j++)// ���ñ���
			if (date.getMonth() == new Date().getMonth() && new Date().getDate() == j) 
				day[i].setBackground(Color.pink);
	}
}

