package nyw_Calendar;

import java.util.Date;
import java.util.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.*;

public class Data {

	private String db;

	public Data(String dbname) {
		db = dbname;
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");
			// 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();
			//stat.executeUpdate("create table ThingsA(Date long int, things String);");//
		    // 创建一个表，两列
			//stat.executeUpdate("create table ThingsB(Date long int, things String);");//
			// 创建一个表，两列

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertThingsA(Date date, String thing) {
		try {
			NetClient net= NetClient.getInstance();
			net.send("insertThingsA#"+date.getTime()+"#"+thing);
			// 连接SQLite的JDBC并建立数据库连接
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

	String getThingsA(Date date) {
		String things ="";
		try {
			// 连接SQLite的JDBC并建立数据库连接
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsA where date=" + date.getTime() / 1000 + ";"); // 查询数据
			if(rs.next()) { // 将查询到的数据赋值
				things = rs.getString("things");
			}
			rs.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return things;
	}

	public void insertThingsB(Date date, String thing) {
		NetClient net= NetClient.getInstance();
		net.send("insertThingsB#"+date.getTime()+"#"+thing);
		try {
			// 连接SQLite的JDBC并建立数据库连接
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
			NetClient net= NetClient.getInstance();
			net.send("updateThingsB#"+oldDate.getTime()+"#"+newDate.getTime()+"#"+thing);
			// 连接SQLite的JDBC并建立数据库连接
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsB where date=" + oldDate.getTime() / 1000 + ";");
			if (rs.next())
				stat.executeUpdate("update ThingsB set date="+newDate.getTime()/1000+", things=\"" + thing
						+ "\" where date=" + oldDate.getTime() / 1000 + ";");
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<Date, String> getThingsB(Date date) {
		Map<Date, String> tMap = new LinkedHashMap<Date, String>();
		ResultSet a = null;
		try {
			// 连接SQLite的JDBC并建立数据库连接
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsB "
					+ "where date>=" + date.getTime() / 1000 + " and date <"+ (86400+date.getTime() / 1000)+" order by date ASC ;");
			while(rs.next()) {
				tMap.put(new Date(1000L*Integer.valueOf(rs.getString("date")).intValue()), rs.getString("things"));
			}
			a=rs;
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tMap;

	}

	public void deleteThingsB(Date date,String thing)
	{
		NetClient net= NetClient.getInstance();
		net.send("deleteThingsB#"+date.getTime()+"#"+thing);
		try {
			// 连接SQLite的JDBC并建立数据库连接
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();
			stat.executeUpdate("delete from ThingsB where date=" + date.getTime() / 1000 + ";");
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String findStringB(Date date)
	{
		String a="";
		try {
			// 连接SQLite的JDBC并建立数据库连接
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(db);
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("select * from ThingsB where date=" + date.getTime() / 1000 + ";");
			if (rs.next())
				a=a+rs.getString("things");
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
}
