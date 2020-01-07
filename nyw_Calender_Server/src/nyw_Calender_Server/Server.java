package nyw_Calender_Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	ServerSocket server;
	int serverPort = 8888;
	InputStream in = null;

	public Server() {
		try {
			server = new ServerSocket(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("服务器启动: ServerSocket: " + server);
	}

	private void listen() {
		while (true) {
			try {
				Socket socket = server.accept();
				new ClientThread(socket).start();
				//System.out.println(1111111);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server().listen();
	}

	class ClientThread extends Thread {
		private Socket socket;
		private ServerData db;
		
		public ClientThread(Socket socket) {
			this.socket = socket;
			//System.out.println("客户端连接成功:\n Socket: " + socket);
				try {
					//DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
					String path="data/things.db";
					File file=new File(path);
					FileInputStream in=new FileInputStream(file);
					byte[]date=new byte[1024];
					int i=0;
					while((i=in.read(date))!=-1) {
					outputStream.write(date,0,i );
					}
					outputStream.flush();
					in.close();
					//outputStream.close();
					this.socket.shutdownOutput();
					//System.out.println("客户端数据已更新");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			db = new ServerData("jdbc:sqlite:data/things.db");

		}

		@Override
		public void run() {
			while (true) {
				try {
					DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					String getS = inputStream.readUTF();
					String S[] = getS.split("#");
					/*for (String s : S)
					{
						System.out.println(s);
					}
					*/
					switch(S[0]) {
					case "insertThingsA":
						//System.out.println("1");
						db.insertThingsA(new Date(Long.valueOf(S[1]).longValue()),S.length<=2?"":S[2]);
						break;
					case"insertThingsB":
						//System.out.println("2");
						db.insertThingsB(new Date(Long.valueOf(S[1]).longValue()),S[2]);
						break;
					case"updateThingsB":
						//System.out.println("3");
						db.updateThingsB(new Date(Long.valueOf(S[1]).longValue()),new Date(Long.valueOf(S[2]).longValue()),S[3]);
						break;
					case"deleteThingsB":
						db.deleteThingsB(new Date(Long.valueOf(S[1]).longValue()),S[2]);
						break;
					}
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					this.stop();
				}
			}
		}

	}

}
