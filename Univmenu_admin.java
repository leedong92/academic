package univ;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Univmenu_admin extends JFrame implements ActionListener {
	JPanel jp;
	JMenuBar bar;
	JMenu std_m, prof_m, dept_m, sub_m, admin_m;
	JMenuItem std_tem,prof_tem,dept_tem, sub_tem, admin_tem;
	Connect con;
	
	
	public Univmenu_admin(String a) {
		con = new Connect();
		setTitle("학사관리시스템:"+a);
		
		menu();
		
		setSize(600,650);
		setVisible(true);
		
	}
	void menu() {
		bar = new JMenuBar();
		
		std_m = new JMenu("학생관리");
		prof_m = new JMenu("교수관리");
		dept_m = new JMenu("학과관리");
		sub_m = new JMenu("교과목관리");
		admin_m = new JMenu("사용자등록");
		
		std_tem = new JMenuItem("등록");
		prof_tem = new JMenuItem("등록");
		dept_tem = new JMenuItem("등록");
		sub_tem = new JMenuItem("등록");
		admin_tem = new JMenuItem("사용자등록");
		
		std_tem.addActionListener(this);
		prof_tem.addActionListener(this);
		dept_tem.addActionListener(this);
		sub_tem.addActionListener(this);
		admin_tem.addActionListener(this);
		
		std_m.add(std_tem);
		prof_m.add(prof_tem);
		dept_m.add(dept_tem);
		sub_m.add(sub_tem);
		admin_m.add(admin_tem);
		
		bar.add(std_m);
		bar.add(prof_m);
		bar.add(dept_m);
		bar.add(sub_m);
		bar.add(admin_m);
		
		setJMenuBar(bar);
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==std_tem) {
			Student_M stdM = new Student_M();
			dispose();
		}
		if(e.getSource()==prof_tem) {
			Prof_M profM = new Prof_M();
			dispose();
		}
		if(e.getSource()==dept_tem) {
			Department_M deptM = new Department_M();
			dispose();
		}
		if(e.getSource()==sub_tem) {
			Subject_M subM = new Subject_M();
			dispose();
		}
		if(e.getSource()==admin_tem) {
			Admin_M adminM = new Admin_M();
			dispose();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Univmenu_admin("p101");
	}

}
