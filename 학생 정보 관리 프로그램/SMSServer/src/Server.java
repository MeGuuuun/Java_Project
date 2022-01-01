import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Server {
	ServerSocket ss = null; //서버 소켓 생성 + 접근해오는 클라이언트 허용하고 소켓 반환->ArrayList 
	ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>(); //접속된 클라이언트 관리 
	LogInChecker lc = null;
	
	public static void main(String[] args) {
		Server server = new Server();
		server.lc = new LogInChecker();
		
		try {
			server.ss = new ServerSocket(8080);
			System.out.println("Server> Server socket is created");
			while(true) {
				Socket socket = server.ss.accept(); //반환된 소켓을 담는 객체 
				ConnectedClient c = new ConnectedClient(socket, server);
				server.clients.add(c);
				c.start();
			}
		}catch(Exception e) {
			System.out.println("Server> error!");
		}
	}

}
