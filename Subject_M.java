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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Subject_M extends JFrame implements ActionListener{
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, sub_code, sub_name, subopen_y, open_sub, opengrade,open_semester,class_hours,sub_prof,opening_credits;
	JTextField code_f, name_f, hours_f, credits_f,search_field;
	JComboBox combo,y_c, dept_c, opengrade_c,semester_c,prof_c,
			search_c;
	String y[] = {"2021","2022","2023","2024","2025","2026","2027","2028"};
	String og[] = {"1","2","3","4"};
	String s[] = {"1","2","3","4"};
	String dbm[] = {"검색할 카테고리 선택하세요","code","subject","dept_code","prof_code","year"};
	JButton look_bt,lookall_bt,add_bt,modify_bt,del_bt,end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Font fon1 = new Font("Dialog",Font.PLAIN,20);
	
	Connect con;
	ResultSet rs = null;
	ResultSet rs1 = null;

	PreparedStatement pstmt;
	
	int srow;
	String deptcode = null;
	String deptname = null;
	String yc,category;
	String profcategory = null;
	String profcodeV = null;
	String profname = null;
	
	public Subject_M(){
		setTitle("학사 관리");
		setLayout(null);
		setBounds(50,50,600,650);
		con = new Connect();
		title();
		input();
		search();
		table();
		button();
		prof_c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				profcategory = (String) prof_c.getSelectedItem();
				try {
					String sql = "select * from prof where name = ?";
					pstmt = con.conn.prepareStatement(sql);
					pstmt.setString(1, profcategory);
					System.out.println(profcategory);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						profcodeV = rs.getString(1);
						profname = rs.getString(2);
						System.out.println(profcodeV);
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
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
		sub_code = new JLabel("교과목코드");
		sub_name = new JLabel("개설강좌명");
		subopen_y = new JLabel("개설년도");
		
		open_sub = new JLabel("개설학과");
		opengrade = new JLabel("개설학년");
		open_semester = new JLabel("개설학기");
		
		class_hours = new JLabel("수업시수");
		sub_prof = new JLabel("담당교수");
		opening_credits = new JLabel("개설학점");
		
		code_f = new JTextField();
		name_f = new JTextField();
		y_c = new JComboBox(y);
		dept_c = new JComboBox(); // 개설학과
		boxm1();
		dept_c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				category = (String) dept_c.getSelectedItem();
				search_field.requestFocus();
				
				
				try {
					String sql = "select * from depart where dept = ?"; 
					pstmt = con.conn.prepareStatement(sql);
					pstmt.setString(1, category);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						deptcode = rs.getString(1);
						deptname = rs.getString(2);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		opengrade_c = new JComboBox(og);
		semester_c = new JComboBox(s);
		
		hours_f = new JTextField();
		
		prof_c = new JComboBox();
		boxprof();
		
//		prof_c.addItemListener(new ItemListener() {
//
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				// TODO Auto-generated method stub
//				profcategory = (String) prof_c.getSelectedItem();
//				try {
//					String sql = "select * from prof where name = ?";
//					pstmt = con.conn.prepareStatement(sql);
//					pstmt.setString(1, profcategory);
//					System.out.println(profcategory);
//					rs = pstmt.executeQuery();
//
//					while (rs.next()) {
//						profcodeV = rs.getString(1);
//						profname = rs.getString(2);
//						System.out.println(profcodeV);
//					}
//
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//			
//		});
		credits_f = new JTextField();
		
		input_panel.setBounds(0,50,600,170);
		
		sub_code.setBounds(15,10,75,25);
		code_f.setBounds(95,10,95,25);
		sub_name.setBounds(200,10,75,25);
		name_f.setBounds(280,10,95,25);
		subopen_y.setBounds(385,10,75,25);
		y_c.setBounds(465,10,95,25);
		
		open_sub.setBounds(15,40,75,25);
		dept_c.setBounds(95,40,95,25);
		opengrade.setBounds(200,40,75,25);
		opengrade_c.setBounds(280,40,95,25);
		open_semester.setBounds(385,40,75,25);
		semester_c.setBounds(465,40,95,25);
		
		class_hours.setBounds(15,70,75,25);
		hours_f.setBounds(95,70,95,25);
		sub_prof.setBounds(200,70,75,25);
		prof_c.setBounds(280,70,95,25);
		opening_credits.setBounds(385,70,75,25);
		credits_f.setBounds(465,70,95,25);
		
		input_panel.add(sub_code);
		input_panel.add(code_f);
		input_panel.add(sub_name);
		input_panel.add(name_f);
		input_panel.add(subopen_y);
		input_panel.add(y_c);
		input_panel.add(open_sub);
		input_panel.add(dept_c);
		input_panel.add(opengrade);
		input_panel.add(opengrade_c);
		input_panel.add(open_semester);
		input_panel.add(semester_c);
		input_panel.add(class_hours);
		input_panel.add(hours_f);
		input_panel.add(sub_prof);
		input_panel.add(prof_c);
		input_panel.add(opening_credits);
		input_panel.add(credits_f);
		add(input_panel);
	}
	void search() {
		search_panel = new JPanel(null);
		combo = new JComboBox(dbm);
		search_field = new JTextField();
		look_bt = new JButton("조회");
		lookall_bt = new JButton("전체조회");
		
		look_bt.addActionListener(this);
		lookall_bt.addActionListener(this);
		
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
		String header[] = {"교과코드","교과명","개설학과","개설년도","개설학년","개설학기","수업시수","담당교수","학점"};
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
				
				String code = ((String)table.getValueAt(srow, 0));
				code_f.setText(code);
				String subject = ((String)table.getValueAt(srow, 1));
				name_f.setText(subject);
				
				String year = ((String)table.getValueAt(srow, 3));
				y_c.setSelectedItem(year);
				
				String dept_code = ((String)table.getValueAt(srow, 2));
				dept_c.setSelectedItem(dept_code);
				
				String grade = ((String)table.getValueAt(srow, 4));
				opengrade_c.setSelectedItem(grade);
				
				String term = ((String)table.getValueAt(srow, 5));
				semester_c.setSelectedItem(term);
				
				String time = ((String)table.getValueAt(srow, 6));
				hours_f.setText(time);
				
				String prof_code = ((String)table.getValueAt(srow, 7));
				prof_c.setSelectedItem(prof_code);
				String credit = ((String)table.getValueAt(srow, 8));
				credits_f.setText(credit);
				
				
			}
			
		});
		table_panel.setBounds(5,275,575,230);
		
		add(table_panel);
	}
	void button() {
		button_panel = new JPanel(null);
		add_bt = new JButton("추가");
		modify_bt = new JButton("수정");
		del_bt = new JButton("삭제");
		end_bt = new JButton("종료");
		
		add_bt.addActionListener(this);
		modify_bt.addActionListener(this);
		del_bt.addActionListener(this);
		end_bt.addActionListener(this);
		
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
	void boxm1() {
		try {
			String quary = "SELECT * FROM depart";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				String dept = rs.getString(2);

				dept_c.addItem(dept);
	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	void boxprof() {
		prof_c.addItem("교수선택");
		try {
			String quary = "SELECT * FROM prof";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				String prof = rs.getString(2);

				prof_c.addItem(prof);
				;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	void getListAll() { // 완
		try {
			dtm.setRowCount(0);
			String quary = "SELECT * FROM subject";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				
				String code = rs.getString(1);
				String subject = rs.getString(2);
				
				String dept_code = rs.getString(3);
				quary = "select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, dept_code);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					dept_code = rs1.getString(2);
				}

				String year = rs.getString(4);
				String grade = rs.getString(5);
				String term = rs.getString(6);
				String time = rs.getString(7);
				
				String prof_code = rs.getString(8);
				quary = "select * from prof where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, prof_code);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					prof_code = rs1.getString(2);
				}
				
				String credit = rs.getString(9);

				Object[] rowData = { code, subject, dept_code, year, grade, term, time, prof_code, credit};
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int regist(String code) {
		int result = 0;
		String sql = "SELECT * FROM subject where code = '" + code + "'";
		try {
			pstmt = con.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JOptionPane.showMessageDialog(this, "이미 등록된 과목입니다!", "과목코드 확인", JOptionPane.YES_OPTION);
				return result;
			}
			sql = "INSERT INTO subject (code, subject, dept_code, year, grade, term, time, prof_code, credit) values(?,?,?,?,?,?,?,?,?)";
			pstmt = con.conn.prepareStatement(sql);

			pstmt.setString(1, code_f.getText());
			pstmt.setString(2, name_f.getText());
			
			pstmt.setString(3, deptcode);
			pstmt.setString(4, y_c.getSelectedItem().toString());
			pstmt.setString(5, opengrade_c.getSelectedItem().toString());
			pstmt.setString(6, semester_c.getSelectedItem().toString());
			
			pstmt.setString(7, hours_f.getText());
			pstmt.setString(8, profcodeV);
			pstmt.setString(9, credits_f.getText());
			
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	void searchM() {
		String s = dbm[combo.getSelectedIndex()];

		String deptcode = null, deptname = null, profname =null, profcode = null;
		String codeV = null;
		try {
			if (search_field.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "조회할 내용 입력하세요?", "조회", JOptionPane.YES_OPTION);
				search_field.requestFocus();
				return;
			}
			if (s.equals("dept_code")) {
				String query = "select * from depart where dept =? or code=?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, search_field.getText());
				pstmt.setString(2, search_field.getText());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					deptcode = rs.getString(1);
				}

				query = "select * from subject where " + s + " = '" + deptcode + "'";
				pstmt = con.conn.prepareStatement(query);
			}if(s.equals("prof_code")) {
				String query = "select * from prof where name =? or code=?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, search_field.getText());
				pstmt.setString(2, search_field.getText());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					profcode = rs.getString(1);
				}

				query = "select * from subject where " + s + " = '" + profcode + "'";
				pstmt = con.conn.prepareStatement(query);
			}
			
			else {
				String query = "SELECT * FROM subject WHERE " + s + " = '" + search_field.getText() + "'";
				pstmt = con.conn.prepareStatement(query);
			}

			rs = pstmt.executeQuery();
			dtm.setRowCount(0);
			while (rs.next()) {
				String code = rs.getString(1);
				String subject = rs.getString(2);
				String dept_code = rs.getString(3);
				String sql = "Select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(sql);
				pstmt.setString(1, dept_code);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					dept_code = rs1.getString(2);
				}

				String year = rs.getString(4);
				String grade = rs.getString(5);
				String term = rs.getString(6);
				String time = rs.getString(7);
				
				String prof_code = rs.getString(8);
				sql = "Select * from prof where code = ?";
				pstmt = con.conn.prepareStatement(sql);
				pstmt.setString(1, prof_code);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					prof_code = rs1.getString(2);
				}
				
				String credit = rs.getString(9);

				Object[] rowData = { code, subject, dept_code, year, grade, term, time, prof_code, credit};
				dtm.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void del() {// 완

		int result = 0;
		String sql = "DELETE FROM subject where code = ?";
		System.out.println("삭제혀");
		try {
			pstmt = con.conn.prepareStatement(sql);
			pstmt.setString(1, code_f.getText());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void update() {
		
		String sql1 = "update subject set subject = ?, dept_code = ?, year = ?, grade = ?, term = ?, time = ?, prof_code = ?, credit = ? where code = ?";

		try {
			pstmt = con.conn.prepareStatement(sql1);

			pstmt.setString(9, code_f.getText());
			pstmt.setString(1, name_f.getText());
			pstmt.setString(2, deptcode);
			
			pstmt.setString(3, y_c.getSelectedItem().toString());
			pstmt.setString(4, opengrade_c.getSelectedItem().toString());
			
			pstmt.setString(5, semester_c.getSelectedItem().toString());
			pstmt.setString(6, hours_f.getText());
			pstmt.setString(7, profcodeV);
			pstmt.setString(8, credits_f.getText());

			int a = pstmt.executeUpdate();

			if (a != 0) {
				JOptionPane.showMessageDialog(getParent(), "수정성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "수정실패!");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==look_bt) {
			searchM();
		}
		if(e.getSource()==lookall_bt) {
			getListAll();
		}
		if(e.getSource()==add_bt) {
			int result = regist(code_f.getText());

			if (result != 0) {
				JOptionPane.showMessageDialog(getParent(), "등록성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "등록실패!");
			}
		}
		if(e.getSource()==del_bt) {
			del();
			getListAll();
		}
		if(e.getSource()==modify_bt) {
			update();
			getListAll();
		}
		if(e.getSource()==end_bt) {
			dispose();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Subject_M();
	}

}
