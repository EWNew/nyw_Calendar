package nyw_Calendar;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;

public class MainWindow extends JFrame implements MouseListener {
	private TopPanel topPanel;
	private MidPanel midPanel;
	private BotPanel botPanel;

	public MainWindow() {
		super("Calender");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(765, 900);
		this.setLocation(510, 60);
		setResizable(false);
		this.setLayout(null);
		topPanel = new TopPanel();
		midPanel = new MidPanel();
		botPanel = new BotPanel();
		this.add(topPanel);
		this.add(midPanel);
		this.add(botPanel);
		topPanel.setBounds(0, 0, 765, 50);
		midPanel.setBounds(0, 50, 750, 500);
		botPanel.setBounds(0, 550, 765, 350);
		setVisible(true);

		for (int i = 0; i < 42; i++) {
			midPanel.day[i].addMouseListener(this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < 42; i++) {
			if (e.getSource() == midPanel.day[i]) {
				int t = Integer.valueOf(midPanel.day[i].getText()).intValue();
				Calendar thingDate = Calendar.getInstance();
				thingDate.setTime(midPanel.date);
				thingDate.set(Calendar.DATE, t);
				if (i < 7 && t > 20) {
					if (thingDate.getTime().getMonth() - 1 < 0)
						thingDate.roll(Calendar.YEAR, -1);
					thingDate.roll(Calendar.MONTH, -1);
				} else if (i > 28 && t < 15) {
					if (thingDate.getTime().getMonth() + 1 > 11)
						thingDate.roll(Calendar.YEAR, 1);
					thingDate.roll(Calendar.MONTH, 1);
				}
				botPanel.refresh(thingDate.getTime());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel temp = (JLabel) e.getSource();
		temp.setBackground(Color.pink);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel temp = (JLabel) e.getSource();
		// if (midPanel.date.getMonth() == new Date().getMonth() && new Date().getDate()
		// == Integer.valueOf(temp.getText()).intValue()) ;
		// else
		temp.setBackground(null);
		midPanel.dyeNow();

	}

}
