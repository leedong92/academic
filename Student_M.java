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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Student_M extends JFrame implements ActionListener {

	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, std_code, std_name, jumin_num, jumin_bar, addr, phone, phone_bar, phone_bar2, callnum, year, h_school,
			h_school_data, depart, prof, gender;
	JTextField std_field, std_name_field, jumin_field, jumin_bar_field, addr_field, phone_field, bar_field, bar2_field,
			callnum_field, year_field, h_school_field, h_school_data_field, search_field;
	JComboBox combo, departbox, profbox;

	String menu[] = { "학생", "주소", "학과", "교수", "졸업고교", "성별", "검색할 카테고리 선택하세요" };
	String dbm[] = { "code", "addr", "dept_code", "prof_code", "highschool", "gender" };

	JButton look_bt, lookall_bt, add_bt, modify_bt, del_bt, end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Font fon1 = new Font("Dialog", Font.PLAIN, 20);
	JRadioButton genderM_radio, genderF_radio;
	Connect con;
	ResultSet rs = null;
	ResultSet rs1 = null;

	PreparedStatement pstmt = null;
	int srow = -1;
	String genderS = null;
	String category = null;
	String deptcode = null;
	String deptname = null;
	String profcategory = null;
	String profcodeV = null;
	String profname = null;

	public Student_M() {
		setTitle("학생관리");
		setLayout(null);
		setBounds(50, 50, 600, 650);

		con = new Connect();
		title();
		input();
		search();
		table();
		button();

		setResizable(false);
		setVisible(true);
	}

	void title() {
		title_panel = new JPanel(null);
		title_panel.setBackground(Color.black);
		title = new JLabel("학 생 관 리");
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
		std_code = new JLabel("학번");
		std_name = new JLabel("이름");
		jumin_num = new JLabel("주민번호");
		jumin_bar = new JLabel("-");
		addr = new JLabel("주소");
		phone = new JLabel("휴대폰");
		phone_bar = new JLabel("-");
		phone_bar2 = new JLabel("-");
		callnum = new JLabel("전화번호");

		year = new JLabel("입학년도");
		h_school = new JLabel("졸업고교");
		h_school_data = new JLabel("고교졸업년도");

		depart = new JLabel("학과");
		prof = new JLabel("지도교수");
		gender = new JLabel("성별");

		std_field = new JTextField();
		std_name_field = new JTextField();
		jumin_field = new JTextField();
		jumin_bar_field = new JTextField();
		addr_field = new JTextField();
		phone_field = new JTextField();
		bar_field = new JTextField();
		bar2_field = new JTextField();
		callnum_field = new JTextField();

		year_field = new JTextField();
		h_school_field = new JTextField();
		h_school_data_field = new JTextField();

		departbox = new JComboBox();
		boxm1();

		profbox = new JComboBox();
		boxprof();
		ButtonGroup bgroup = new ButtonGroup();
		genderM_radio = new JRadioButton("남자");
		genderF_radio = new JRadioButton("여자");
		bgroup.add(genderF_radio);
		bgroup.add(genderM_radio);

		genderM_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				genderS = genderM_radio.getActionCommand();
			}

		});
		genderF_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				genderS = genderF_radio.getActionCommand();
			}

		});

		departbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				category = (String) departbox.getSelectedItem();
				search_field.requestFocus();

				try {
					String sql = "select * from depart where dept = ?";
					pstmt = con.conn.prepareStatement(sql);
					pstmt.setString(1, category);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						deptcode = rs.getString(1);
						deptname = rs.getString(2);
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		profbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				profcategory = (String) profbox.getSelectedItem();
				//search_field.requestFocus();

				try {
					String sql = "select * from prof where name = ?";
					pstmt = con.conn.prepareStatement(sql);
					pstmt.setString(1, profcategory);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						profcodeV = rs.getString(1);
						profname = rs.getString(2);
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		input_panel.setBounds(0, 50, 600, 170);

		std_code.setBounds(15, 10, 50, 25);
		std_field.setBounds(75, 10, 80, 25);
		std_name.setBounds(180, 10, 50, 25);
		std_name_field.setBounds(215, 10, 80, 25);
		jumin_num.setBounds(310, 10, 50, 25);
		jumin_field.setBounds(365, 10, 90, 25);
		jumin_bar.setBounds(460, 10, 5, 25);
		jumin_bar_field.setBounds(470, 10, 90, 25);

		addr.setBounds(15, 40, 50, 25);
		addr_field.setBounds(75, 40, 485, 25);

		phone.setBounds(15, 70, 50, 25);
		phone_field.setBounds(75, 70, 70, 25);
		phone_bar.setBounds(150, 70, 5, 25);
		bar_field.setBounds(160, 70, 70, 25);
		phone_bar2.setBounds(235, 70, 5, 25);
		bar2_field.setBounds(245, 70, 70, 25);
		callnum.setBounds(350, 70, 50, 25);
		callnum_field.setBounds(440, 70, 120, 25);

		year.setBounds(15, 100, 50, 25);
		year_field.setBounds(75, 100, 80, 25);
		h_school.setBounds(180, 100, 50, 25);
		h_school_field.setBounds(235, 100, 90, 25);
		h_school_data.setBounds(350, 100, 90, 25);
		h_school_data_field.setBounds(440, 100, 120, 25);

		depart.setBounds(15, 130, 50, 25);
		departbox.setBounds(75, 130, 100, 25);
		prof.setBounds(200, 130, 55, 25);
		profbox.setBounds(260, 130, 100, 25);
		gender.setBounds(400, 130, 50, 25);
		genderM_radio.setBounds(450, 130, 55, 25);
		genderF_radio.setBounds(510, 130, 55, 25);

		input_panel.add(std_code);
		input_panel.add(std_field);
		input_panel.add(std_name);
		input_panel.add(std_name_field);
		input_panel.add(jumin_num);
		input_panel.add(jumin_field);
		input_panel.add(jumin_bar);
		input_panel.add(jumin_bar_field);
		input_panel.add(addr);
		input_panel.add(addr_field);
		input_panel.add(phone);
		input_panel.add(phone_field);
		input_panel.add(phone_bar);
		input_panel.add(bar_field);
		input_panel.add(phone_bar2);
		input_panel.add(bar2_field);
		input_panel.add(callnum);
		input_panel.add(callnum_field);

		input_panel.add(year);
		input_panel.add(year_field);
		input_panel.add(h_school);
		input_panel.add(h_school_field);
		input_panel.add(h_school_data);
		input_panel.add(h_school_data_field);

		input_panel.add(depart);
		input_panel.add(departbox);
		input_panel.add(prof);
		input_panel.add(profbox);
		input_panel.add(gender);
		input_panel.add(genderM_radio);
		input_panel.add(genderF_radio);

		add(input_panel);
	}

	void search() {
		search_panel = new JPanel(null);
		combo = new JComboBox(menu);
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
		String header[] = { "학번", "이름", "주소", "주민등록번호", "휴대폰", "전화번호", "입학년도", "졸업고교", "고교졸업년도", "학과", "지도교수", "성별" };
		Object rowdata[][] = {};
		dtm = new DefaultTableModel(null, header);
		table = new JTable(dtm);
		table_panel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				srow = table.getSelectedRow();

				String code = ((String) table.getValueAt(srow, 0));
				std_field.setText(code);
				String name = ((String) table.getValueAt(srow, 1));
				std_name_field.setText(name);
				String addr = ((String) table.getValueAt(srow, 2));
				addr_field.setText(addr);

				String jumin = ((String) table.getValueAt(srow, 3));
				jumin_field.setText(jumin.substring(0, 6));
				jumin_bar_field.setText(jumin.substring(7, 14));

				String phone = ((String) table.getValueAt(srow, 4));
				phone_field.setText(phone.substring(0, 3));
				bar_field.setText(phone.substring(4, 8));
				bar2_field.setText(phone.substring(9, 13));

				String call = ((String) table.getValueAt(srow, 5));
				String a = call.substring(0, 3);
				String b = call.substring(4, 7);
				String c = call.substring(8, 12);
				callnum_field.setText(a + b + c);

				String year = ((String) table.getValueAt(srow, 6));
				year_field.setText(year);

				String school = ((String) table.getValueAt(srow, 7));
				h_school_field.setText(school);
				String schooldata = ((String) table.getValueAt(srow, 8));
				h_school_data_field.setText(schooldata);

				String depart = ((String) table.getValueAt(srow, 9));
				departbox.setSelectedItem(depart);

				String profcode = ((String) table.getValueAt(srow, 10));
				profbox.setSelectedItem(profname);

				String gender = ((String) table.getValueAt(srow, 11));
				if (gender.equals("남자")) {
					genderM_radio.setSelected(true);
				} else {
					genderF_radio.setSelected(true);
				}
			}

		});
		table_panel.setBounds(5, 275, 575, 230);

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

		button_panel.setBounds(0, 510, 600, 140);
		add_bt.setBounds(15, 30, 135, 50);
		modify_bt.setBounds(155, 30, 135, 50);
		del_bt.setBounds(295, 30, 135, 50);
		end_bt.setBounds(435, 30, 135, 50);

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

				departbox.addItem(dept);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void boxprof() {
		try {
			String quary = "SELECT * FROM prof";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				String prof = rs.getString(2);

				profbox.addItem(prof);
				;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void getListAll() { // 완
		try {
			this.dtm.setRowCount(0);

			String quary = "SELECT * FROM student";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				String code = rs.getString(1);
				String name = rs.getString(2);
				String addr = rs.getString(3);

				String jumin = rs.getString(4);
				jumin = jumin.substring(0, 6) + "-" + jumin.substring(6, 13);
				
				String phone = rs.getString(5);
				phone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11);
				String call = rs.getString(6);
				call = call.substring(0, 3) + ")" + call.substring(3, 6) + "-" + call.substring(6, 10);
				String uin_year = rs.getString(7);
				String highschool = rs.getString(8);
				String end_year = rs.getString(9);

				String deptcode = rs.getString(10);
				quary = "select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, deptcode);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					deptname = rs1.getString(2);
				}

				String profcode = rs.getString(11);
				quary = "select * from prof where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, profcode);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					profname = rs1.getString(2);
				}

				String gender = rs.getString(12);

				Object[] rowData = { code, name, addr, jumin, phone, call, uin_year, highschool, end_year, deptname,
						profname, gender };
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void searchM() {
		String s = dbm[combo.getSelectedIndex()];

		String deptcode = null, deptname = null, profname =null, profcode = null;

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

				query = "select * from student where " + s + " = '" + deptcode + "'";
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

				query = "select * from student where " + s + " = '" + profcode + "'";
				pstmt = con.conn.prepareStatement(query);
			}
			else {
				String query = "SELECT * FROM student WHERE " + s + " = '" + search_field.getText() + "'";
				pstmt = con.conn.prepareStatement(query);
			}

			rs = pstmt.executeQuery();
			dtm.setRowCount(0);
			while (rs.next()) {
				String code = rs.getString(1);
				String name = rs.getString(2);
				String addr = rs.getString(3);

				String jumin = rs.getString(4);
				jumin = jumin.substring(0, 6) + "-" + jumin.substring(6, 13);
				String phone = rs.getString(5);
				phone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" +
				phone.substring(7, 11);
				String callnum = rs.getString(6);
				callnum = callnum.substring(0, 3) + ")" + callnum.substring(3, 6) + "-" +callnum.substring(6, 10);
				String uinyear = rs.getString(7);
				String highschool = rs.getString(8);
				
				String end_year = rs.getString(9);
				
				String dept_code = rs.getString(10);
				String sql = "Select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(sql);
				pstmt.setString(1, dept_code);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					dept_code = rs1.getString(2);
				}
				
				String prof_code = rs.getString(11);
				sql = "Select * from prof where code = ?";
				pstmt = con.conn.prepareStatement(sql);
				pstmt.setString(1, prof_code);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					prof_code = rs1.getString(2);
				}
				
				String gender = rs.getString(12);

				Object[] rowData = { code, name, addr, jumin, phone, callnum, uinyear, highschool, end_year, dept_code, prof_code, gender };
				dtm.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int regist(String code) {
		int result = 0;
		String sql = "SELECT * FROM student where code = '" + code + "'";
		try {
			pstmt = con.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JOptionPane.showMessageDialog(this, "이미 등록된 학생입니다!", "학생코드 확인", JOptionPane.YES_OPTION);
				return result;
			}

			sql = "INSERT INTO student (code,name,addr,jumin,phone,callnum,uinyear,highschool,end_year,dept_code,prof_code,gender) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.conn.prepareStatement(sql);

			pstmt.setString(1, std_field.getText());
			pstmt.setString(2, std_name_field.getText());
			pstmt.setString(3, addr_field.getText());
			// std_field, std_name_field, jumin_field, jumin_bar_field, addr_field,
			// phone_field, bar_field, bar2_field,
			// callnum_field, year_field, h_school_field, h_school_data_field

//			String juminf = jumin_field.getText();
//			String juminb = jumin_bar_field.getText();
//			String jumin = juminf + juminb;
			pstmt.setString(4, jumin_field.getText() + jumin_bar_field.getText());

			String phonef = phone_field.getText();
			String phonem = bar_field.getText();
			String phoneb = bar2_field.getText();
			String phone = phonef + phonem + phoneb;
			pstmt.setString(5, phone);

			pstmt.setString(6, callnum_field.getText());

			pstmt.setString(7, year_field.getText());
			pstmt.setString(8, h_school_field.getText());
			// pstmt.setString(9, (String)departbox.getSelectedItem());
			pstmt.setString(9, h_school_data_field.getText());
			pstmt.setString(10, deptcode);
			// pstmt.setString(9, category);
			pstmt.setString(11, profcodeV);
			pstmt.setString(12, genderS);
			System.out.println(deptcode);
			System.out.println(profcodeV);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	void del() {// 완

		int result = 0;
		String sql = "DELETE FROM student where code = ?";
		System.out.println("삭제혀");
		try {
			pstmt = con.conn.prepareStatement(sql);
			pstmt.setString(1, std_field.getText());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void update() {
		//std_field, std_name_field, jumin_field, jumin_bar_field, addr_field, phone_field, bar_field, bar2_field,
		//callnum_field, year_field, h_school_field, h_school_data_field
		String sql1 = "update student set name = ?, addr = ?, jumin = ?, phone = ?, callnum = ?, uinyear = ?, highschool = ?, end_year = ?,dept_code = ?, prof_code = ?, gender = ? where code = ?";

		try {
			pstmt = con.conn.prepareStatement(sql1);

			pstmt.setString(12, std_field.getText());
			pstmt.setString(1, std_name_field.getText());
			pstmt.setString(2, addr_field.getText());
			
			pstmt.setString(3, jumin_field.getText()+jumin_bar_field.getText());
			pstmt.setString(4, phone_field.getText()+bar_field.getText()+bar2_field.getText());
			
			pstmt.setString(5, callnum_field.getText());
			pstmt.setString(6, year_field.getText());
			pstmt.setString(7, h_school_field.getText());
			pstmt.setString(8, h_school_data_field.getText());
			pstmt.setString(9, deptcode);
			pstmt.setString(10, profcodeV);
			pstmt.setString(11, genderS);

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
		if (e.getSource() == lookall_bt) {
			getListAll();
		}
		if (e.getSource() == look_bt) {
			searchM();
		}
		if (e.getSource() == add_bt) {
			int result = regist(std_field.getText());

			if (result != 0) {
				JOptionPane.showMessageDialog(getParent(), "등록성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "등록실패!");
			}
		}
		if (e.getSource() == del_bt) {
			del();
			getListAll();
		}
		if (e.getSource() == modify_bt) {
			update();
		}
		if (e.getSource() == end_bt) {
			dispose();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Student_M();
	}

}
