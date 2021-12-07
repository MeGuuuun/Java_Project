import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameFrame extends JFrame {
	
	Socket socket;
	String msg;
	
	JPanel panel = new JPanel(new BorderLayout());
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel btnPanel = new JPanel();
	JLabel name = new JLabel("닉네임을 입력하시오. : ");
	JTextField typeName = new JTextField();
	JButton ok = new JButton("OK");
	
	String nameTag = "NAME";
	
	ChatFrame cf = new ChatFrame(socket);
	
	
	NameFrame(Socket _s){
		socket = _s;
		
		MyActionListener ml = new MyActionListener();
		
		setContentPane(panel);
		panel.add(mainPanel, BorderLayout.CENTER);
		panel.add(btnPanel, BorderLayout.SOUTH);
		
		mainPanel.add(name,BorderLayout.NORTH);
		mainPanel.add(typeName);
		
		btnPanel.add(ok);
		
		name.setPreferredSize(new Dimension(200,40));
		typeName.setPreferredSize(new Dimension(130,30));
		ok.setPreferredSize(new Dimension(70,30));
		
		ok.addActionListener(ml);
		
		setSize(300,150);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //모니터 크기 가져오는 코드 
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			
			//ok 버튼을 누르면 입력한 닉네임을 서버로 보내는 함수로 전달 
			if(b.getText().equals("OK")) {
				String name = typeName.getText();
				sendName(socket, name);
			}
		}
	}
	
	//사용자가 입력한 닉네임을 태그와 함께 보내줌 
	void sendName (Socket _s, String _m) {
		Socket socket = _s;
		String name = _m;
		String info=null;
		
		try {
			OutputStream outStream = socket.getOutputStream();
			DataOutputStream dataOutStream = new DataOutputStream(outStream);
			
			info = nameTag+"!"+name;
			
			dataOutStream.writeUTF(info);
		}catch(Exception e) {
			System.out.println("Client > 닉네임 정보 전송 오류 ");
		}
		
		
	}
}
