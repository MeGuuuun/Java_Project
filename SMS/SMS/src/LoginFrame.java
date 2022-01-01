import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel mainPanel = new JPanel(new FlowLayout());
	JPanel btnPanel = new JPanel(new FlowLayout());
	
	JLabel id = new JLabel("ID : ");
	JTextField typeId = new JTextField();
	JLabel pass = new JLabel("Password : ");
	JPasswordField typePass = new JPasswordField();
	
	JButton create = new JButton("회원가입");
	JButton ok = new JButton("확인");
	
	LoginFrame() {
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setContentPane(panel);
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(btnPanel, BorderLayout.SOUTH);
		
		mainPanel.add(id);
		mainPanel.add(typeId);
		mainPanel.add(pass);
		mainPanel.add(typePass);
		
		btnPanel.add(create);
		btnPanel.add(ok);
		
		id.setPreferredSize(new Dimension(100,40));
		typeId.setPreferredSize(new Dimension(140,40));
		pass.setPreferredSize(new Dimension(100,40));
		typePass.setPreferredSize(new Dimension(140,40));
		ok.setPreferredSize(new Dimension(60,40));
		create.setPreferredSize(new Dimension(90,40));
		
		MyActionListener ml = new MyActionListener();
		create.addActionListener(ml);
		ok.addActionListener(ml);
		
		
		setSize(300,180);
		setVisible(true);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
	}
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("회원가입")) {
				CreateFrame cf = new CreateFrame();
				cf.setVisible(true);
			}
			if(b.getText().equals("확인")) {
				String id = typeId.getText();
				String pw = "";
				for (int i = 0; i < typePass.getPassword().length; i++) {
					pw = pw + typePass.getPassword()[i]; //패스워드를 한 글자씩 가져와서 넣어주는 과정 
				}
				
				InfoChecker(id, pw);
			}
		}
	}
	
	void InfoChecker(String _p, String __p) {
		String id = _p;
		String pass = __p;
		
		String dbId;
		String dbPass;
		
		Connection con = null;
		Statement stmt = null;
		
		String url = "jdbc:mysql://localhost:3306/university?serverTimezone=Asia/Seoul";
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
			
			ResultSet result = stmt.executeQuery("SELECT * FROM user"); 
	
			while (result.next()) { 
				dbId = result.getString("id");
				dbPass = result.getString("password");

				if(dbId.equals(id)&& dbPass.equals(pass)) {
					System.out.println("유저 정보 일치 : 로그인 성공");
					dispose();
					MainFrame mf = new MainFrame();
					mf.setVisible(true);
					break;
				}
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		} 
		
	}
}
