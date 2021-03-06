import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언
	JButton enter = new JButton("Login"); // Button enter 선언
	JButton cancel = new JButton("Cancel"); // Button enter 선언
	JTextField typeId = new JTextField(); // id 받은곳 선언
	JPasswordField typePassword = new JPasswordField(); // password 받은곳 선언 받으면 ** < 처럼 나옴
	JLabel id = new JLabel("I   D"); // 라벨 type id
	JLabel password = new JLabel("Password"); // 라벨 type password
	MyConnector connector;
	Operator mainOperator = null;

	public LoginFrame(Operator _o) {
		mainOperator = _o; //넘어온 오퍼레이터 객체 
		connector = _o.connector; //오퍼레이터가 가지고 있는 커넥터가 담겨짐(소켓 정보 필요하기 때문)
		
		MyActionListener ml = new MyActionListener();
		setTitle("학생관리시스템 LOGIN with Server");
		id.setPreferredSize(new Dimension(70, 30)); //PreferredSize 크기 정하는 메소드, Dimension 객체의 크기만큼 
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(70, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		enter.setPreferredSize(new Dimension(185, 30));
		cancel.setPreferredSize(new Dimension(185, 30));
		panel.add(id); // ID 추가
		panel.add(typeId); // 입력된 ID 추가
		panel.add(password); // PASSWORD 추가
		panel.add(typePassword); // 입력된 PASSWORD 추가
		panel.add(enter);
		panel.add(cancel);
		setContentPane(panel);
		enter.addActionListener(ml); // Login 버튼에 이벤트 리스너 추가
		cancel.addActionListener(ml); // Cancel 버튼에 이벤트 리스너 추가

		setResizable(false);
		setSize(400, 150);
		
		//로그인 창을 화면 중앙에 배치시키기...
		Dimension frameSize = this.getSize(); // 프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //모니터 크기 가져오는 코드 
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		setVisible(true);
	}

	class MyActionListener implements ActionListener {
		// 이벤트를 발생시킨 컴포넌트(소스)
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Login")) { // 로그인버튼을 누르면...
				
				//Password 컴포넌트에서 문자열 읽어오기 1
				String pw = "";
				for (int i = 0; i < typePassword.getPassword().length; i++) {
					pw = pw + typePassword.getPassword()[i]; //패스워드를 한 글자씩 가져와서 넣어주는 과정 
				}
				
				System.out.println(typeId.getText() + "//" + pw); //옵션 : 확인용 
				
				if (connector.sendLoginInformation(typeId.getText(), pw)) {
					mainOperator.mf.setVisible(true); //메인 오퍼레이터 코드에 set visible 코드 없음 여기서 실행 
					dispose(); //지금 뜨는 창을 안 보이게 하는 메소드 - 로그인 창이 닫히고 메인 프레임 창이 뜸 
				} else {
					System.out.println("Log in Error~~~");
		
				}

			} else if (b.getText().equals("Cancel")) {
				typeId.setText("");
				typePassword.setText("");
			}
		}
	}
}