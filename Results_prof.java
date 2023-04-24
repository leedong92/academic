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

import javax.swing.DefaultCellEditor;
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
import javax.swing.table.TableColumn;

public class Results_prof extends JFrame implements ActionListener {
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, prof_code, prof_name, open_y, open_grade, open_semester;
	JTextField code_field, name_field, search_field;
	JComboBox combo, y_c, grade_c, semester_c, hak_c;
	String menu[] = { "검색할 교과목코드 선택하세요", "code" };
	String gc[] = { "1", "2", "3", "4" };
	String yc[] = { "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028" };
	String s[] = { "1", "2", "3", "4" };
	String hak[] = { "A+", "A", "B+", "B", "C+", "C", "D+", "D", "E+", "E", "F" };
	JButton look_bt, add_bt, end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Font fon1 = new Font("Dialog", Font.PLAIN, 20);
	Connect con;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	String category, y_combo,grade_combo, semester_combo;
	String subjcode;
	String subject;

	PreparedStatement pstmt = null;
	int srow;

	public Results_prof(String a) {
		setTitle("성적 관리");
		setLayout(null);
		setBounds(50, 50, 600, 650);
		con = new Connect();
		title();

		input();
		code_field.setText(a);
		textadd();
		search();
		boxx();
		
		table();
		button();

		setResizable(false);
		setVisible(true);
	}

	void title() {
		title_panel = new JPanel(null);
		title_panel.setBackground(Color.black);
		title = new JLabel("성적관리");
		title.setFont(fon1);
		title.setForeground(Color.white);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(5, 5, 600, 30);
		title_panel.add(title);
		title_panel.setBounds(0, 0, 600, 50);
		add(title_panel);
	}

