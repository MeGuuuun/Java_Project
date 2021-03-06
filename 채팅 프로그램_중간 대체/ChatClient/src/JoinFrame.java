import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
import java.net.*;


public class JoinFrame extends JFrame {
	Socket socket;
	String joinTag = "JOIN";
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel mainPanel = new JPanel(new FlowLayout());
	JPanel btnPanel = new JPanel(new FlowLayout());
	
	JLabel id = new JLabel("아이디 : ");
	JLabel pass = new JLabel("비밀 번호 : ");
	JLabel name = new JLabel("이름 : ");
	JLabel number = new JLabel("연락처 : (숫자만 입력하시오.)");
	
	JTextField typeId = new JTextField();
	JTextField typePass = new JTextField();
	JTextField typeName = new JTextField();
	JTextField typeNumber = new JTextField();
	
	JButton ok = new JButton("OK");
	JButton cancel = new JButton("CANCEL");
	
	JoinFrame(Socket _s){
		socket = _s;
		
		setContentPane(panel);
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(btnPanel, BorderLayout.SOUTH);
		
		mainPanel.add(id);
		mainPanel.add(typeId);
		mainPanel.add(pass);
		mainPanel.add(typePass);
		mainPanel.add(name);
		mainPanel.add(typeName);
		mainPanel.add(number);
		mainPanel.add(typeNumber);
		
		btnPanel.add(ok);
		btnPanel.add(cancel);
		
		MyActionListener ml = new MyActionListener();
		ok.addActionListener(ml);
		cancel.addActionListener(ml);
		
		id.setPreferredSize(new Dimension(150,40));
		pass.setPreferredSize(new Dimension(150,40));
		name.setPreferredSize(new Dimension(150,40));
		number.setPreferredSize(new Dimension(150,40));
		
		typeId.setPreferredSize(new Dimension(150,40));
		typePass.setPreferredSize(new Dimension(150,40));
		typeName.setPreferredSize(new Dimension(150,40));
		typeNumber.setPreferredSize(new Dimension(150,40));
		
		ok.setPreferredSize(new Dimension(100,40));
		cancel.setPreferredSize(new Dimension(100,40));
		
		
		setSize(350,250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //모니터 크기 가져오는 코드 
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			
			if(b.getText().equals("OK")) {
				String msg = joinTag+"!"+typeId.getText()+"!"+typePass.getText()+"!"+typeName.getText()+"!"+typeNumber.getText();
				
				//ok 버튼을 누르면 입력한 정보가 회원가입 태그와 함께 서버로 보내는 함수로 전달됨 
				sendUserInfo(msg, socket);
				dispose();
			}
			//취소 버튼을 누르면 창이 닫힘.
			else if(b.getText().equals("CANCEL")) {
				dispose();
			}
		}
	}
	
	//회원가입 정보를 서버로 보내는 함수 
	void sendUserInfo(String _m, Socket _s) {
		Socket socket = _s;
		String msg=_m;
		
		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			dos.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("Client > 회원가입 정보 전송 실패 ");
		}
	}
}
