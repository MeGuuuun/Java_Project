import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteFrame extends JFrame {
	
	MainFrame mf = new MainFrame();
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel main = new JPanel(new FlowLayout());
	JPanel btn = new JPanel(new FlowLayout());
	
	JLabel name = new JLabel("이름 : ");
	JTextField typeName = new JTextField();
	JLabel num = new JLabel("학번 : ");
	JTextField typeNum = new JTextField();
	
	JButton ok = new JButton("확인");
	JButton cancel = new JButton("취소");
	
	DeleteFrame(){
		setTitle("학생 정보 삭제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		
		panel.add(main, BorderLayout.CENTER);
		panel.add(btn, BorderLayout.SOUTH);
		
		main.add(name);
		main.add(typeName);
		main.add(num);
		main.add(typeNum);
		
		btn.add(ok);
		btn.add(cancel);
		
		name.setPreferredSize(new Dimension(100,30));
		typeName.setPreferredSize(new Dimension(150,30));
		num.setPreferredSize(new Dimension(100,30));
		typeNum.setPreferredSize(new Dimension(150,30));
		
		ok.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		cancel.addActionListener(ml);
		
		
		setSize(310,170);
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
				String name = typeName.getText();
				String num = typeNum.getText();
				
				deleteUser(name, num);
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				dispose();
			}
			if(b.getText().equals("취소")) {
				dispose();
			}
		}
	}
	
	void deleteUser(String _s, String _ss) {
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://localhost:3306/university?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1234";
		
		String name = _s;
		String num = _ss;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
			return;
		}

		try {
			String deleteString = "DELETE FROM students WHERE sname = '" +name+"' AND snum = '"+num+"';";
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			stmt.executeUpdate(deleteString);
			System.out.println("delete finished");
			mf.tableModel.fireTableDataChanged();
			
	
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
