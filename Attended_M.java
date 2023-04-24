package univ;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Attended_M extends JFrame implements ActionListener {
	Attended_M atd;
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, std_code, name, dept;
	JTextField std_f, name_f, dept_f = null, search_field;
	JComboBox combo;
	String dbm[] = { "개설학과 선택하세요", "컴퓨터공학", "방사선", "물리치료", "사무자동화", "간호1" };
	JButton look_bt, lookall_bt, add_bt, modify_bt, del_bt, end_bt;
	JScrollPane table_panel, table_panel2;
	DefaultTableModel dtm, dtm2;
	JTable table, table2;
	Font fon1 = new Font("Dialog", Font.PLAIN, 20);
	Connect con;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	String a;
	PreparedStatement pstmt;
	int srow;

	public Attended_M(String a) {
		setTitle("수강 신청");
		setLayout(null);
		setBounds(50, 50, 600, 650);
		this.a = a;
		con = new Connect();
		
		
		title();
		input();
		textadd();
		search();
		table();
		table2();
		button();

		setResizable(false);
		setVisible(true);
	}

	void title() {
		title_panel = new JPanel(null);
		title_panel.setBackground(Color.black);
		title = new JLabel("수강신청");
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

		std_code = new JLabel("학생코드");
		name = new JLabel("이름");
		dept = new JLabel("학과");

		std_f = new JTextField(a);
		name_f = new JTextField();
		dept_f = new JTextField();

		input_panel.setBounds(0, 50, 600, 170);

		std_code.setBounds(15, 10, 65, 25);
		std_f.setBounds(85, 10, 90, 25);

		name.setBounds(205, 10, 55, 25);
		name_f.setBounds(270, 10, 90, 25);

		dept.setBounds(390, 10, 55, 25);
		dept_f.setBounds(455, 10, 90, 25);

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
		combo = new JComboBox(dbm);
		search_field = new JTextField();
		look_bt = new JButton("조회");
		lookall_bt = new JButton("전체조회");

		lookall_bt.addActionListener(this);
		look_bt.addActionListener(this);

		search_panel.setBackground(Color.GRAY);
		look_bt.setBackground(Color.black);
		look_bt.setForeground(Color.white);
		lookall_bt.setBackground(Color.black);
		lookall_bt.setForeground(Color.white);

		search_panel.setBounds(0, 220, 600, 50);
		combo.setBounds(10, 10, 170, 30);
		search_field.setBounds(185, 10, 150, 30);
		look_bt.setBounds(375, 10, 100, 30);
		lookall_bt.setBounds(480, 10, 100, 30);

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
		table_panel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				super.mouseClicked(e);
				if(std_f.getText().length()==0) {
					JOptionPane.showMessageDialog(atd, "학생코드 입력은 필수입니다, 학번을 입력하세요","학번입력",JOptionPane.WARNING_MESSAGE);
					std_f.requestFocus();
					return;
				}
				srow = table.getSelectedRow();
				String sub_code = (String)table.getValueAt(srow, 0);
				String std_code = std_f.getText();
				
				String subjcode = null;
				String deptcode = null;
				String profcode = "";
				String year = "";
				String grade = "";
				String term = "";
				
				String query = "select * from attended where std_code = ? and sub_code = ?";
				try {
					pstmt = con.conn.prepareStatement(query);
					pstmt.setString(1, std_code);
					pstmt.setString(2, sub_code);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						JOptionPane.showMessageDialog(atd, "이미 수강신청되어 있습니다. 다시 선택하세요?" , "수강신청", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				int result = 0;
				query = "select * from subject where code = '"+ sub_code + "'";
				try {
					pstmt = con.conn.prepareStatement(query);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						subjcode = rs.getString(1);
						deptcode = rs.getString(3);
						year = rs.getString(4);
						grade = rs.getString(5);
						term = rs.getString(6);
						profcode = rs.getString(8);
					}
					
					query = "insert into attended(idx, std_code, prof_code, sub_code, year) values(?,?,?,?,?)";
					pstmt = con.conn.prepareStatement(query);
					pstmt.setString(1, null);
					pstmt.setString(2, std_code);
					pstmt.setString(3, profcode);
					pstmt.setString(4, sub_code);
					pstmt.setString(5, year);
					
					result = pstmt.executeUpdate();
					
					query = "INSERT INTO result(idx, prof_code, std_code, sub_code, year, grade, term, middle, fainal, report, attended, added, sum, grade_value) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					pstmt = con.conn.prepareStatement(query);
					pstmt.setString(1, null);
					pstmt.setString(2, profcode);
					pstmt.setString(3, std_code);
					pstmt.setString(4, subjcode);
					pstmt.setString(5, year);
					pstmt.setString(6, grade);
					pstmt.setString(7, term);
					pstmt.setInt(8, 0);
					pstmt.setString(9, "0");
					pstmt.setString(10, "0");
					pstmt.setString(11, "0");
					pstmt.setString(12, "0");
					pstmt.setString(13, "0");
					pstmt.setString(14, "0");
					result = pstmt.executeUpdate();					
					
					getListAllsubject();
					getListAllAttended();
				
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		table_panel.setBounds(5,275,575,115);
		
		add(table_panel);
	}

	void table2() {
		String header2[] = { "교과목코드", "교과목명", "개설학과", "수업시수", "담당교수", "학점" };
		Object rowdata2[][] = {};
		dtm2 = new DefaultTableModel(null, header2);
		table2 = new JTable(dtm2);
		table_panel2 = new JScrollPane(table2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table2.setAutoCreateRowSorter(true);
		table_panel2.setBounds(5, 395, 575, 115);

		add(table_panel2);
	}

	void button() {
		button_panel = new JPanel(null);
		
		del_bt = new JButton("삭제");
		end_bt = new JButton("종료");
		
		del_bt.addActionListener(this);
		end_bt.addActionListener(this);

		button_panel.setBounds(0, 510, 600, 140);
		
		del_bt.setBounds(295, 30, 135, 50);
		end_bt.setBounds(435, 30, 135, 50);

		
		button_panel.add(del_bt);
		button_panel.add(end_bt);

		add(button_panel);
	}

	void getListAllsubject() {
		try {
			dtm.setRowCount(0);
			String query = "select * from subject left outer join (select * from attended where std_code = ?) as att on (subject.code = att.sub_code)";
			pstmt = con.conn.prepareStatement(query);
			pstmt.setString(1, std_f.getText());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String subjcode = rs.getString(1);
				String subject = rs.getString(2);
				String deptcode = rs.getString(3);
				String year = rs.getString(4);
				String grade = rs.getString(5);
				String term = rs.getString(6);
				String time = rs.getString(7);
				String profcode = rs.getString(8);
				String credit = rs.getString(9);

				String att_subj_code = rs.getString(13);

				if (att_subj_code != null) {
					continue;
				}
				String deptname = "", profname = "";

				query = "select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, deptcode);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					deptname = rs1.getString(2);
				}

				query = "select * from prof where code =?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, profcode);
				rs2 = pstmt.executeQuery();
				while (rs2.next()) {
					profname = rs2.getString(2);
				}
				
				Object[] rowdata = { subjcode, subject, deptname, time, profname, credit };
				dtm.addRow(rowdata);				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void getListAllAttended() {
		dtm2.setRowCount(0);

		try {
			String query = "select * from attended where std_code = ?";
			pstmt = con.conn.prepareStatement(query);
			pstmt.setString(1, std_f.getText());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String index = rs.getString(1);
				String std_code = rs.getString(2);
				String prof_code = rs.getString(3);
				String sub_code = rs.getString(4);
				String year = rs.getString(5);

				String deptcode = null;
				String subject = null;
				String deptname = "";
				String profname = "";
				String grade = "";
				String term = "";
				String time = "";
				String credit = "";

				query = "select * from subject where code =?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, sub_code);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					sub_code = rs1.getString(1);
					subject = rs1.getString(2);
					deptname = rs1.getString(3);
					profname = rs1.getString(4);
					grade = rs1.getString(5);
					term = rs1.getString(6);
					time = rs1.getString(7);
					credit = rs1.getString(8);
				}
				query = "select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, deptcode);
				rs2 = pstmt.executeQuery();

				while (rs2.next()) {
					deptname = rs2.getString(2);
				}

				query = "select * from prof where code = ?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, prof_code);
				rs2 = pstmt.executeQuery();

				while (rs2.next()) {
					profname = rs2.getString(2);
				}

				Object[] rowdata = { sub_code, subject, deptname, term, profname, credit };
				dtm2.addRow(rowdata);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int delete(String subjcode) {
		int result = 0;

		if (std_code.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "학번입력은 필수입니다. 학번을 입력하세요 ? ", "학번입력!!!", JOptionPane.WARNING_MESSAGE);
			std_code.requestFocus();
			return result;
		}

		String query = "DELETE FROM attended where std_code = ? and sub_code = ?";

		try {
			pstmt = con.conn.prepareStatement(query);
			pstmt.setString(1, std_code.getText());
			pstmt.setString(2, subjcode);
			result = pstmt.executeUpdate();

			query = "DELETE FROM results where std_code = ? and sub_code=?";
			pstmt = con.conn.prepareStatement(query);
			pstmt.setString(1, std_code.getText());
			pstmt.setString(2, subjcode);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		getListAllsubject();
		getListAllAttended();
		return result;
	}
	void textadd() {
		String deptcode = null;
		String deptname = null;
		
		
		
		String query = "SELECT * FROM student where code = ?";			
		try {
			pstmt = con.conn.prepareStatement(query);
			pstmt.setString(1, a);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {			
				deptcode = rs.getString(10);	// 학과코드	
				name_f.setText(rs.getString(2));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		
		String query2 = "SELECT * FROM depart where code = ?";			
		try {
			pstmt = con.conn.prepareStatement(query2);
			pstmt.setString(1, deptcode);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {			
				deptname = rs.getString(2);	// 학과명
				System.out.println(deptname);
				dept_f.setText(deptname);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	void searchM() {
		String s = dbm[combo.getSelectedIndex()];
		
		try {
			String query = "select * from subject where dept_code = '"+ dept_f.getText() +"'order by code";
			pstmt = con.conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String code = rs.getString(1);
				String subject = rs.getString(2);
				String dept_code = rs.getString(3);
				String year = rs.getString(4);
				String grade = rs.getString(5);
				String term = rs.getString(6);
				String time = rs.getString(7);
				String prof_code = rs.getString(8);
				String credit = rs.getString(9);
				
				String deptname = "", profname="";
				
				query = "select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, dept_code);
				rs1 = pstmt.executeQuery();
				
				while(rs1.next()) {
					deptname = rs1.getString(2);
				}
				
				query = "select * from prof where code = ?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, prof_code);
				rs2 = pstmt.executeQuery();
				
				while (rs2.next()) {
					profname = rs2.getString(2);
				}
				
				Object [] rowData = {code, subject, deptname, time, profname, credit};
				dtm.addRow(rowData);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == lookall_bt) {
			getListAllsubject();
			getListAllAttended();
		}
		if (e.getSource() == look_bt) {
			searchM();
		}
		if (e.getSource() == del_bt) {
			srow = table2.getSelectedRow();

			if (table2.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "삭제할 행 선택하세요? ", "삭제!!!", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String subjcode = (String) table2.getValueAt(srow, 0); // 교과목코드
			String subjname = (String) table2.getValueAt(srow, 1);
			if (JOptionPane.showConfirmDialog(getParent(), "선택한 " + subjname + " 수강신청을 삭제 하시겠습니까? ") == 0) {
				int result = delete(subjcode);

				if (result != 0) {
					JOptionPane.showMessageDialog(this, "삭제 성공", "삭제!!!", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if (e.getSource() == end_bt) {
			dispose();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Attended_M("s101");
	}

}
