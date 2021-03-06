//2018680066 박지민 - 중간고사 대체과제 - 채팅 프로그램 

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	Socket socket = null; //클라이언트 소켓 생성 
	

	public static void main(String[] args) {
		Client client = new Client();

		try {
			
			client.socket = new Socket("localhost", 60000); // IP와 포트번호
			System.out.println("Client > 서버로 연결되었습니다. ");
			
			MessageListener ml = new MessageListener(client.socket); //서버로부터 오는 정보를 받을 스레드 
			ml.start();
			

		} catch (Exception e) {
			System.out.println("Client > 서버 연결 실패 ");
		}

	}
}

class MessageListener extends Thread{
	Socket socket;
	InputStream inStream;
	DataInputStream dataInStream;
	String uName;
	
	//정보의 종류를 파악할 수 있는 태그. 서버와 통일함. 
	String joinTag = "JOIN";
	String log_confirmTag = "LOGIN_OK";
	String chatTag = "CHAT";
	String failedTag = "FAILED";
	String connectTag = "CONNECTED";
	String unconnectTag = "UNCONNECTED";
	
	StringTokenizer st;
	
	MessageListener(Socket _s){
		socket = _s;
	}
	
	public void run() {
		
		try {
			inStream = socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			NameFrame nf = new NameFrame(socket);
			LoginFrame lf = new LoginFrame(socket);
			JoinFrame jf = new JoinFrame(socket);
			ChatFrame cf = new ChatFrame(socket);
			
			while(true) {
				String msg = dataInStream.readUTF(); //readUTF로 서버에서 오는 메세지 읽음 
				st = new StringTokenizer(msg, "!");
				
				String tag = st.nextToken();
				
				//로그인 확인 정보를 받았을 때 
				if(msg.equals(log_confirmTag)) {
					lf.dispose();
					nf.setVisible(true);
				}
				//로그인 실패 시 
				if(msg.equals(failedTag)) {
					lf.typeId.setText("");
					lf.typePassword.setText("");
				}
				//닉네임 정보를 서버에 저장 완료 했을 시 
				if(tag.equals("FINE")) {
					nf.dispose();
					cf.setVisible(true);
				}
				//채팅 메세지를 주고 받을 때 
				if(tag.equals(chatTag)) {
					cf.readChat(msg);
				}
				//소켓의 연결 여부를 확인할 때 
				if(tag.equals(connectTag)) {
					String user = st.nextToken();
					if(!cf.model.contains(user)) {
						cf.model.addElement(user);
					}
				}
				if(tag.equals(unconnectTag)) {
					String user = st.nextToken();
					cf.model.removeElement(user);
				}
			}
		}catch(Exception e) {
			System.out.println("Server > 서버 데이터 수신 실패 ");
		}
		
	}
}