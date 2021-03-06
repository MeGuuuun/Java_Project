import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MainFrame extends JFrame {
	
	JPanel panel = new JPanel(new FlowLayout());
	JLabel info = new JLabel("<-달력");
	JTextArea typeTxt = new JTextArea();
	JButton save = new JButton("저장");
	UtilDateModel model = new UtilDateModel();
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("MENU");
	JMenuItem searchMI = new JMenuItem("다이어리 찾기");
	JMenuItem changeMI = new JMenuItem("비밀번호 변경");
	JMenuItem exitMI = new JMenuItem("로그아웃");
	
	MainFrame(){
		
		setTitle("🐹비밀 다이어리🐹");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		
		homeMenu.add(searchMI); //메뉴에 메뉴 아이템 추가 
		homeMenu.addSeparator(); //구분자 
		homeMenu.add(changeMI);
		homeMenu.addSeparator();
		homeMenu.add(exitMI);
		mb.add(homeMenu); //메뉴바에 메뉴 추가 
		setJMenuBar(mb); // 메뉴바 영역에 메뉴바 객체 추가 
		
		panel.add(datePicker);
		panel.add(info);
		panel.add(typeTxt);
		panel.add(save);
		
		
		typeTxt.setLineWrap(true);
		
		typeTxt.setPreferredSize(new Dimension(280,250));
		save.setPreferredSize(new Dimension(60,40));
		info.setPreferredSize(new Dimension(60,40));
		
		MyMenuListener mml = new MyMenuListener();
		searchMI.addActionListener(mml);
		changeMI.addActionListener(mml);
		exitMI.addActionListener(mml);
		
		MyActionListener ml = new MyActionListener();
		save.addActionListener(ml);
		
		
		setSize(300,400);
		datePicker.setBounds(300,300,400,400);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if(b.getText().equals("저장")) {
				String date = model.getYear()+"/"+(model.getMonth()+1)+"/"+model.getDay();
				String content = typeTxt.getText();
				saveDiary(date, content);
				
				typeTxt.setText("");
				JOptionPane.showMessageDialog(null, "저장되었습니다.");
			}
		}
	}
	
	class MyMenuListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			String menu = e.getActionCommand();
			
			switch(menu) {
				case "다이어리 찾기" :
					Search sc = new Search();
					sc.setVisible(true);
					break;
					
				case "비밀번호 변경" :
					ChangePass cp = new ChangePass();
					cp.setVisible(true);
					break;
					
				case "로그아웃" :
					System.exit(0);
					break;
			}
		}
	}
	
	void saveDiary (String _s, String _ss) {
		String date = _s;
		String content = _ss;
		
		AES256 aes256 = new AES256();
		
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
		
		String insertString = "INSERT INTO diary (date, content) VALUES ";

		try {
			String passString = insertString + "('"+ date +"', '"+ aes256.encrypt(content) + "' )";
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			stmt.executeUpdate(passString);
			System.out.println("insert finished");
			typeTxt.setText("");
	
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
