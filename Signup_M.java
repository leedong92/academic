package univ;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Signup_M extends JFrame{
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, std_code, name, dept;
	JTextField std_f,name_f,dept_f,search_field;
	JComboBox combo;
	String menu[] = {"개설학과 선택하세요","컴퓨터공학","방사선","물리치료","사무자동화","간호1"};
	JButton look_bt,lookall_bt,add_bt,modify_bt,del_bt,end_bt;
	JScrollPane table_panel, table_panel2;
	DefaultTableModel dtm, dtm2;
	JTable table,table2;
	Font fon1 = new Font("Dialog",Font.PLAIN,20);
	
	public Signup_M(){
		setTitle("학사 관리");
		setLayout(null);
		setBounds(50,50,600,650);
		title();
		input();
		search();
		table();
		table2();
		button();
		
		setResizable(false);
		setVisible(true);
	}
	void title(){
		title_panel = new JPanel(null);
		title_panel.setBackground(Color.black);
		title = new JLabel("학사관리");
		title.setFont(fon1);
		title.setForeground(Color.white);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(5,5,600,30);
		title_panel.add(title);
		title_panel.setBounds(0,0,600,50);
		add(title_panel);
	}
	void input() {
		input_panel = new JPanel(null);
		
		std_code = new JLabel("학생코드");
		name = new JLabel("이름");
		dept = new JLabel("학과");
		
		std_f = new JTextField();
		name_f = new JTextField();
		dept_f = new JTextField();
		
		input_panel.setBounds(0,50,600,170);
		
		std_code.setBounds(15,10,65,25);
		std_f.setBounds(85,10,90,25);
		
		name.setBounds(205,10,55,25);
		name_f.setBounds(270,10,90,25);
		
		dept.setBounds(390,10,55,25);
		dept_f.setBounds(455,10,90,25);
		
		
		input_panel.add(std_code);
		input_panel.add(std_f);
		input_panel.add(name);
		input_panel.add(name_f);
		input_panel.add(dept);
		input_panel.add(dept_f);
		add(input_panel);
	}
	void search() {
		search_panel = new JPanel(null);
		combo = new JComboBox(menu);
		search_field = new JTextField();
		look_bt = new JButton("조회");
		lookall_bt = new JButton("전체조회");
		
		search_panel.setBackground(Color.GRAY);
		look_bt.setBackground(Color.black);
		look_bt.setForeground(Color.white);
		lookall_bt.setBackground(Color.black);
		lookall_bt.setForeground(Color.white);
		
		search_panel.setBounds(0,220,600,50);
		combo.setBounds(10,10,170,30);
		search_field.setBounds(185,10,150,30);
		look_bt.setBounds(375,10,100,30);
		lookall_bt.setBounds(480,10,100,30);
		
		search_panel.add(combo);
		search_panel.add(search_field);
		search_panel.add(look_bt);
		search_panel.add(lookall_bt);
		
		add(search_panel);
	}
	void table() {
		String header[] = {"교과목코드","교과목명","개설학과","수업시수","담당교수","학점"};
		Object rowdata[][] = {};
		dtm = new DefaultTableModel(null, header);
		table = new JTable(dtm);
		table_panel = new JScrollPane(table);
		table_panel.setBounds(5,275,575,115);
		
		add(table_panel);
	}
	void table2() {
		String header2[] = {"교과목코드","교과목명","개설학과","수업시수","담당교수","학점"};
		Object rowdata2[][] = {};
		dtm2 = new DefaultTableModel(null, header2);
		table2 = new JTable(dtm2);
		table_panel2 = new JScrollPane(table2);
		table_panel2.setBounds(5,395,575,115);
		
		add(table_panel2);
	}
	void button() {
		button_panel = new JPanel(null);
		add_bt = new JButton("추가");
		modify_bt = new JButton("수정");
		del_bt = new JButton("삭제");
		end_bt = new JButton("종료");
		
		button_panel.setBounds(0,510,600,140);
		add_bt.setBounds(15,30,135,50);
		modify_bt.setBounds(155,30,135,50);
		del_bt.setBounds(295,30,135,50);
		end_bt.setBounds(435,30,135,50);
		
		button_panel.add(add_bt);
		button_panel.add(modify_bt);
		button_panel.add(del_bt);
		button_panel.add(end_bt);
		
		add(button_panel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Signup_M();
	}

}
