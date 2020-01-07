package nyw_Calender_Server;

import java.sql.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServerData {
	private String db;

	public ServerData(String dbname) {
		db = dbname;
		try {
			// ����SQLite��JDBC
			Class.forName("org.sqlite.JDBC");
			// ����һ�����ݿ���zieckey.db�����ӣ���������ھ��ڵ�ǰĿ¼�´���֮
			Connection conn = DriverManager.getConnection(db);
			// Statement stat = conn.createStatement();
			// stat.executeUpdate("create table ThingsA(Date long int, things String);");//
			// ����һ��������
			// stat.executeUpdate("create table ThingsB(Date long int, things String);");//
			// ����һ��������

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertThingsA(Date date, String thing) {
		try {
			// ����SQLite��JDBC���������ݿ�����
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsA where date=" + date.getTime() / 1000 + ";");
			if (rs.next())
				stat.executeUpdate(
						"update ThingsA set things=\"" + thing + "\" where date=" + date.getTime() / 1000 + ";");
			else
				stat.executeUpdate(
						"insert into ThingsA(Date,things) values(" + date.getTime() / 1000 + ",\"" + thing + "\");");

			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertThingsB(Date date, String thing) {
		try {
			// ����SQLite��JDBC���������ݿ�����
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsB where date=" + date.getTime() / 1000 + ";");
			if (rs.next())
				stat.executeUpdate("update ThingsB set things=\"" + rs.getString("things") + " and " + thing
						+ "\" where date=" + date.getTime() / 1000 + ";");
			else
				stat.executeUpdate(
						"insert into ThingsB(Date,things) values(" + date.getTime() / 1000 + ",\"" + thing + "\");");

			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateThingsB(Date oldDate, Date newDate, String thing) {
		try {
			// ����SQLite��JDBC���������ݿ�����
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsB where date=" + oldDate.getTime() / 1000 + ";");
			//System.out.println("1111111");
			if (rs.next())
				stat.executeUpdate("update ThingsB set date=" + newDate.getTime() / 1000 + ", things=\"" + thing
						+ "\" where date=" + oldDate.getTime() / 1000 + ";");
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteThingsB(Date date, String thing) {
		try {
			// ����SQLite��JDBC���������ݿ�����
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();
			stat.executeUpdate("delete from ThingsB where date=" + date.getTime() / 1000 + ";");

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
