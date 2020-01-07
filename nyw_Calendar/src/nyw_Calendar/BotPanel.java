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
	JButton addThing = new JButton("��������¼�");
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
		SimpleDateFormat ft = new SimpleDateFormat("yyyy��MM��dd��");
		date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		timeLabel = new JLabel(ft.format(date));
		timeLabel.setFont(new Font("", Font.BOLD, 20));
		topPanel.add(timeLabel);

		// mainPanel
		things = new JTextArea(13, 42);
		things.setLineWrap(true);// �����Զ�����
		thingsB = new JTextArea(13, 30);
		thingsB.setLineWrap(true);
		thingsB.setEditable(false);
		mainPanel.add(things);
		mainPanel.add(thingsB);
		// ���ݿ��ʼ��
		db = new Data("jdbc:sqlite:data/things.db");

		// ��ʼ����־�б�
		if (db.getThingsA(date) == "")
			things.setText("���ճ�");
		else
			things.setText(db.getThingsA(date));

		Map<Date, String> tMap = db.getThingsB(date);
		b = new String("");
		tMap.forEach((key, value) -> {
			SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//System.out.println(ftB.format(key) + "��" + value);
			b = b + ftB.format(key) + " ��" + value + "\n";
		});
		thingsB.setText("�������¼���\n" + b);

		// ��������¼���ť
		topPanel.add(addThing);
		addThing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setTitle("Ϊ" + ft.format(date) + "��������¼�");
				dialog.setBounds(710, 400, 350, 150);
				dialog.setResizable(false);
				JLabel a = new JLabel("����ʱ�䣺");
				JLabel b = new JLabel("ʱ");
				JLabel c = new JLabel("��");
				JLabel d = new JLabel("�����¼���");
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

				JButton Y = new JButton("ȷ��");
				JButton N = new JButton("ȡ��");
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

		// ��ӱ��水ť
		JButton Baocun = new JButton("������־");
		mainPanel.add(Baocun);
		Baocun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Athings.put(date, things.getText());
				db.insertThingsA(date, things.getText());
				//System.out.println(Athings);
			}
		});

		// ��ӱ༭�������¼���ť
		JButton Bianji = new JButton("�༭�������¼�");
		mainPanel.add(Bianji);
		Bianji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				class ADialog extends JDialog{
					DefaultListModel<String> listModel = new DefaultListModel<String>();
					JList<String> thingsBList = new JList<String>(listModel);
					JScrollPane scrollPane = new JScrollPane(thingsBList);
					public ADialog() {
						setTitle("�༭�������¼�");
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
								dialog.setTitle("�༭�����¼�");
								dialog.setBounds(710, 400, 350, 150);
								dialog.setResizable(false);
								JLabel a = new JLabel("�༭ʱ�䣺");
								JLabel b = new JLabel("ʱ");
								JLabel c = new JLabel("��");
								JLabel d = new JLabel("�༭�¼���");
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

								JButton Y = new JButton("ȷ��");
								JButton N = new JButton("ȡ��");
								JButton Delete = new JButton("ɾ��������");
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
		SimpleDateFormat ft = new SimpleDateFormat("yyyy��MM��dd��");
		timeLabel.setText(ft.format(date));

		// ������־ thingsA
		if (db.getThingsA(date) ==""||db.getThingsA(date).length()==0)
			things.setText("���ճ�");
		else
			things.setText(db.getThingsA(date));

		// ���������¼�thingsB
		Map<Date, String> tMap = db.getThingsB(date);
		b = new String("");
		tMap.forEach((key, value) -> {
			SimpleDateFormat ftB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//System.out.println(ftB.format(key) + "��" + value);
			b = b + ftB.format(key) + " ��" + value + "\n";
		});
		thingsB.setText("�������¼���\n" + b);
	}

}
