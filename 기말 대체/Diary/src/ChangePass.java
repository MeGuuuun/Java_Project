import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class ChangePass extends JFrame {
	JPanel panel = new JPanel();
	JLabel pass = new JLabel("변경할 비밀번호 : ");
	JTextField typePass = new JTextField();
	JLabel hint = new JLabel("변경할 힌트 입력 : ");
	JTextField typeHint = new JTextField();
	JButton ok = new JButton("변경");
	JButton cancel = new JButton("닫기");
	
	ChangePass() {
		setTitle("비밀번호 변경");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		
		panel.add(pass);
		panel.add(typePass);
		panel.add(hint);
		panel.add(typeHint);
		panel.add(ok);
		panel.add(cancel);
		
		pass.setPreferredSize(new Dimension(100,40));
		typePass.setPreferredSize(new Dimension(150,40));
		hint.setPreferredSize(new Dimension(100,40));
		typeHint.setPreferredSize(new Dimension(150,40));
		
		ok.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		cancel.addActionListener(ml);
		
		setSize(300,230);
		setResizable(false);
		setVisible(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("변경")) {
				String pass = typePass.getText();
				String hint = typeHint.getText();
				
				passChanger(pass, hint);
				JOptionPane.showMessageDialog(null, "변경되었습니다.");
				dispose();
				System.exit(0);
			}
			if(b.getText().equals("닫기")) {
				dispose();
			}
		}
	}
	
	void passChanger(String _s, String _ss) {
		
		SHA256 sha256 = new SHA256();
		
		String newPass = _s;
		String newHint = _ss;
		
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://localhost:3306/Diary?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1234";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
			return;
		}
		
		
		try {
			String sql = "INSERT INTO Password (user_password, user_hint) VALUES";
			String passString = sql + "('"+ sha256.encrypt(newPass) +"', '"+ newHint + "' )";
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			stmt.executeUpdate(passString);
			System.out.println("update finished");
	
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
