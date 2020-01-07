package nyw_Calendar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MusicThread extends Thread {
	public void run() {
		//声明一个File对象
		File mp3 = new File("music/music.mp3");
		Player player;
		while(true)
		{
			try {
				//创建一个输入流
				FileInputStream fileInputStream = new FileInputStream(mp3);
				
				//创建一个缓冲流
				BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
				
				//创建播放器对象，把文件的缓冲流传入进去
				player = new Player(bufferedInputStream);
				
				//调用播放方法进行播放
				player.play();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
