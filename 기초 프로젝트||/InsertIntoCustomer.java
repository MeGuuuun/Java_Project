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
		// ������ ���� ������ �迭�� ������ �� �̸� �̿��� ����
		String recordString[] = 
			{insertString + "('C-1001', '������', '010-1111-2222', '����')",
			insertString + "('C-1002', '�󸶹�', '010-1111-3333', '�λ�')",
			insertString + "('C-1003', '�����', '010-1111-4444', '�뱸')",
			insertString + "('C-1004', '������', '010-1111-5555', '����')",
			insertString + "('C-1005', '���ٶ�', '010-1111-6666', '����')",
			insertString + "('C-1006', '�ٶ�', '010-1111-7777', '����')"};

		try {
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			while (count<recordString.length) {
				stmt.executeUpdate(recordString[count]);
				count++;
			}
			System.out.println(count + "���� ���ڵ尡 customer ���̺� ���ԵǾ����ϴ�! ");
	
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