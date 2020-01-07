package nyw_Calendar;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class NetClient {
	private static NetClient instance = null;
	Socket client = null;
	String serverAddr = "localhost";
	int serverPort = 8888;

	boolean isConnect;

	private NetClient() {
		try {
			client = new Socket(serverAddr, serverPort);
			isConnect = true;
			DataInputStream inputStream = new DataInputStream(client.getInputStream());
			//DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
			String path = "data/things.db";
			File file = new File(path);

			OutputStream fo = new FileOutputStream(file);
			byte[]date=new byte[1024];
			int i=0;
			while((i=inputStream.read(date))!=-1) {
				fo.write(date,0,i );
			}
			fo.close();
			inputStream.close();
			JOptionPane.showMessageDialog(null, "网络服务器连接成功，数据已更新");
			System.out.println("服务器连接成功:\n Client: " + client);
		} catch (UnknownHostException e) {
			//e.printStackTrace();
			isConnect = false;
		} catch (IOException e) {
			//e.printStackTrace();
			isConnect = false;
		}
	}

	public static NetClient getInstance() {
		if (instance == null) {
			instance = new NetClient();
		}
		return instance;
	}

	public void send(String s) {
		if (!isConnect)
			return;
		try {
			client = new Socket(serverAddr, serverPort);
			DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
			outputStream.writeUTF(s);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
