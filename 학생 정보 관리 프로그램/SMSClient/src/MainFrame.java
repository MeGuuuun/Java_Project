import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;

public class MainFrame extends JFrame {
	String[] departments = { "컴퓨터공학부", "전자공학과", "기계공학과", "건축공학과" };
	Object[][] students = { { "컴퓨터공학부", "1학년", "홍길동", "학부생", "111111" }, { "컴퓨터공학부", "4학년", "김철수", "학부생", "123467" },
			{ "컴퓨터공학부", "4학년", "이병헌", "학부생", "100011" }, { "컴퓨터공학부", "2학년", "푸하하", "학부생", "145361" },
			{ "컴퓨터공학부", "1학년", "하하하", "학부생", "111551" }, { "컴퓨터공학부", "1학년", "강호동", "학부생", "123111" },
			{ "컴퓨터공학부", "3학년", "이수근", "학부생", "165101" }, { "컴퓨터공학부", "1학년", "서장훈", "학부생", "133411" },
			{ "전자공학과", "1학년", "홍길동", "학부생", "111111" }, { "전자공학과", "1학년", "김철수", "학부생", "123467" },
			{ "전자공학과", "4학년", "이병헌", "학부생", "100011" }, { "전자공학과", "4학년", "푸하하", "학부생", "145361" },
			{ "전자공학과", "1학년", "하하하", "학부생", "111551" }, { "전자공학과", "2학년", "강호동", "학부생", "123111" },
			{ "전자공학과", "1학년", "이수근", "대학원생", "165101" }, { "전자공학과", "3학년", "서장훈", "대학원생", "133411" } };
	
	//공간 제어용
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel westPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	// 메뉴 제작용
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("HOME"); //메뉴 버튼 
	JMenuItem openMI = new JMenuItem("OPEN"); //메뉴를 눌렀을 때 나오는 아이템들 
	JMenuItem newMI = new JMenuItem("NEW");
	JMenuItem exitMI = new JMenuItem("EXIT");
	
	//westPannel 컴포넌트 선언
	JLabel titleLabel = new JLabel("Select Stuedent Type");
	JCheckBox usCheck = new JCheckBox("학부생");
	JCheckBox gsCheck = new JCheckBox("대학원생");
	JComboBox comboBox; //버튼을 누르면 리스트가 나오는 컴포넌트 
	JTree tree;
	
	//JButton exitBtn = new JButton("EXIT");
	DefaultMutableTreeNode root;
	DefaultTreeModel treeModel; //이 두 변수가 트리가 출력되는 것을 책임  

	//센터 패널용 컴포턴트 선언
	JTable table; //표 
	DefaultTableModel tableModel; //표에 있는 내용 책임 
	String columNames[] = { "학과", "학년", "성명", "구분", "학번" };

	MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("학생정보관리시스템 V 0.1");

		//homeMenu 추가 
		homeMenu.add(newMI); //메뉴에 메뉴 아이템 추가 
		homeMenu.addSeparator(); //구분자 
		homeMenu.add(openMI);
		homeMenu.addSeparator();
		homeMenu.add(exitMI);
		mb.add(homeMenu); //메뉴바에 메뉴 추가 
		setJMenuBar(mb); // 메뉴바 영역에 메뉴바 객체 추가 

		// 패널 추가 작업
		westPanel.setPreferredSize(new Dimension(160, basePanel.getHeight()));
		//160으로 고정 + basePanel의 높이 그대로 사용 
		setContentPane(basePanel);
		basePanel.add(westPanel, BorderLayout.WEST);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		westPanel.setLayout(new FlowLayout());
		
		//westPannel 컴포넌트 작업
		comboBox = new JComboBox(departments); //문자열을 콤보박스 리스트에 넣음 
		root = new DefaultMutableTreeNode("학과"); //트리 루트 노드 생성  
		tree = new JTree(root); //트리 생성 + 부모 노드 넣어줌 
		
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
	}

}