import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class Search extends JFrame {
	
	JPanel panel = new JPanel(new FlowLayout());
	JTextArea ta = new JTextArea();
	JScrollPane sp = new JScrollPane(ta);
	
	JLabel label = new JLabel("날짜를 입력하세요. ex) 2021/12/25");
	JTextField typeDate = new JTextField();
	JButton ok = new JButton("검색");
	JButton cancel = new JButton("닫기");
	
	Search(){
		setTitle("날짜별 다이어리 검색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		
		panel.add(label);
		panel.add(typeDate);
		panel.add(ok);
		panel.add(sp);
		panel.add(cancel);
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		cancel.addActionListener(ml);
		
		ta.setEditable(false);
		ta.setLineWrap(true);
		
		ta.setPreferredSize(new Dimension(270, 350));
		label.setPreferredSize(new Dimension(240,40));
		typeDate.setPreferredSize(new Dimension(150,40));
		ok.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		
		
		setSize(320,520);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("검색")) {
				String date = typeDate.getText();
				
				contentSelector(date);
			}
			if(b.getText().equals("닫기")) {
				dispose();
			}
		}
	}
	
	void contentSelector(String _s) {
		Connection con = null;
		Statement stmt = null;
		
		String url = "jdbc:mysql://localhost:3306/Diary?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1234";
		
		AES256 aes256 = new AES256();
		
		String dateNum = _s;

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
			
			ResultSet result = stmt.executeQuery("SELECT * FROM diary where date = '"+dateNum+"';"); 
	
			while (result.next()) { 
				String date = result.getString("date");
				String content = result.getString("content");
				
				String str = date+ "  :  "+aes256.decrypt(content)+"\r\n";
				ta.setText(str);
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.out.println("error");
			System.err.println(ex.getMessage());
		} 
	}
}
