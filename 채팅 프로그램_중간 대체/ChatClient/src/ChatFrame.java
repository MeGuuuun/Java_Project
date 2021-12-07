import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatFrame extends JFrame {
	Socket socket = null;
	String msg=null;
	
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;

	JPanel Panel = new JPanel(new BorderLayout());
	JPanel btnPanel = new JPanel(new BorderLayout());
	JPanel listPanel = new JPanel(new BorderLayout());
	JTextArea recv = new JTextArea(7, 20); //주고받는 메세지가 보일 창 
	JTextField send = new JTextField(20); //메세지를 입력할 입력창 
	JButton sendBtn = new JButton("send"); //보내기 버튼 
	JScrollPane sp = new JScrollPane(recv);
	JList userList = new JList(new DefaultListModel()); //동시 접속자를 보여줄 JList
	DefaultListModel model = (DefaultListModel) userList.getModel();
	JLabel info = new JLabel("현재 접속 중");
	
	String chatTag = "CHAT";
	

	ChatFrame(Socket _s) {
		socket = _s;
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("채팅창");

		setContentPane(Panel);
		MyActionListener mal = new MyActionListener();

		Panel.add(sp, BorderLayout.CENTER);
		Panel.add(btnPanel, BorderLayout.SOUTH);
		Panel.add(listPanel, BorderLayout.EAST);
		
		listPanel.add(info,BorderLayout.NORTH);
		listPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

		recv.setLineWrap(true);
		recv.setWrapStyleWord(true);
		recv.setEditable(false);

		btnPanel.add(send, BorderLayout.CENTER);
		btnPanel.add(sendBtn, BorderLayout.EAST);

		recv.setPreferredSize(new Dimension(300, 200));
		send.setPreferredSize(new Dimension(250, 50));
		sendBtn.setPreferredSize(new Dimension(50, 40));
		listPanel.setPreferredSize(new Dimension(80,180));

		sendBtn.addActionListener(mal);

		setSize(400, 350);
		setResizable(false);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //모니터 크기 가져오는 코드 
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			
			if(b.getText().equals("send")) {
				msg=chatTag+"!"+send.getText();
				
				//보내기 버튼을 누르면 메세지를 서버로 보내는 void 함수로 소켓과 메세지 정보를 넘김 
				sendChat(socket, msg);
				send.setText("");
			}
		}
	}
	
	//서바로 메세지를 보내는 함수 
	void sendChat (Socket _s, String _m) {
		Socket socket = _s;
		String msg = _m;
		
		try {
			OutputStream outStream = socket.getOutputStream();
			DataOutputStream dataOutStream = new DataOutputStream(outStream);
			
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("Client > 채팅 메세지 전송 오류 ");
			System.out.println(e.toString());
		}
	}
	
	//함수가 보내는 메세지를 읽어 textArea에 보여주는 함수 
	void readChat(String _m){
		String msg = _m;
		StringTokenizer st = new StringTokenizer(msg,"!");
		String tag = st.nextToken();
		String message = st.nextToken();
		
		recv.append(message+"\n");
	}
	
}

