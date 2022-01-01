import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConnectedClient extends Thread {
	Socket socket;
	Server server;
	
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;
	String msg;
	String uName;
	String loginTag = "LOGIN"; //주고 받는 데이터의 정보를 표시하기 위해 
	String queryTag = "QUERY";
	
	ConnectedClient(Socket _s, Server _server){
		this.server = _server;
		this.socket = _s;
	}
	
	//스레드를 상속받는 클래스는 스레드가 실행되는 위치를 지정해주는 메소드 생성 필수 run
	//run을 부르기 위해선 start 메소드를 호출 해야 함 
	//run 메소드 돌아가면 이 스레드가 병렬적으로 실행되기 시작 
	public void run() {
		try {
			System.out.println("Server> " + this.socket.getInetAddress()+ "에서의 접속이 연결되었습니다.");
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			while(true) {
				msg = dataInStream.readUTF();
				StringTokenizer stk = new StringTokenizer(msg, "//");
				if(stk.nextToken().equals(loginTag)) {
					String id = stk.nextToken();
					String pass = stk.nextToken();
					if(server.lc.check(id, pass)) {
						dataOutStream.writeUTF("LOGIN_OK");
					}else {
						dataOutStream.writeUTF("LOGIN_FAILED");
					}
				}
			}
			
		}catch(Exception e) {
			
		}
	}
}
