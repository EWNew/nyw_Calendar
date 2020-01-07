package nyw_Calendar;

import java.util.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;

public class BotPanel extends JPanel {
	private JPanel topPanel;
	private JPanel mainPanel;
	private JLabel timeLabel;
	private JTextArea things, thingsB;
	private Date date;
	JButton addThing = new JButton("添加提醒事件");
	//Map<Date, String> Athings = new HashMap<Date, String>();
	//Map<Date, String> Bthings = new HashMap<Date, String>();
	private Data db;
	private String b;

	BotPanel() {
		topPanel = new JPanel();
		mainPanel = new JPanel();
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);

		// topPanel
		SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日");
		date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		timeLabel = new JLabel(ft.format(date));
		timeLabel.setFont(new Font("", Font.BOLD, 20));
		topPanel.add(timeLabel);

		// mainPanel
		things = new JTextArea(13, 42);
		things.setLineWrap(true);// 设置自动换行
		thingsB = new JTextArea(13, 30);
		thingsB.setLineWrap(true);
		thingsB.setEditable(false);
		mainPanel.add(things);
		mainPanel.add(thingsB);
		// 数据库初始化
		db = new Data("jdbc:sqlite:data/things.db");

		// 初始化日志列表
		if (db.getThingsA(date) == "")
			things.setText("无日程");
		else
			things.setText(db.getThingsA(date));

		Map<Date, String> tMap = db.getThingsB(date);
		b = new String("");
		tMap.forEach((key, value) -> {
			SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//System.out.println(ftB.format(key) + "：" + value);
			b = b + ftB.format(key) + " ：" + value + "\n";
		});
		thingsB.setText("需提醒事件：\n" + b);

		// 添加提醒事件按钮
		topPanel.add(addThing);
		addThing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setTitle("为" + ft.format(date) + "添加提醒事件");
				dialog.setBounds(710, 400, 350, 150);
				dialog.setResizable(false);
				JLabel a = new JLabel("输入时间：");
				JLabel b = new JLabel("时");
				JLabel c = new JLabel("分");
				JLabel d = new JLabel("输入事件：");
				JTextField h = new JTextField();
				JTextField m = new JTextField();
				JTextField t = new JTextField();
				h.setColumns(10);
				m.setColumns(10);
				t.setColumns(22);
				JPanel Panel = new JPanel();
				dialog.add(Panel);
				Panel.add(a);
				Panel.add(h);
				Panel.add(b);
				Panel.add(m);
				Panel.add(c);
				Panel.add(d);
				Panel.add(t);

				JButton Y = new JButton("确定");
				JButton N = new JButton("取消");
				Panel.add(Y);
				Y.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Date Bdate = new Date();
						Bdate = date;
						Bdate.setHours(Integer.valueOf(h.getText()).intValue());
						Bdate.setMinutes(Integer.valueOf(m.getText()).intValue());
						db.insertThingsB(Bdate, t.getText());
						refresh(date);
						dialog.dispose();
					}
				});
				Panel.add(N);
				N.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}

				});

				dialog.setModal(true);
				dialog.setVisible(true);
			}
		});

		// 添加保存按钮
		JButton Baocun = new JButton("保存日志");
		mainPanel.add(Baocun);
		Baocun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Athings.put(date, things.getText());
				db.insertThingsA(date, things.getText());
				//System.out.println(Athings);
			}
		});

		// 添加编辑需提醒事件按钮
		JButton Bianji = new JButton("编辑需提醒事件");
		mainPanel.add(Bianji);
		Bianji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				class ADialog extends JDialog{
					DefaultListModel<String> listModel = new DefaultListModel<String>();
					JList<String> thingsBList = new JList<String>(listModel);
					JScrollPane scrollPane = new JScrollPane(thingsBList);
					public ADialog() {
						setTitle("编辑需提醒事件");
						setBounds(710, 300, 350, 400);
						setResizable(false);
						Map<Date, String> tMap = db.getThingsB(date);
						tMap.forEach((key, value) -> {
							SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							String B = new String(ftB.format(key) + " : " + value);
							listModel.addElement(B);			
						});
						
						thingsBList.addMouseListener(new MouseListener() {
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								String[] aa = thingsBList.getSelectedValue().split(" : ");
								//System.out.println(thingsBList.getSelectedValue());
								//System.out.println(aa[0]);
								//System.out.println(aa[1]);
								SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								Date da = new Date();
								try {
									da = ftB.parse(aa[0]);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								JDialog dialog = new JDialog();
								dialog.setTitle("编辑提醒事件");
								dialog.setBounds(710, 400, 350, 150);
								dialog.setResizable(false);
								JLabel a = new JLabel("编辑时间：");
								JLabel b = new JLabel("时");
								JLabel c = new JLabel("分");
								JLabel d = new JLabel("编辑事件：");
								JTextField h = new JTextField("" + da.getHours());
								JTextField m = new JTextField("" + da.getMinutes());
								JTextField t = new JTextField(aa[1]);
								h.setColumns(10);
								m.setColumns(10);
								t.setColumns(22);
								JPanel Panel = new JPanel();
								dialog.add(Panel);
								Panel.add(a);
								Panel.add(h);
								Panel.add(b);
								Panel.add(m);
								Panel.add(c);
								Panel.add(d);
								Panel.add(t);

								JButton Y = new JButton("确定");
								JButton N = new JButton("取消");
								JButton Delete = new JButton("删除此提醒");
								Panel.add(Y);
								Y.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										Date oldDate = new Date();
										try {
											oldDate = ftB.parse(aa[0]);
										} catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										Date newDate = new Date(oldDate.getTime());
										newDate.setHours(Integer.valueOf(h.getText()).intValue());
										newDate.setMinutes(Integer.valueOf(m.getText()).intValue());
										db.updateThingsB(oldDate, newDate, t.getText());
										updateB();
										dialog.dispose();
									}
								});
								Panel.add(N);
								N.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										dialog.dispose();
									}
								});
								Delete.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										Date oldDate = new Date();
										try {
											oldDate = ftB.parse(aa[0]);
										} catch (ParseException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										db.deleteThingsB(oldDate, aa[1]);
										updateB();
										dialog.dispose();
									}
								});
								Panel.add(Delete);
								dialog.setModal(true);
								dialog.setVisible(true);
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
								
							}

							@Override
							public void mouseExited(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
						});
						
						add(scrollPane);
						setModal(true);
						setVisible(true);
						
					}
					
					public void updateB() {
						listModel.clear();
						Map<Date, String> tMap = db.getThingsB(date);
						tMap.forEach((key, value) -> {
							SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							String B = new String(ftB.format(key) + " : " + value);
							listModel.addElement(B);
						});
						refresh(date);
					}
				}
				
				ADialog aD=new ADialog();
				};
		});
	}

	public void refresh(Date d) {
		date = d;
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日");
		timeLabel.setText(ft.format(date));

		// 更新日志 thingsA
		if (db.getThingsA(date) ==""||db.getThingsA(date).length()==0)
			things.setText("无日程");
		else
			things.setText(db.getThingsA(date));

		// 更新提醒事件thingsB
		Map<Date, String> tMap = db.getThingsB(date);
		b = new String("");
		tMap.forEach((key, value) -> {
			SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//System.out.println(ftB.format(key) + "：" + value);
			b = b + ftB.format(key) + " ：" + value + "\n";
		});
		thingsB.setText("需提醒事件：\n" + b);
	}

}
