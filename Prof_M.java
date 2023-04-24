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

public class Prof_M extends JFrame implements ActionListener {

	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, prof_code, prof_name, jumin_num, jumin_bar, addr, phone, phone_bar, phone_bar2, callnum, year, hakwi,
			depart, lab, gender;
	JTextField prof_field, name_field, jumin_field, jumin_bar_field, addr_field, phone_field, bar_field, bar2_field,
			callnum_field, year_field, hakwi_field, lab_field, search_field;
	JComboBox combo, departbox;
	String dpbox[] = { "개설학과", "컴퓨터공학", "방사선", "물리치료", "사무자동화", "간호" };
	String menu[] = { "교수코드", "교수명", "학과명", "성별", "검색할 카테고리 선택하세요" };
	String menu2[] = {"code","name","dept","gender"};
	String dbm[] = { "code", "name", "dept_code", "gender" };
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
	String category= null;
	String deptcode= null;
	String deptname= null;

	public Prof_M() {
		setTitle("교수관리");
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
		title = new JLabel("교 수 관 리");
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
		prof_name = new JLabel("이름");
		jumin_num = new JLabel("주민번호");
		jumin_bar = new JLabel("-");
		addr = new JLabel("주소");
		phone = new JLabel("휴대폰");
		phone_bar = new JLabel("-");
		phone_bar2 = new JLabel("-");
		callnum = new JLabel("전화번호");
		year = new JLabel("임용년도");
		hakwi = new JLabel("학위");
		depart = new JLabel("학과");
		lab = new JLabel("연구실");
		gender = new JLabel("성별");

		prof_field = new JTextField();
		name_field = new JTextField();
		jumin_field = new JTextField();
		jumin_bar_field = new JTextField();
		addr_field = new JTextField();
		phone_field = new JTextField();
		bar_field = new JTextField();
		bar2_field = new JTextField();
		callnum_field = new JTextField();
		year_field = new JTextField();
		hakwi_field = new JTextField();
		departbox = new JComboBox();
		boxm1();

		lab_field = new JTextField();
		ButtonGroup bgroup = new ButtonGroup();
		genderM_radio = new JRadioButton("남자");
		genderF_radio = new JRadioButton("여자");
		bgroup.add(genderM_radio);
		bgroup.add(genderF_radio);
		
		genderM_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				//Object c = e.getItem();
				genderS = genderM_radio.getActionCommand();
			}
			
		});
		genderF_radio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				//Object c = e.getItem();
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

		input_panel.setBounds(0, 50, 600, 170);

		prof_code.setBounds(15, 10, 50, 25);
		prof_field.setBounds(75, 10, 80, 25);
		prof_name.setBounds(180, 10, 50, 25);
		name_field.setBounds(215, 10, 80, 25);
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
		callnum_field.setBounds(415, 70, 145, 25);

		year.setBounds(15, 100, 50, 25);
		year_field.setBounds(75, 100, 80, 25);
		hakwi.setBounds(180, 100, 50, 25);
		hakwi_field.setBounds(230, 100, 90, 25);
		depart.setBounds(350, 100, 50, 25);
		departbox.setBounds(415, 100, 145, 25);

		lab.setBounds(15, 130, 50, 25);
		lab_field.setBounds(75, 130, 90, 25);
		gender.setBounds(180, 130, 50, 25);
		genderM_radio.setBounds(235, 130, 55, 25);
		genderF_radio.setBounds(290, 130, 55, 25);

		input_panel.add(prof_code);
		input_panel.add(prof_field);
		input_panel.add(prof_name);
		input_panel.add(name_field);
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
		input_panel.add(hakwi);
		input_panel.add(hakwi_field);
		input_panel.add(depart);
		input_panel.add(departbox);
		input_panel.add(lab);
		input_panel.add(lab_field);
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

		lookall_bt.addActionListener(this);
		look_bt.addActionListener(this);

		search_panel.add(combo);
		search_panel.add(search_field);
		search_panel.add(look_bt);
		search_panel.add(lookall_bt);

		add(search_panel);
	}

	void table() {
		String header[] = { "교수코드", "이름", "주소", "주민등록번호", "휴대폰", "전화", "임용년도", "학위", "학과", "연구실", "성별" };
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
				prof_field.setText(code);
				String name = ((String) table.getValueAt(srow, 1));
				name_field.setText(name);
				String addr = ((String) table.getValueAt(srow, 2));
				addr_field.setText(addr);
				
				String jumin = ((String) table.getValueAt(srow, 3));
				jumin_field.setText(jumin.substring(0,6));
				jumin_bar_field.setText(jumin.substring(7,14));
				
				String phone = ((String) table.getValueAt(srow, 4));
				phone_field.setText(phone.substring(0,3));
				bar_field.setText(phone.substring(4,8));
				bar2_field.setText(phone.substring(9,13));
				
				String call = ((String) table.getValueAt(srow, 5));
				String a = call.substring(0,3);
				String b = call.substring(4,7);
				String c = call.substring(8,12);
				callnum_field.setText(a+b+c);
				
				
				String year = ((String) table.getValueAt(srow, 6));
				year_field.setText(year);
				String hakwi = ((String) table.getValueAt(srow, 7));
				hakwi_field.setText(hakwi);
				
				String depart = ((String) table.getValueAt(srow, 8));
				departbox.setSelectedItem(depart);
				
				String lab = ((String) table.getValueAt(srow, 9));
				lab_field.setText(lab);
				String gender = ((String) table.getValueAt(srow, 10));
				if(gender.equals("남자")) {
					genderM_radio.setSelected(true);
				}else {
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

		button_panel.setBounds(0, 510, 600, 140);
		add_bt.setBounds(15, 30, 135, 50);
		modify_bt.setBounds(155, 30, 135, 50);
		del_bt.setBounds(295, 30, 135, 50);
		end_bt.setBounds(435, 30, 135, 50);
		
		add_bt.addActionListener(this);
		modify_bt.addActionListener(this);
		del_bt.addActionListener(this);
		end_bt.addActionListener(this);

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
				;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void getListAll() { //완
		try {
			this.dtm.setRowCount(0);

			String quary = "SELECT * FROM prof";
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
				String ent_year = rs.getString(7);
				String hakwi = rs.getString(8);

				String dept_code = rs.getString(9);
				quary = "Select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(quary);
				pstmt.setString(1, dept_code);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					dept_code = rs1.getString(2);
				}

				String roomno = rs.getString(10);
				String gender = rs.getString(11);

				Object[] rowData = { code, name, addr, jumin, phone, call, ent_year, hakwi, dept_code, roomno, gender };
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int regist(String code) {
		int result = 0;
		String sql = "SELECT * FROM prof where code = '" + code + "'";
		System.out.println(code);
		try {
			pstmt = con.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			dtm.setRowCount(0);
			while (rs.next()) {
				JOptionPane.showMessageDialog(this, "이미 등록된 교수입니다!", "교수코드 확인", JOptionPane.YES_OPTION);
				return result;
			}
			
			sql = "INSERT INTO prof (code,name,addr,jumin,phone,callnum,ent_year,hakwi,dept_code,roomno,gender) values(?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.conn.prepareStatement(sql);
			
			pstmt.setString(1, prof_field.getText());
			pstmt.setString(2, name_field.getText());
			pstmt.setString(3, addr_field.getText());
			
//			String juminf = jumin_field.getText();
//			String juminb = jumin_bar_field.getText();
//			String jumin = juminf + juminb;
			pstmt.setString(4, jumin_field.getText()+jumin_bar_field.getText());
			String phonef = phone_field.getText();
			String phonem = bar_field.getText();
			String phoneb = bar2_field.getText();
			String phone = phonef + phonem + phoneb;
			pstmt.setString(5, phone);
			//String call = callnum_field.getText();
			pstmt.setString(6, callnum_field.getText());
			
			pstmt.setString(7, year_field.getText());
			pstmt.setString(8, hakwi.getText());
			//pstmt.setString(9, (String)departbox.getSelectedItem());
			pstmt.setString(9, deptcode);
			//pstmt.setString(9, category);
			pstmt.setString(10, lab_field.getText());
			pstmt.setString(11, genderS);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	void del() {//완
		
		int result = 0;
		String sql = "DELETE FROM prof where code = ?";
		System.out.println("삭제혀");
		try {
			pstmt = con.conn.prepareStatement(sql);
			pstmt.setString(1, prof_field.getText());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void update() {
		String sql1 = "update prof set name = ?, addr = ?, jumin = ?, phone = ?, callnum = ?, ent_year = ?, hakwi = ?, dept_code = ?, roomno = ?, gender = ? where code = ?";

		try {
			pstmt = con.conn.prepareStatement(sql1);

			pstmt.setString(11, prof_field.getText());
			pstmt.setString(1, name_field.getText());
			pstmt.setString(2, addr_field.getText());
			
			pstmt.setString(3, jumin_field.getText()+jumin_bar_field.getText());
			pstmt.setString(4, phone_field.getText()+bar_field.getText()+bar2_field.getText());
			
			pstmt.setString(5, callnum_field.getText());
			pstmt.setString(6, year_field.getText());
			pstmt.setString(7, hakwi.getText());
			pstmt.setString(8, deptcode);
			pstmt.setString(9, lab_field.getText());
			pstmt.setString(10, genderS);

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

	void searchM() { //완
		String s = dbm[combo.getSelectedIndex()];
		
		String deptcode = null, deptname = null;
		

		try {			
			if(search_field.getText().length()==0) {
				JOptionPane.showMessageDialog(this, "조회할 내용 입력하세요?","조회",JOptionPane.YES_OPTION);
				search_field.requestFocus();
				return;
			}
			if(s.equals("dept_code")) {
				String query = "select * from depart where dept =? or code=?";
				pstmt = con.conn.prepareStatement(query);
				pstmt.setString(1, search_field.getText());
				pstmt.setString(2, search_field.getText());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					deptcode = rs.getString(1);
				}
				
				query = "select * from prof where " + s + " = '" + deptcode + "'";
				pstmt = con.conn.prepareStatement(query);
			}else {
				String query = "SELECT * FROM prof WHERE " + s + " = '" + search_field.getText() + "'";
				pstmt = con.conn.prepareStatement(query);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String code = rs.getString(1);
				String dept = rs.getString(2);
				String addr = rs.getString(3);

				String jumin = rs.getString(4);
				//jumin = jumin.substring(0, 6) + "-" + jumin.substring(6, 13);
				String phone = rs.getString(5);
				//phone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7, 11);
				String call = rs.getString(6);
				//call = call.substring(0, 3) + ")" + call.substring(3, 6) + "-" + call.substring(6, 10);
				String ent_year = rs.getString(7);
				String hakwi = rs.getString(8);

				String dept_code = rs.getString(9);
				String sql = "Select * from depart where code = ?";
				pstmt = con.conn.prepareStatement(sql);
				pstmt.setString(1, dept_code);
				rs1 = pstmt.executeQuery();

				while (rs1.next()) {
					dept_code = rs1.getString(2);
				}

				String roomno = rs.getString(10);
				String gender = rs.getString(11);

				Object[] rowData = { code, dept, addr, jumin, phone, call, ent_year, hakwi, dept_code, roomno, gender };
				dtm.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			int result = regist(prof_field.getText());
			
			if (result != 0) {
				JOptionPane.showMessageDialog(getParent(), "등록성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "등록실패!");
			}
		}
		if (e.getSource() == modify_bt) {
			update();
		}
		if (e.getSource() == del_bt) {
			del();
			getListAll();
		}
		if (e.getSource() == end_bt) {
			dispose();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Prof_M();
	}

}
