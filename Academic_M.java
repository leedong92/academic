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

public class Academic_M extends JFrame{
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, depart_code, depart_name, major;
	JTextField code_field, name_field, major_field, search_field;
	JComboBox combo;
	String menu[] = {"학과코드","학과명","전공명","검색할 카테고리 선택하세요"};
	JButton look_bt,lookall_bt,add_bt,modify_bt,del_bt,end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Font fon1 = new Font("Dialog",Font.PLAIN,20);
	
	public Academic_M(){
		setTitle("학사 관리");
		setLayout(null);
		setBounds(50,50,600,650);
		title();
		input();
		search();
		table();
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
		depart_code = new JLabel("학과코드");
		depart_name = new JLabel("학과명");
		major = new JLabel("전공명");
		code_field = new JTextField();
		name_field = new JTextField();
		major_field = new JTextField();
		
		input_panel.setBounds(0,50,600,170);
		
		depart_code.setBounds(10,20,60,25);
		code_field.setBounds(75,20,100,25);
		
		depart_name.setBounds(210,20,60,25);
		name_field.setBounds(275,20,100,25);
		
		major.setBounds(410,20,60,25);
		major_field.setBounds(475,20,100,25);
		
		input_panel.add(depart_code);
		input_panel.add(depart_name);
		input_panel.add(major);
		input_panel.add(code_field);
		input_panel.add(name_field);
		input_panel.add(major_field);
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
		String header[] = {"학과코드","학과명","전공명"};
		Object rowdata[][] = {};
		dtm = new DefaultTableModel(null, header);
		table = new JTable(dtm);
		table_panel = new JScrollPane(table);
		table_panel.setBounds(5,275,575,230);
		
		add(table_panel);
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
		new Academic_M();
	}

}
