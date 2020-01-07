package nyw_Calendar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MusicThread extends Thread {
	public void run() {
		//����һ��File����
		File mp3 = new File("music/music.mp3");
		Player player;
		while(true)
		{
			try {
				//����һ��������
				FileInputStream fileInputStream = new FileInputStream(mp3);
				
				//����һ��������
				BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
				
				//�������������󣬰��ļ��Ļ����������ȥ
				player = new Player(bufferedInputStream);
				
				//���ò��ŷ������в���
				player.play();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
