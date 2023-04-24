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

public class Department_M extends JFrame implements ActionListener {
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, depart_code, depart_name, major;
	JTextField code_field, name_field, major_field, search_field;
	JComboBox combo;
	String menu[] = { "학과코드", "학과명", "전공명", "검색할 카테고리 선택하세요" };
	String dbm[] = {"code","dept","major"};
	JButton look_bt, lookall_bt, add_bt, modify_bt, del_bt, end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Connect con;
	Font fon1 = new Font("Dialog", Font.PLAIN, 20);
	ResultSet rs = null;
	PreparedStatement pstmt;
	int srow;

	public Department_M() {
		setTitle("학사 관리");
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
		title = new JLabel("학사관리");
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
		depart_code = new JLabel("학과코드");
		depart_name = new JLabel("학과명");
		major = new JLabel("전공명");
		code_field = new JTextField();
		name_field = new JTextField();
		major_field = new JTextField();

		input_panel.setBounds(0, 50, 600, 170);

		depart_code.setBounds(10, 20, 60, 25);
		code_field.setBounds(75, 20, 100, 25);

		depart_name.setBounds(210, 20, 60, 25);
		name_field.setBounds(275, 20, 100, 25);

		major.setBounds(410, 20, 60, 25);
		major_field.setBounds(475, 20, 100, 25);

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

		search_panel.setBounds(0, 220, 600, 50);
		combo.setBounds(10, 10, 170, 30);
		search_field.setBounds(185, 10, 150, 30);
		look_bt.setBounds(375, 10, 100, 30);
		lookall_bt.setBounds(480, 10, 100, 30);
		
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Object s = e.getItem();
			}
			
		});
		look_bt.addActionListener(this);
		lookall_bt.addActionListener(this);

		search_panel.add(combo);
		search_panel.add(search_field);
		search_panel.add(look_bt);
		search_panel.add(lookall_bt);

		add(search_panel);
	}

	void table() {
		String header[] = { "학과코드", "학과명", "전공명" };
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
				code_field.setText(code);
				String name = ((String) table.getValueAt(srow, 1));
				name_field.setText(name);
				String major = ((String) table.getValueAt(srow, 2));
				major_field.setText(major);

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

	void getListAll() {
		try {
			this.dtm.setRowCount(0);

			String quary = "SELECT * FROM depart";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				String code = rs.getString(1);
				String dept = rs.getString(2);
				String major = rs.getString(3);

				Object[] rowData = { code, dept, major };
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int regist(String code) {
		int result = 0;
		String sql = "SELECT * FROM depart where code = '" + code + "'";
		try {
			pstmt = con.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JOptionPane.showMessageDialog(this, "이미 등록된 학과입니다!", "학과코드 확인", JOptionPane.YES_OPTION);
				return result;
			}
			sql = "INSERT INTO depart(code, dept, major) VALUES(?,?,?)";
			pstmt = con.conn.prepareStatement(sql);

			pstmt.setString(1, code_field.getText());
			pstmt.setString(2, name_field.getText());
			pstmt.setString(3, major_field.getText());

			result = pstmt.executeUpdate();

			getListAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	void del() {
		String sql = "DELETE FROM depart where code = ?";
		int result = 0;
		
		System.out.println("삭제혀");
		try {
			pstmt = con.conn.prepareStatement(sql);
			pstmt.setString(1, code_field.getText());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void update() {
		String sql1 = "update depart set dept= ?, major = ? where code = ?";
		
		String code = code_field.getText();
		String name = name_field.getText();
		String major = major_field.getText();
		
		try {
			pstmt = con.conn.prepareStatement(sql1);
			
			pstmt.setString(3, code_field.getText());
			pstmt.setString(1, name_field.getText());
			pstmt.setString(2, major_field.getText());
			
			int a = pstmt.executeUpdate();
			
			if(a != 0) {
				JOptionPane.showMessageDialog(getParent(), "수정성공");
				getListAll();
			}else {
				JOptionPane.showConfirmDialog(getParent(), "수정실패!");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	void searchM() {
		String info = dbm[combo.getSelectedIndex()];
		System.out.println(info);
		String sql = "SELECT * FROM depart WHERE " + info + " = '" + search_field.getText() + "'";
		
		try {
			pstmt = con.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			dtm.setRowCount(0);
			while(rs.next()) {
				String code = rs.getString(1);
				String dept = rs.getString(2);
				String major = rs.getString(3);
				
				Object[] rowData = {code, dept, major};
				
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
		if (e.getSource() == add_bt) {
			String code = code_field.getText();
			String name = name_field.getText();
			String major = major_field.getText();

			String[] nag = { code, name, major};

			int result = regist(code);
			
			if (result != 0) {
				JOptionPane.showMessageDialog(getParent(), "등록성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "등록실패!");
			}
		}	
		if (e.getSource() == modify_bt) {
			update();
			getListAll();
		}
		if (e.getSource() == del_bt) {
			del();
			getListAll();
		}
		if (e.getSource() == end_bt) {
			dispose();
		}
		if (e.getSource() == look_bt) {
			searchM();
		}
		if (e.getSource() == lookall_bt) {
			getListAll();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Department_M();
	}

}
