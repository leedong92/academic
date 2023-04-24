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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Admin_M extends JFrame implements ActionListener {
	JPanel title_panel, input_panel, search_panel, button_panel;
	JLabel title, id, pass, pass2, admin, admin2, adddate;
	JTextField id_f, pass_f, pass2_f, admin_f, admin2_f, adddate_f;

	JButton id_bt, pass_bt, add_bt, modify_bt, del_bt, end_bt;
	JScrollPane table_panel;
	DefaultTableModel dtm;
	JTable table;
	Connect con;
	Font fon1 = new Font("Dialog", Font.PLAIN, 20);
	ResultSet rs = null;
	PreparedStatement pstmt;
	int idx;
	int srow;

	public Admin_M() {
		setTitle("사용자 관리");
		setLayout(null);
		setBounds(50, 50, 600, 650);
		con = new Connect();
		title();
		input();
		table();
		button();
		getListAll();

		setResizable(false);
		setVisible(true);
	}

	void title() {
		title_panel = new JPanel(null);
		title_panel.setBackground(Color.black);
		title = new JLabel("사용자관리");
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

		id = new JLabel("아이디");
		pass = new JLabel("패스워드");
		pass2 = new JLabel("패스워드확인");
		admin = new JLabel("사용자");
		admin2 = new JLabel("사용권한");
		adddate = new JLabel("등록날짜");

		id_f = new JTextField();
		pass_f = new JTextField();
		pass2_f = new JTextField();
		admin_f = new JTextField();
		admin2_f = new JTextField();
		adddate_f = new JTextField();

		id_bt = new JButton("ID 중복확인");
		pass_bt = new JButton("PW 중복확인");

		input_panel.setBounds(0, 50, 600, 170);

		id.setBounds(15, 20, 75, 25);
		id_f.setBounds(95, 20, 100, 25);
		id_bt.setBounds(215, 20, 120, 25);

		pass.setBounds(15, 50, 75, 25);
		pass_f.setBounds(95, 50, 100, 25);
		pass2.setBounds(215, 50, 75, 25);
		pass2_f.setBounds(295, 50, 100, 25);
		pass_bt.setBounds(415, 50, 120, 25);

		admin.setBounds(15, 80, 75, 25);
		admin_f.setBounds(95, 80, 100, 25);
		admin2.setBounds(215, 80, 75, 25);
		admin2_f.setBounds(295, 80, 100, 25);

		adddate.setBounds(15, 110, 75, 25);
		adddate_f.setBounds(95, 110, 100, 25);

		input_panel.add(id);
		input_panel.add(id_f);
		input_panel.add(id_bt);
		input_panel.add(pass);
		input_panel.add(pass_f);
		input_panel.add(pass2);
		input_panel.add(pass2_f);
		input_panel.add(pass_bt);
		input_panel.add(admin);
		input_panel.add(admin_f);
		input_panel.add(admin2);
		input_panel.add(admin2_f);
		input_panel.add(adddate);
		input_panel.add(adddate_f);

		add(input_panel);
	}

	void table() {
		String header[] = { "아이디", "패스워드", "사용자", "사용권한", "등록날짜" };
		Object rowdata[][] = {};
		dtm = new DefaultTableModel(null, header) {
			public boolean isCellEditable(int row, int colum) {
				return false;
			}
		};
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
				
				String id = ((String)table.getValueAt(srow, 0));
				id_f.setText(id);
				String pass = ((String)table.getValueAt(srow, 1));
				pass_f.setText(pass);
				String user = ((String)table.getValueAt(srow, 2));
				admin_f.setText(user);
				String auth = ((String)table.getValueAt(srow, 0));
				admin2_f.setText(auth);
				String date = ((String)table.getValueAt(srow, 0));
				adddate.setText(date);
				
				
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

		button_panel.add(add_bt);
		button_panel.add(modify_bt);
		button_panel.add(del_bt);
		button_panel.add(end_bt);

		add(button_panel);
	}
	

	public int regist(String id, String pass, String user, String auth, String date) {
		int result = 0;
		String sql = "INSERT INTO admin(id, pass, user, auth, date) VALUES(?,?,?,?,?)";
		try {
			pstmt = con.conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, user);
			pstmt.setString(4, auth);
			pstmt.setString(5, date);
			result = pstmt.executeUpdate();

			getListAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	void getListAll() {
		try {
			this.dtm.setRowCount(0);

			String quary = "SELECT * FROM admin";
			pstmt = con.conn.prepareStatement(quary);
			rs = pstmt.executeQuery(quary);
			while (rs.next()) {
				String id = rs.getString(1);
				String pass = rs.getString(2);
				String user = rs.getString(3);
				String auth = rs.getString(4);
				String date = rs.getString(5);

				Object[] rowData = { id, pass, user, auth, date };
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void del() {
		String sql = "DELETE FROM admin where user = ?";
		int a = 0;
		System.out.println("삭제혀");
		try {
			pstmt = con.conn.prepareStatement(sql);
			pstmt.setString(1, admin.getText());
			
			a = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == add_bt) {
			String id = id_f.getText();
			String pass = pass_f.getText();
			String user = admin_f.getText();
			String auth = admin2_f.getText();
			String date = adddate_f.getText();

			String[] nag = { id, pass, user, auth, date };

			int result = regist(id, pass, user, auth, date);
			System.out.println("1");
			if (result != 0) {
				JOptionPane.showMessageDialog(getParent(), "등록성공");
				getListAll();
			} else {
				JOptionPane.showConfirmDialog(getParent(), "등록실패!");
			}
		}
		if (e.getSource()==modify_bt) {
			
		}
		if (e.getSource()==del_bt) {
			
		}
		if (e.getSource()==end_bt) {
			
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Admin_M();
	}

}
