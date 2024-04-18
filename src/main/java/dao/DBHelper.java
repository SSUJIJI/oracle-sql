package dao;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class DBHelper {
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = null;
		
		//TNS_ADMIN 매개값은 전자지갑의 위치
		String dbUrl = "jdbc:oracle:thin:@shop_high?TNS_ADMIN=c://oracle_wallet//Wallet_shop";
		String dbUser = "admin";
		
		// 보안이슈로 로컬에서 설정파일 로드 
		FileReader fr = new FileReader("D:\\dev\\auth\\oracle.properties");
		Properties prop = new Properties();
		prop.load(fr);
		String dbPw = prop.getProperty("dbPw");
		conn = DriverManager.getConnection(dbUrl,dbUser,dbPw);
		return conn;
	}
	
	//getConnection()메서드 디버깅용 테스트 코드
	/*public static void main(String[] args) throws Exception{
		Connection conn = DBHelper.getConnection(sql);
		System.out.println(conn);
	}*/
}
