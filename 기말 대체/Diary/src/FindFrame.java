import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class FindFrame extends JFrame {
	JPanel panel = new JPanel(new FlowLayout());
	JLabel label = new JLabel("                     힌트");
	JTextArea hint = new JTextArea();
	JButton ok = new JButton("확인");
	
	FindFrame(){
		
		getHint();
		
		setTitle("비밀 다이어리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(hint, BorderLayout.CENTER);
		panel.add(ok, BorderLayout.SOUTH);
		
		label.setPreferredSize(new Dimension(200,40));
		hint.setPreferredSize(new Dimension(230,80));
		ok.setPreferredSize(new Dimension(60,40));
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		
		hint.setEditable(false);
		setSize(250,210);
		setVisible(true);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("확인")) {
				dispose();
			}
		}
	}
	
	
	void getHint() {
		String userHint;
		
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
				userHint = result.getString("user_hint");
				
				this.hint.append(userHint);
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		} 
		
	}
}
