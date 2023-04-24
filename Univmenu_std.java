package univ;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Univmenu_std extends JFrame {
	JPanel jp;
	JMenuBar bar;
	JMenu attend_m, result_m, admin_m;
	JMenuItem attend_tem, result_tem, admin_tem;
	
	
	
	public Univmenu_std() {
		setTitle("학사관리시스템:");
		
		menu();
		
		setSize(600,650);
		setVisible(true);
		
	}
	void menu() {
		bar = new JMenuBar();
		
		attend_m = new JMenu("수강관리");
		result_m = new JMenu("성적관리");
		admin_m = new JMenu("사용자등록");
		
		attend_tem = new JMenuItem("등록");
		result_tem = new JMenuItem("조회");
		admin_tem = new JMenuItem("사용자등록");
		
		attend_m.add(attend_tem);
		result_m.add(result_tem);
		admin_m.add(admin_tem);
		
		bar.add(attend_m);
		bar.add(result_m);
		bar.add(admin_m);
		
		setJMenuBar(bar);
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Univmenu_std();
	}

}
