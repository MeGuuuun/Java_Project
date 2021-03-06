import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
	ServerSocket ss= null; //서버 소켓 필드 선언 
	ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();
	//클라이언트 소켓 관리할 ArrayList 
	
	public static void main(String[] args) {
		Server server = new Server();
		
		try {
			server.ss = new ServerSocket(8080); //서버 소켓 생성 
			System.out.println("Server> Server Socket is created...");
			
			while(true) {
				Socket socket = server.ss.accept(); //클라이언트 연결을 받는 메소드 + 그걸 담는 소켓
				ConnectedClient c = new ConnectedClient(socket, server); //연결된 클라이언트 소켓 객체 생성 + 소켓과 서버 넘겨줌 
				server.clients.add(c);
				c.start(); //스레드를 시작하는 메소드 
			}
			
		}catch(SocketException e) {
			System.out.println("Server> 소켓 예외 발생 ");
		} catch(IOException e) {
			System.out.println("Server> 입출력 예외 발생 ");
		}
	}

}


class ConnectedClient extends Thread { //클라이언트 각각의 소켓을 처리하기 위해 각각의 스레드가 필요 / 스레드 상속 
	Socket socket;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream inStream;
	DataInputStream dataInStream;
	
	String uName; //유저 이름 필드 
	Server server;
	
	ConnectedClient(Socket _s, Server _ss) { //서버로부터 넘겨받은 소켓 정보와 서버 정보 
		this.socket = _s;
		this.server = _ss;
	}
	
	public void run() {
		try {
			System.out.println("Server> "+this.socket.toString()+"에서의 연결이 되었습니다.");
			
			//클라이언트와 값을 주고 받기 위해 outputStream inputStream 사용 
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			dataOutStream.writeUTF("Welcome to Server!!");
			
			uName = dataInStream.readUTF(); //클라이언트가 보내는 유저 이름 받아들임 
			
			while(true) {
				String msg = dataInStream.readUTF(); //클라이언트가 주는 데이터를 받기 위해 계속 read 상태를 유지 
				System.out.println("Server> "+this.uName+": "+msg);
				
				for(int i=0;i<server.clients.size();i++) {
					if(!(uName.equals(server.clients.get(i).uName))){ //동일한 유저 = 나 자신을 제외한 나머지 모든 클라이언트에게 보내는 조건 
						outStream = server.clients.get(i).socket.getOutputStream(); //outStream 갱신
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(uName + "sent : " + msg); //클라이언트에게 받은 메세지 출력 
					}
				}
			} 
			
		}catch(Exception e) {
			System.out.println("Server> 예외 발생 ");
		}
	}
	
}