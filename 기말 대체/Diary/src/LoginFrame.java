import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel mainPanel = new JPanel(new FlowLayout());
	JPanel btnPanel = new JPanel(new FlowLayout());
	
	JLabel pass = new JLabel("Password : ");
	JPasswordField typePass = new JPasswordField();
	
	JButton create = new JButton("비밀번호 생성");
	JButton ok = new JButton("확인");
	JButton find = new JButton("비밀번호 찾기");
	
	LoginFrame(){
		setTitle("비밀 다이어리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(panel);
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(btnPanel, BorderLayout.SOUTH);
		
		mainPanel.add(pass, BorderLayout.NORTH);
		mainPanel.add(typePass, BorderLayout.CENTER);
		
		btnPanel.add(create);
		btnPanel.add(ok);
		btnPanel.add(find);
		
		pass.setPreferredSize(new Dimension(100,40));
		typePass.setPreferredSize(new Dimension(140,40));
		ok.setPreferredSize(new Dimension(60,40));
		create.setPreferredSize(new Dimension(90,40));
		find.setPreferredSize(new Dimension(90,40));
		
		MyActionListener ml = new MyActionListener();
		create.addActionListener(ml);
		ok.addActionListener(ml);
		find.addActionListener(ml);
		
		
		setSize(300,140);
		setVisible(true);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
	}
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("비밀번호 생성")) {
				CreateFrame cf = new CreateFrame();
				cf.setVisible(true);
			}
			if(b.getText().equals("확인")) {
				String pw = "";
				for (int i = 0; i < typePass.getPassword().length; i++) {
					pw = pw + typePass.getPassword()[i]; //패스워드를 한 글자씩 가져와서 넣어주는 과정 
				}
				
				passwordChecker(pw);
			}
			if(b.getText().equals("비밀번호 찾기")) {
				FindFrame ff = new FindFrame();
			}
		}
	}
	
	void DBChecker() {
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
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			
			ResultSet result = stmt.executeQuery("SELECT * FROM password"); 
	
			if (result.next()) { 
				if(result.getString("user_hint")!=null) {
					JFrame LoginFrame = new JFrame();
					JOptionPane.showMessageDialog(LoginFrame, "이미 생성된 비밀번호가 있습니다.");
				}else {
					CreateFrame cf = new CreateFrame();
					cf.setVisible(true);
				}
				
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		} 
	}
	
	void passwordChecker(String _p) {
		String typePass = _p;
		String userPass;
		
		SHA256 sha256 = new SHA256();
		
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
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			
			ResultSet result = stmt.executeQuery("SELECT * FROM password"); 
	
			if (result.next()) { 
				userPass = result.getString("user_password");
				
				String encTypePass = sha256.encrypt(typePass);
				
				if(userPass.equals(encTypePass)) {
					System.out.println("비밀 번호 일치 : 로그인 성공");
					dispose();
					MainFrame mf = new MainFrame();
					mf.setVisible(true);
				}
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		} 
		
	}
	
	
	
}
