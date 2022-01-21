package chap6;
import java.sql.*;

public class SelectFromCustomer {
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
			System.err.println("����̹� �ε� ����: " + e.getMessage());
			return;
		} 

		try {
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
                        // customer ���̺� �ִ� ��� ���ڵ� �˻� 	
			ResultSet result = stmt.executeQuery("SELECT * FROM customer"); 
	
			// result ��ü�� ����� ���� ����κ��� ���� ���� ����
			int count=0;
			while (result.next()) { 
				if (count==0) displayHeadInfo();
				String resultStr = result.getString("customer_id") + "\t\t";
				resultStr += result.getString("customer_name") + "\t";
				resultStr +=  result.getString("customer_tel") + "\t";
				resultStr += result.getString("customer_addr");
				System.out.println(resultStr);
				count++;
			}
			displayEndInfo(count);
			stmt.close();
			con.close();	

		} catch(SQLException ex) {
			System.err.println("Select ����: " + ex.getMessage());
		}
		
	}
	
	public static void displayHeadInfo() {
		System.out.println("\n������ ���� ���");
		drawLine();
		System.out.println("����ȣ\t����\t��ȭ��ȣ\t�ּ�");
		drawLine();
	}
	
	public static void displayEndInfo(int count) {
		drawLine();
		System.out.println(count + "���� ���ڵ尡 �˻��Ǿ����ϴ�! ");
	}

	public static void drawLine () {
		System.out.println("=============================================");
	}
	
}