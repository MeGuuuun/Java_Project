//2018680066 박지민 - 중간고사 대체과제 - 채팅 프로그램 

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	ServerSocket ss= null; //서버 소켓 필드 선언 
	ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>(); //클라이언트 소켓 관리할 ArrayList
	ArrayList<String> users = new ArrayList<>(); //연결된 소켓의 닉네임 관리하는 ArrayList 
	
	public static void main(String[] args) {
		Server server = new Server();
	
		try {
			server.ss = new ServerSocket(60000); //서버 소켓 생성 
			System.out.println("Server> Server Socket is created...");
			
			while(true) {
				Socket socket = server.ss.accept(); //클라이언트 연결을 받는 메소드 + 그걸 담는 소켓
				ConnectedClient c = new ConnectedClient(socket, server);
				server.clients.add(c);
				c.start();
			}
		}catch(Exception e) {
			System.out.println("Server> 소켓 예외 발생 ");
		} 
	}

}

class ConnectedClient extends Thread{
	Socket socket;
	Server server;
	String msg;
	StringTokenizer st;
	String uName=null;
	
	InputStream inStream;
	DataInputStream dataInStream;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	
	
	//클라이언트가 데이터를 확인할 때 필요한 태그 선언 
	String loginTag = "LOGIN";
	String joinTag = "JOIN";
	String chatTag = "CHAT";
	String nameTag = "NAME";
	String log_confirmTag = "LOGIN_OK";
	String failedTag = "FAILED";
	String connectTag = "CONNECTED";
	String unconnectTag = "UNCONNECTED";
	
	
	ConnectedClient(Socket _s, Server _ss){
		socket = _s;
		server = _ss;
	}
	
	public void run() {
		
		try {
			inStream = socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			outStream = socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			
			while(true) {
				
				String msg=null;
				msg = dataInStream.readUTF();
				st = new StringTokenizer(msg, "!");
				System.out.println(msg);
				
				String tag = st.nextToken();
				
				//로그인 태그와 함께 넘어온 정보를 LoginChecker로 넘겨줌 
				if(tag.equals(loginTag)) {
					LoginChecker lc_ = new LoginChecker(msg, socket);
				}
				
				//회원가입 태그와 함께 넘어온 정보를 AddUserInfo로 넘겨줌 
				if(tag.equals(joinTag)) {
					AddUserInfo us = new AddUserInfo(socket, msg);
				}
				
				//클라이언트가 보낸 닉네임을 ArrayList에 저장한 후 클라이언트에게 신호를 줌.
				//채팅방에 입장 문구를 표시. 
				if(tag.equals(nameTag)) {
					uName = st.nextToken();
					server.users.add(uName);
					dataOutStream.writeUTF("FINE");
					for(int i=0;i<server.clients.size();i++) {
						OutputStream os = server.clients.get(i).socket.getOutputStream();
						DataOutputStream dos = new DataOutputStream(os);
						dos.writeUTF(chatTag+"!"+uName+" 님이 입장하셨습니다. ");
					}
				}
				
				//채팅 태그와 함께 넘어온 메세지를 연결된 모든 클라이언트 소켓에 보내는 용도 
				if(tag.equals(chatTag)) {
					String message = st.nextToken();
					for(int i=0;i<server.clients.size();i++) {
						OutputStream os = server.clients.get(i).socket.getOutputStream();
						DataOutputStream dos = new DataOutputStream(os);
						dos.writeUTF(chatTag+"!"+uName+" : "+message);
					}
				}
				
				//닉네임이 입력되어있고 소켓이 연결되어있을 때, users에 들어있는 닉네임을 모두 클라이언트로 보내줌.
				if(uName!=null) {
					if(socket.isClosed()) {
						for(int i=0;i<server.clients.size();i++) {
							OutputStream os = server.clients.get(i).socket.getOutputStream();
							DataOutputStream dos = new DataOutputStream(os);
							
							dos.writeUTF(unconnectTag+"!"+uName);
							
						}
					}else {
						for(int i=0;i<server.clients.size();i++) {
							OutputStream os = server.clients.get(i).socket.getOutputStream();
							DataOutputStream dos = new DataOutputStream(os);
							
							for(int j=0;j<server.users.size();j++) {
								dos.writeUTF(connectTag+"!"+server.users.get(j));
							}
						}
					}
				}
			}
					
			
		}catch(Exception e) {
			System.out.println("Server > 클라이언트 데이터 수신 오류 ");
			System.out.println(e.toString());
		}
	}
}

