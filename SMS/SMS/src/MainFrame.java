import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.*;
import javax.swing.tree.*;

public class MainFrame extends JFrame{
	String[] departments = {};
	Object students[][] = new Object[0][0];
	
	//공간 제어용
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel westPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	// 메뉴 제작용
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("HOME"); //메뉴 버튼 
	JMenu updateMenu = new JMenu("UPDATE");
	JMenuItem deletMI = new JMenuItem("DELETE"); //메뉴를 눌렀을 때 나오는 아이템들 
	JMenuItem newMI = new JMenuItem("NEW");
	JMenuItem exitMI = new JMenuItem("EXIT");
	
	//westPannel 컴포넌트 선언
	JLabel titleLabel = new JLabel("Select Stuedent Type");
	JCheckBox usCheck = new JCheckBox("학부생");
	JCheckBox gsCheck = new JCheckBox("대학원생");
	JComboBox<?> comboBox; //버튼을 누르면 리스트가 나오는 컴포넌트 
	JTree tree;
	
	//JButton exitBtn = new JButton("EXIT");
	DefaultMutableTreeNode root;
	DefaultTreeModel treeModel; //이 두 변수가 트리가 출력되는 것을 책임  

	//센터 패널용 컴포턴트 선언
	JTable table; //표 
	DefaultTableModel tableModel; //표에 있는 내용 책임 
	DefaultComboBoxModel<String> comboModel;
	String columNames[] = { "학과", "학년", "성명", "구분", "학번" };

	MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("학생정보관리시스템_박지민");

		//homeMenu 추가 
		homeMenu.add(newMI); //메뉴에 메뉴 아이템 추가 
		homeMenu.addSeparator(); //구분자 
		homeMenu.add(deletMI);
		homeMenu.addSeparator();
		homeMenu.add(exitMI);
		mb.add(homeMenu); //메뉴바에 메뉴 추가 
		mb.add(updateMenu);
		setJMenuBar(mb); // 메뉴바 영역에 메뉴바 객체 추가
		
		newMI.addActionListener(new MyMenuListener());
		deletMI.addActionListener(new MyMenuListener());
		exitMI.addActionListener(new MyMenuListener());
		

		// 패널 추가 작업
		westPanel.setPreferredSize(new Dimension(160, basePanel.getHeight()));
		//160으로 고정 + basePanel의 높이 그대로 사용 
		setContentPane(basePanel);
		basePanel.add(westPanel, BorderLayout.WEST);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		westPanel.setLayout(new FlowLayout());
		
		//westPannel 컴포넌트 작업
		comboBox = new JComboBox<Object>(departments); //문자열을 콤보박스 리스트에 넣음 
		root = new DefaultMutableTreeNode("학과"); //트리 루트 노드 생성  
		tree = new JTree(root); //트리 생성 + 부모 노드 넣어줌 
		
		tree.addTreeSelectionListener(new MySelectionListener());
		
		for (int i = 0; i < departments.length; i++) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(departments[i]);
			//트리에 아이템을 넣으려면 차일드 노드를 만들어야 함 , 문자열 요소마다 노드를 만들기 
			root.add(node); //차일드 노드를 루트 노드에 추가 
			treeModel = (DefaultTreeModel) tree.getModel();
			//노드를 묶어서 가지고 있는 것이 모델, 이것을 표현하는 것이 트리 
			treeModel.setRoot(root); //루트를 새롭게 갱신 
		}

		comboBox.setPreferredSize(new Dimension(160, 20));
		titleLabel.setPreferredSize(new Dimension(160, 20));
		tree.setPreferredSize(new Dimension(160, 140));
		
		comboModel = new DefaultComboBoxModel<String>(departments);

		//westPanel.add 
		//flowLayout 이니까 들어간 순서대로 보임 
		westPanel.add(titleLabel);
		westPanel.add(usCheck);
		westPanel.add(gsCheck);
		westPanel.add(comboBox);
		westPanel.add(tree);
		//westPanel.add(exitBtn);
		
		//센터 패널 컴포넌트 작업
		tableModel = new DefaultTableModel(students, columNames) {
			public boolean isCellEditable(int i, int c){ return false; }
		};
		//표에 출력될 데이터 가지고 있을 모델 선언 + 데이터 + 컬럼명 
		//테이블 객체를 생성하려면 테이블 모델이 있어야 함 테이블은 보여주는 것, 모델은 그 테이블을 출력 
		table = new JTable(tableModel);
		JScrollPane sp = new JScrollPane(table); //스크롤이 있는 스크롤팬을 만들고 그 안에 테이블 넣기 

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(sp, BorderLayout.CENTER); //스크롤 팬을 센터패널에 넣기 
		
		setSize(900, 300);
		
		DBselector();
		departSelector();

		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
	}
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			
		}
	}
	
	class MyMenuListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			String menu = e.getActionCommand();
			
			switch(menu) {
				case "NEW" :
					NewFrame nf = new NewFrame();
					nf.setVisible(true);
					tableModel.fireTableDataChanged();
					break;
					
				case "DELETE" :
					DeleteFrame df = new DeleteFrame();
					df.setVisible(true);
					tableModel.fireTableDataChanged();
					break;
					
				case "EXIT" :
					System.exit(0);
					break;
			}
		}
	}
	
	class MySelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			JTree tree = (JTree) e.getSource();
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			String selectedNodeName = selectedNode.toString();
			if (selectedNode.isLeaf()) {
				System.out.println(selectedNodeName);

			}
			
		}
	}
	
	void DBselector() {
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
			
			ResultSet result = stmt.executeQuery("SELECT * FROM students"); 
	
			while (result.next()) { 
				String depart = result.getString("department");
				String year = result.getString("year");
				String name = result.getString("sname");
				String grade = result.getString("grade");
				String num = result.getString("snum");
				
				tableModel.addRow(new Object[]{depart, year, name, grade, num});
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.out.println("error");
			System.err.println(ex.getMessage());
		} 
		
	}
	
	void departSelector() {
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
			
			ResultSet result = stmt.executeQuery("SELECT * FROM departments"); 
	
			while (result.next()) { 
				String depart = result.getString("dname");
				System.out.printf(depart, " ");
				comboModel.addElement(depart);
				
			}
			stmt.close();
			con.close();

		} catch(Exception ex) {
			System.out.println("error");
			System.err.println(ex.getMessage());
		} 
		
	}
}
