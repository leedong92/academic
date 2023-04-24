package univ;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Univmenu_prof extends JFrame {
	JPanel jp;
	JMenuBar bar;
	JMenu std_m, sub_m, result_m, admin_m;
	JMenuItem std_tem, sub_tem, result_tem, admin_tem;
	
	
	
	public Univmenu_prof() {
		setTitle("학사관리시스템:");
		
		menu();
		
		setSize(600,650);
		setVisible(true);
		
	}
	void menu() {
		bar = new JMenuBar();
		
		std_m = new JMenu("학생관리");
		sub_m = new JMenu("교과목관리");
		result_m = new JMenu("성적관리");
		admin_m = new JMenu("사용자등록");
		
		std_tem = new JMenuItem("등록");
		sub_tem = new JMenuItem("등록");
		result_tem = new JMenuItem("등록");
		admin_tem = new JMenuItem("사용자등록");
		
		std_m.add(std_tem);
		sub_m.add(sub_tem);
		result_m.add(result_tem);
		admin_m.add(admin_tem);
		
		bar.add(std_m);
		bar.add(sub_m);
		bar.add(result_m);
		bar.add(admin_m);
		
		setJMenuBar(bar);
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Univmenu_prof();
	}

}
