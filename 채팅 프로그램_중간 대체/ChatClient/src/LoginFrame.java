import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class LoginFrame extends JFrame {
	
	Socket socket;
	Client c = new Client();
	String msg=null;
	
	String loginTag="LOGIN";
	
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언
	JButton enter = new JButton("Login"); // Button enter 선언
	JButton cancel = new JButton("Cancel"); // Button enter 선언
	JButton join = new JButton("Join");
	JTextField typeId = new JTextField(); // id 받은곳 선언
	JPasswordField typePassword = new JPasswordField(); // password 받은곳 선언 받으면 ** < 처럼 나옴
	JLabel id = new JLabel("I   D"); // 라벨 type id
	JLabel password = new JLabel("Password"); // 라벨 type password

	public LoginFrame(Socket _s) {
		socket = _s;
		
		MyActionListener ml = new MyActionListener();
		setTitle("LOGIN with Server");
		id.setPreferredSize(new Dimension(70, 30));
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(70, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		enter.setPreferredSize(new Dimension(120, 30));
		cancel.setPreferredSize(new Dimension(120, 30));
		join.setPreferredSize(new Dimension(120,30));
		panel.add(id); // ID 추가
		panel.add(typeId); // 입력된 ID 추가
		panel.add(password); // PASSWORD 추가
		panel.add(typePassword); // 입력된 PASSWORD 추가
		panel.add(enter);
		panel.add(join);
		panel.add(cancel);
		
		setContentPane(panel);
		enter.addActionListener(ml); // Login 버튼에 이벤트 리스너 추가
		cancel.addActionListener(ml); // Cancel 버튼에 이벤트 리스너 추가
		join.addActionListener(ml); //join 버튼에 이벤트 리스너 추가 
		
		setResizable(false);
		setSize(400, 150);
		
		//로그인 창을 화면 중앙에 배치시키기...
		Dimension frameSize = this.getSize(); // 프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //모니터 크기 가져오는 코드 
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
				
				System.out.println("입력받은 로그인 정보 : " +typeId.getText() + "!" + pw); //옵션 : 확인용 
				
				//로그인 정보를 서버로 보내는 void 함수로 정보 전달 
				sendLogin(typeId.getText(),pw,socket);
				
				//회원가입 버튼을 누를 시 회원가입 창이 뜨도록 함.
			}else if(b.getText().equals("Join")){ 
				JoinFrame jf = new JoinFrame(socket);
				jf.setVisible(true);
				
			//취소 버튼을 누를 시 입력창 초기화 
			}else if (b.getText().equals("Cancel")) {
				typeId.setText("");
				typePassword.setText("");
			}
		}
	}
	
	//입력받은 로그인 정보를 서버로 보내는 함수 
	void sendLogin (String _id, String _pass,Socket _s) {
		Socket socket = _s;
		String id=_id;
		String pass = _pass;
		String info=null;
		
		try {
			OutputStream outStream = socket.getOutputStream();
			DataOutputStream dataOutStream = new DataOutputStream(outStream);
			
			info = loginTag + "!" + id + "!" + pass;
			//로그인 태그와 구분자를 넣어 보냄 .
			
			dataOutStream.writeUTF(info);
		}catch(Exception e) {
			System.out.println("Client > 로그인 정보 전송 오류 ");
		}
		
		
	}
	
}