	void input() {
		input_panel = new JPanel(null);

		prof_code = new JLabel("교수코드");

		prof_name = new JLabel("교수이름");
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
				grade_combo = grade_c.getSelectedItem().toString();
			}
			
		});
		semester_c = new JComboBox(s);
		semester_c.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				semester_combo = semester_c.getSelectedItem().toString();
			}
			
		});

		input_panel.setBounds(0, 50, 600, 170);

		prof_code.setBounds(15, 20, 60, 25);
		code_field.setBounds(80, 20, 90, 25);
		prof_name.setBounds(210, 20, 60, 25);
		name_field.setBounds(275, 20, 90, 25);
		open_y.setBounds(405, 20, 60, 25);
		y_c.setBounds(470, 20, 90, 25);

		open_grade.setBounds(15, 50, 60, 25);
		grade_c.setBounds(80, 50, 90, 25);
		open_semester.setBounds(210, 50, 60, 25);
		semester_c.setBounds(275, 50, 90, 25);

		input_panel.add(prof_code);
		input_panel.add(prof_name);
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
		
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				category = combo.getSelectedItem().toString();
				try {
					String sql = "select * from subject where subname=?";
					pstmt = con.conn.prepareStatement(sql);
					pstmt.setString(1, category);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						subjcode = rs.getString(1);
						
					}
					search_field.setText(subjcode);
					//search_field.setText(subjcode);
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

		search_panel.setBounds(0, 220, 600, 50);
		combo.setBounds(10, 10, 170, 30);
		search_field.setBounds(185, 10, 150, 30);
		look_bt.setBounds(480, 10, 100, 30);

		search_panel.add(combo);
		search_panel.add(search_field);
		search_panel.add(look_bt);

		add(search_panel);
	}

	void table() {

		String header[] = { "학번", "이름", "출석", "레포트", "중간", "기말", "가산점", "합계", "학점" };
		Object rowdata[][] = {};
		dtm = new DefaultTableModel(null, header);
		table = new JTable(dtm);

		hak_c = new JComboBox(hak);
		TableColumn hh = table.getColumnModel().getColumn(8);
		hh.setCellEditor(new DefaultCellEditor(hak_c));

		table_panel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				String attended = table.getValueAt(srow, 2).toString();
				String report = table.getValueAt(srow, 3).toString();
				String middle = table.getValueAt(srow, 4).toString();
				String fainal = table.getValueAt(srow, 5).toString();
				String added = table.getValueAt(srow, 6).toString();
				Integer sum = Integer.parseInt(attended)+Integer.parseInt(report)+Integer.parseInt(middle)+
						Integer.parseInt(fainal)+Integer.parseInt(added);
				table.setValueAt(sum.toString(), srow, 7);
			}

		});
		table.setAutoCreateRowSorter(true);
		table_panel.setBounds(5, 275, 575, 230);

		add(table_panel);
	}

	void button() {
		button_panel = new JPanel(null);
		add_bt = new JButton("저장");
		end_bt = new JButton("종료");

		add_bt.addActionListener(this);
		end_bt.addActionListener(this);

		button_panel.setBounds(0, 510, 600, 140);
		add_bt.setBounds(295, 30, 135, 50);
		end_bt.setBounds(435, 30, 135, 50);

		button_panel.add(add_bt);
		button_panel.add(end_bt);

		add(button_panel);
	}

	void getListAll() { // 완
		try {
			this.dtm.setRowCount(0);

			String quary = "select * from result where year = ? and grade =? and term = ? and sub_code = ? and prof_code=?";
			pstmt = con.conn.prepareStatement(quary);
			pstmt.setString(1, y_combo);
			pstmt.setString(2, grade_combo);
			pstmt.setString(3, semester_combo);
			pstmt.setString(4, subjcode);
			pstmt.setString(5, code_field.getText());
			rs = pstmt.executeQuery();
			
			String std_name = null;

			while (rs.next()) {
				String std_code = rs.getString(3);			
				String attended = rs.getString(11);
				String report = rs.getString(10);
				String middle = rs.getString(8);
				String fainal = rs.getString(9);
				String added = rs.getString(12); // 가산점
				String sum = rs.getString(13);
				String grade_value = rs.getString(14);

				quary = "SELECT * FROM student where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, std_code);
				rs1 = pstmt.executeQuery();
				while(rs1.next()) {
					std_name = rs1.getString(2);
				}
				Object[] rowData = { std_code, std_name, attended, report, middle, fainal, added, sum, grade_value };
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int add() {
		int result = 0;
		int cnt = 0;

		try {
			while (cnt < table.getRowCount()) {
				String query = "update result set middle=?, fainal=?, report=?, attended=?, added=?, sum=?, grade_value=? where std_code =? and sub_code =?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, (String) table.getValueAt(cnt, 4));
				pstmt.setString(2, (String) table.getValueAt(cnt, 5));
				pstmt.setString(3, (String) table.getValueAt(cnt, 3));
				pstmt.setString(4, (String) table.getValueAt(cnt, 2));
				pstmt.setString(5, (String) table.getValueAt(cnt, 6));
				pstmt.setString(6, (String) table.getValueAt(cnt, 7));
				pstmt.setString(7, (String) table.getValueAt(cnt, 8));
				
				pstmt.setString(8, (String) table.getValueAt(cnt, 0));
				pstmt.setString(9, search_field.getText());

				result = pstmt.executeUpdate();
				cnt++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//검색창 과목코드 리스트 가져오기
	void boxx() {
		try {
			String sql = "select distinct * from result where prof_code = ?";
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
	
	//이름 필드에 교수 이름 넣기
	void textadd() {	
		String query = "SELECT * FROM prof where code = ?";			
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
		if (e.getSource() == look_bt) {
			getListAll();
		}
		if (e.getSource() == add_bt) {
			int result=add();
			if (result != 0) {
				JOptionPane.showMessageDialog(getParent(), "등록성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "등록실패!");
			}
			
		}
		if (e.getSource() == end_bt) {
			dispose();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Results_prof("p101");
	}

}
