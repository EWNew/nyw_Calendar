package nyw_Calendar;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

public class BlingTask extends TimerTask {
	private Data db;

	@Override
	public void run() {
		//SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(ft.format(new Date()));
		db = new Data("jdbc:sqlite:data/things.db");
		Date date=new Date();
		String things=db.findStringB(date);
		if(things!="")
		{
			Thread refresh = new Thread(new Runnable() {
				public void run() {
					//System.out.println("����"+things); 
					SimpleDateFormat ftt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					MusicThread music=new MusicThread();
					music.start();
					int n=JOptionPane.showConfirmDialog(null,"�����¼�:"+ftt.format(date)+" : "+things,"�����¼�",JOptionPane.CLOSED_OPTION,JOptionPane. INFORMATION_MESSAGE);
					if(n==JOptionPane.CLOSED_OPTION||n==0)
					{
						//System.out.println("�ر�����");
						music.stop();
					}
				}
			});
			refresh.start();
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
