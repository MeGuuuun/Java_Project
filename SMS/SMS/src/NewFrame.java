import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class NewFrame extends JFrame {
	
	MainFrame mf = new MainFrame();
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel main = new JPanel(new FlowLayout());
	JPanel btn = new JPanel(new FlowLayout());
	
	JLabel depart = new JLabel("학부/학과 : ");
	JTextField typeDepart = new JTextField();
	JLabel year = new JLabel("학년 : ");
	JTextField typeYear = new JTextField();
	JLabel name = new JLabel("이름 : ");
	JTextField typeName = new JTextField();
	JLabel grade = new JLabel("학부생/대학원생 : ");
	JTextField typeGrade = new JTextField();
	JLabel num = new JLabel("학번 : ");
	JTextField typeNum = new JTextField();
	
	JButton ok = new JButton("확인");
	JButton cancel = new JButton("취소");
	
	NewFrame(){
		setTitle("새로운 학생 등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		
		panel.add(main, BorderLayout.CENTER);
		panel.add(btn, BorderLayout.SOUTH);
		
		main.add(depart);
		main.add(typeDepart);
		main.add(year);
		main.add(typeYear);
		main.add(name);
		main.add(typeName);
		main.add(grade);
		main.add(typeGrade);
		main.add(num);
		main.add(typeNum);
		
		btn.add(ok);
		btn.add(cancel);
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		cancel.addActionListener(ml);
		
		depart.setPreferredSize(new Dimension(100,30));
		typeDepart.setPreferredSize(new Dimension(150,30));
		year.setPreferredSize(new Dimension(100,30));
		typeYear.setPreferredSize(new Dimension(150,30));
		name.setPreferredSize(new Dimension(100,30));
		typeName.setPreferredSize(new Dimension(150,30));
		grade.setPreferredSize(new Dimension(100,30));
		typeGrade.setPreferredSize(new Dimension(150,30));
		num.setPreferredSize(new Dimension(100,30));
		typeNum.setPreferredSize(new Dimension(150,30));
		
		ok.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		
		
		
		setSize(310,280);
		setVisible(false);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("확인")) {
				String depart = typeDepart.getText();
				String year = typeYear.getText();
				String name = typeName.getText();
				String grade = typeGrade.getText();
				String num = typeNum.getText();
				
				addNewUser(depart, year, name, grade, num);
				dispose();
				
			}
			if(b.getText().equals("취소")) {
				dispose();
			}
		}
	}
	
	void addNewUser(String _s, String _ss, String _sss, String _ssss, String _sssss) {
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://localhost:3306/university?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1234";
		
		String depart = _s;
		int year = Integer.parseInt(_ss);
		String name = _sss;
		String grade = _ssss;
		String num = _sssss;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
			return;
		}
		
		String insertString = "INSERT INTO students (department, year, sname, grade, snum) VALUES ";

		try {
			String passString = insertString + "('"+ depart +"', '"+ year + "', '"+name+"', '"+grade+"', '"+num+"' )";
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
