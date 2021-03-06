import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class CreateFrame extends JFrame {

	JPanel panel = new JPanel(new BorderLayout());
	JPanel mainPanel = new JPanel();
	JPanel btnPanel = new JPanel(new FlowLayout());
	
	JLabel id = new JLabel("            아이디를 입력하세요.");
	JLabel pass = new JLabel("    비밀번호를 입력하세요.");
	JTextField typeId = new JTextField();
	JPasswordField typePass = new JPasswordField();
	JButton ok = new JButton("생성");
	JButton cancel = new JButton("취소");
	
	CreateFrame(){
		setTitle("회원가입");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(panel);
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(btnPanel, BorderLayout.SOUTH);
		
		mainPanel.add(id);
		mainPanel.add(typeId);
		mainPanel.add(pass);
		mainPanel.add(typePass);
		btnPanel.add(ok);
		btnPanel.add(cancel);
		
		id.setPreferredSize(new Dimension(280,30));
		typeId.setPreferredSize(new Dimension (200,40));
		pass.setPreferredSize(new Dimension(200,30));
		typePass.setPreferredSize(new Dimension(200,40));
		ok.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		cancel.addActionListener(ml);
		
		setSize(280,240);
		setVisible(false);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
	}
	
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("생성")) {
				String id = typeId.getText();
				String pw = "";
				for (int i = 0; i < typePass.getPassword().length; i++) {
					pw = pw + typePass.getPassword()[i]; //패스워드를 한 글자씩 가져와서 넣어주는 과정 
				}
				
				insertUser(id, pw);
				dispose();
			}
			if(b.getText().equals("취소")) {
				dispose();
			}
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
