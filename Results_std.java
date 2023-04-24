package univ;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Results_std extends JFrame implements ActionListener{
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, std_code, std_name, open_y, open_grade, open_semester;
	JTextField code_field, name_field, search_field;
	JComboBox combo, y_c, grade_c, semester_c;
	String menu[] = {"검색할 카테고리 선택하세요","code"};
	String gc[] = {"1","2","3","4"};
	String yc[] = {"2021","2022","2023","2024","2025","2026","2027","2028"};
	String s[] = {"1","2","3","4"};
	String y_combo, g_combo, s_combo;	
	String category; 	//검색창 콤보리스트
	String subjcode;	//검색창 과목코드
	String subject; 	//과목 이름
	JButton look_bt,end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Font fon1 = new Font("Dialog",Font.PLAIN,20);
	Connect con;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;

	PreparedStatement pstmt = null;
	int srow;
	
	public Results_std(String a){
		setTitle("성적 관리");
		setLayout(null);
		setBounds(50,50,600,650);
		
		con = new Connect();
		title();
		input();	//데이터 입력창
		code_field.setText(a); //학생코드 넣기
		namefield(); //학생이름 넣기
		
		search();	//검색창
		boxx();	//검색창 콤보박스에 과목이름 리스트
		table();	//성적표 나오는곳
		button();	//버튼
		
		setResizable(false);
		setVisible(true);
	}
	void title(){
		title_panel = new JPanel(null);
		title_panel.setBackground(Color.black);
		title = new JLabel("성적관리");
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
		std_name = new JLabel("학생이름");
		open_y = new JLabel("개설년도");
		open_grade = new JLabel("개설학년");
		open_semester = new JLabel("개설학기");
		
		code_field = new JTextField();
		name_field = new JTextField();
		y_c = new JComboBox(yc);
		y_c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				y_combo = y_c.getSelectedItem().toString();
			}
			
		});
		
		grade_c = new JComboBox(gc);
		grade_c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				g_combo = grade_c.getSelectedItem().toString();
			}
			
		});
		semester_c = new JComboBox(s);
		semester_c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				s_combo = semester_c.getSelectedItem().toString();
			}
			
		});
		
		input_panel.setBounds(0,50,600,170);
		
		std_code.setBounds(15,20,60,25);
		code_field.setBounds(80,20,90,25);
		std_name.setBounds(210,20,60,25);		
		name_field.setBounds(275,20,90,25);
		open_y.setBounds(405,20,60,25);
		y_c.setBounds(470,20,90,25);
		
		open_grade.setBounds(15,50,60,25);
		grade_c.setBounds(80,50,90,25);
		open_semester.setBounds(210,50,60,25);
		semester_c.setBounds(275,50,90,25);
		
		input_panel.add(std_code);
		input_panel.add(std_name);
		input_panel.add(open_y);
		input_panel.add(open_grade);
		input_panel.add(open_semester);
		
		input_panel.add(code_field);
		input_panel.add(name_field);
		input_panel.add(y_c);
		input_panel.add(grade_c);
		input_panel.add(semester_c);
		add(input_panel);
	}
	void search() {
		search_panel = new JPanel(null);
		combo = new JComboBox();
		
		//과목 이름으로 과목코드 추출해서 검색필드에 코드 넣기
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				category = combo.getSelectedItem().toString();
				try {
					String sql = "select * from subject where sub_name=?";
					pstmt = con.conn.prepareStatement(sql);
					pstmt.setString(1, category);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						subjcode = rs.getString(1);
					}
					search_field.setText(subjcode);
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		search_field = new JTextField();
		look_bt = new JButton("조회");
		look_bt.addActionListener(this);
		
		search_panel.setBackground(Color.GRAY);
		look_bt.setBackground(Color.black);
		look_bt.setForeground(Color.white);
		
		search_panel.setBounds(0,220,600,50);
		combo.setBounds(10,10,170,30);
		search_field.setBounds(185,10,150,30);
		look_bt.setBounds(480,10,100,30);
		
		search_panel.add(combo);
		search_panel.add(search_field);
		search_panel.add(look_bt);
		
		add(search_panel);
	}
	void table() {
		String header[] = {"학번","이름","출석","레포트","중간","기말","가산점","합계","학점"};
		Object rowdata[][] = {};
		dtm = new DefaultTableModel(null, header);
		table = new JTable(dtm);
		table_panel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				srow = table.getSelectedRow();
				
			}
			
		});
		table_panel.setBounds(5,275,575,230);
		
		add(table_panel);
	}
	void button() {
		button_panel = new JPanel(null);
		end_bt = new JButton("종료");
		end_bt.addActionListener(this);
		
		button_panel.setBounds(0,510,600,140);
		end_bt.setBounds(435,30,135,50);
		
		button_panel.add(end_bt);
		
		add(button_panel);
	}
	void getListAll() { // 완
		String attended = null;
		String report = null;
		String middle = null;
		String fainal = null;
		String added = null;
		String sum = null;
		String grade_value = null;
		String stdname = null;
		try {
			this.dtm.setRowCount(0);

			String quary = "SELECT * FROM result where year =? and grade=? and term=? and sub_code=? and std_code=?";
			pstmt = con.conn.prepareStatement(quary);
			pstmt.setString(1, y_combo);
			pstmt.setString(2, g_combo);
			pstmt.setString(3, s_combo);
			pstmt.setString(4, subjcode);
			pstmt.setString(5, code_field.getText());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				attended = rs.getString(11);
				report = rs.getString(10);
				middle = rs.getString(8);
				fainal = rs.getString(9);
				added = rs.getString(12); // 가산점
				sum = rs.getString(13);
				grade_value = rs.getString(14);
				
				quary = "select * from student where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, code_field.getText());
				rs1 = pstmt.executeQuery();
				while(rs1.next()) {
					stdname = rs1.getString(2);
				}
			}			
			Object[] rowData= {code_field.getText(), stdname, attended, report, middle, fainal, added, sum, grade_value};
			dtm.addRow(rowData);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//검색창 과목코드 가져오기
	void boxx() {
		try {
			String sql = "select distinct * from result where std_code = ?";
			pstmt = con.conn.prepareStatement(sql);
			pstmt.setString(1, code_field.getText());
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				String sub_code = rs.getString(4);
				
				String sql2 = "select * from subject where code = ?";
				pstmt = con.conn.prepareStatement(sql2);
				pstmt.setString(1, sub_code);
				rs2 = pstmt.executeQuery();
				
				
				while (rs2.next()) {
					subject = rs2.getString(2);
					combo.addItem(subject);
					
				}
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//이름 필드에 학생이름 넣기
	void namefield() {	
		String query = "SELECT * FROM student where code = ?";			
		try {
			pstmt = con.conn.prepareStatement(query);
			pstmt.setString(1, code_field.getText());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {			
				name_field.setText(rs.getString(2));
				
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==look_bt) {
			getListAll();
		}
		if(e.getSource()==end_bt) {
			dispose();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Results_std("s101");
	}

}
