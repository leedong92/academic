package univ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import player1103.ConQuery;


public class Connect {
	Connection conn;
	
	static String driver = "com.mysql.cj.jdbc.Driver";
	static String user = "root";
	static String pass = "1111";
	static String dbURL = "jdbc:mysql://localhost:3306/academic?serverTimezone=Asia/Seoul";
	
	Connect(){
		try {
			Class.forName(driver);
			System.out.println("MySQL 드라이버 검색 성공...");
		}catch(ClassNotFoundException e) {
			System.out.println("MySQL 드라이버 검색 오류");
		}
		
		try {
			conn = DriverManager.getConnection(dbURL,user,pass);
			System.out.println("MYSQL 연결 성공...");
		}catch(SQLException e) {
			System.out.println("MySQL 연결 객체 생성 실패"+e);
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Connect();
	}
}