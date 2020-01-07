package nyw_Calendar;

import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.text.*;
import javax.swing.*;

public class TopPanel extends JPanel {
	private JLabel timeLabel=new JLabel();
	private Date date = new Date();

	TopPanel() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeLabel.setFont(new Font("Π±Με", Font.BOLD, 30));
		this.add(timeLabel);
		Thread refresh = new Thread(new Runnable() {
			public void run() {
				while (true) {
					date =new Date();
					timeLabel.setText(ft.format(date));
				}
			}
		});
		refresh.start();
	}

}
