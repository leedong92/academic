package univ;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login_M extends JFrame implements ActionListener {
	JPanel titlepanel, textpanel, btnpanel;
	JLabel title, id, pass, username;
	JTextField idtext, passtext, nametext;
	JButton jbt;
	Font fon1;
	ResultSet rs = null;
	PreparedStatement stmt;
	Connect cq1;

	public Login_M() {
		this.setTitle("로그인");
		this.setLayout(null);
		this.setBounds(0, 0, 600, 650);

		cq();
		Title();
		Text();
		Button();

		setSize(600, 650);
		setVisible(true);

	}

	void cq() { // db연결
		cq1 = new Connect();
	}

	void Title() {
		titlepanel = new JPanel();
		fon1 = new Font("serif", Font.BOLD, 20);
		titlepanel.setBackground(Color.black);
		titlepanel.setBounds(0, 0, 600, 50);
		title = new JLabel("로그인");
		title.setFont(fon1);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setForeground(Color.white);
		title.setBounds(5, 5, 600, 30);
		titlepanel.add(title);
		this.add(titlepanel);
	}

	void Text() {
		textpanel = new JPanel();
		id = new JLabel("아이디");
		pass = new JLabel("패스워드");
		username = new JLabel("사용자이름");
		idtext = new JTextField();
		passtext = new JTextField();
		nametext = new JTextField();
		textpanel.setLayout(null);
		textpanel.setBounds(0, 50, 600, 170);
		
		id.setBounds(35, 40, 75, 25);
		idtext.setBounds(95, 40, 95, 25);
		pass.setBounds(215, 40, 75, 25);
		passtext.setBounds(280, 40, 95, 25);
		username.setBounds(385, 40, 75, 25);
		nametext.setBounds(465, 40, 95, 25);
		
		textpanel.add(id);
		textpanel.add(pass);
		textpanel.add(username);
		textpanel.add(idtext);
		textpanel.add(passtext);
		textpanel.add(nametext);

		this.add(textpanel);
	}

	void Button() {
		btnpanel = new JPanel();
		btnpanel.setLayout(null);
		btnpanel.setBounds(0, 250, 600, 70);
		btnpanel.setBackground(Color.blue);

		jbt = new JButton("확인");
		jbt.setForeground(Color.red);
		jbt.setBackground(Color.green);
		jbt.setBounds(400, 15, 100, 40);

		jbt.addActionListener(this);

		btnpanel.add(jbt);
		this.add(btnpanel);

	}
	//

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String id = null;
		String pass = null;
		String name = null;

		String sql = "SELECT * FROM admin where id = ? and pass = ?";
		if (e.getSource() == jbt) {
			try {
				stmt = cq1.conn.prepareStatement(sql);
				stmt.setString(1, idtext.getText());
				stmt.setString(2, passtext.getText());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					id = rs.getString(1);
					pass = rs.getString(2);
					name = rs.getString(3);
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (idtext.getText().equals(id) && passtext.getText().equals(pass)) {
				nametext.setText(name);
				JOptionPane.showMessageDialog(null, name+"환영합니다");
				
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "아이디/비밀번호가 다릅니다.", "조회",0);
				idtext.setText(null);
				passtext.setText(null);
				nametext.setText(null);
				
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login_M();
	}

}
