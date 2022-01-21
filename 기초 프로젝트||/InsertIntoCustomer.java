package chap6;
import java.sql.*;

public class InsertIntoCustomer {
	public static void main(String args[]) {
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://127.0.0.1:3306/banksystem";
		String user = "root";
		String passwd = "test123";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
			return;
		}
		
		int count = 0;
		String insertString = "INSERT INTO customer VALUES ";
		// 삽입할 행의 정보를 배열에 저장한 후 이를 이용해 삽입
		String recordString[] = 
			{insertString + "('C-1001', '가나다', '010-1111-2222', '서울')",
			insertString + "('C-1002', '라마바', '010-1111-3333', '부산')",
			insertString + "('C-1003', '사아자', '010-1111-4444', '대구')",
			insertString + "('C-1004', '가나다', '010-1111-5555', '광주')",
			insertString + "('C-1005', '나다라', '010-1111-6666', '대전')",
			insertString + "('C-1006', '다라마', '010-1111-7777', '강원')"};

		try {
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			while (count<recordString.length) {
				stmt.executeUpdate(recordString[count]);
				count++;
			}
			System.out.println(count + "개의 레코드가 customer 테이블에 삽입되었습니다! ");
	
		} catch(SQLException ex) {
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