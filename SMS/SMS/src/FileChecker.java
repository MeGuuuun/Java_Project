import java.util.ArrayList;
import java.util.StringTokenizer;
import java.net.*;
import java.io.*;
import java.sql.*;

//공유된 user 파일을 읽어 MySQL에 데이터 추가 
public class FileChecker {
	File dataFile = new File("/Users/jiminpark/Desktop/SMS/SMS/users.txt"); //파일 생성자엔 경로 적기
	String readData = null; //파일에서 한 줄 한 줄 읽어올 객체 
	StringTokenizer st; //문자열 분리 
	
	
	FileChecker(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			//외부에서 들어오는 값을 버퍼에서 읽는 것 = BufferedReader
			//BufferedReader를 사용하기 위해서는 FileReader 객체가 반드시 필요 (익명 객체)
			//FileReader 객체를 만들기 위해서는 파일 데이터 필요 (dataFile)
			while((readData=br.readLine())!=null) { //readDate에 readLine으로 한 줄 읽어와 저장 
				st = new StringTokenizer(readData, "!");
				String userId = st.nextToken();
				String userPassword = st.nextToken();
				insertUser(userId, userPassword);
			}
			br.close();
			System.out.println("Server> 유저 파일 읽기 완료 / DB에 저장 완료 ");
			
		}catch(Exception e) {
			System.out.println("Server > 유저 파일 읽기 에러 / DB에 저장 실패 ");
			e.printStackTrace();
		}
	}
	
	void insertUser(String _s, String _ss) {
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://localhost:3306/university?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1234";
		
		String id = _s;
		String pass = _ss;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
			return;
		}
		
		String insertString = "INSERT INTO user (id, password) VALUES ";

		try {
			String passString = insertString + "('"+ id +"', '"+ pass + "' )";
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			stmt.executeUpdate(passString);
			System.out.println("insert finished");
	
		} catch(Exception ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		finally {
        		try {
        			if (stmt != null) stmt.close();
                	if (con != null) con.close();
               	} catch (Exception e) {}
      	 	}
	}
	
}