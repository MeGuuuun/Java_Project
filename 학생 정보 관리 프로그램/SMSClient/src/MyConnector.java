import java.io.*;
import java.net.*;

public class MyConnector {
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;

	final String loginTag = "LOGIN";
	final String queryTag = "QUERY";

	MyConnector() {
		try {
			mySocket = new Socket("localhost", 8080);
			System.out.println("CLIENT LOG> 서버로 연결되었습니다."); //소켓 만들어짐 
			outStream = mySocket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = mySocket.getInputStream();
			dataInStream = new DataInputStream(inStream); //입출력 가능하게 하기 위한 데이터 입출력 객체 
			Thread.sleep(100);
		} catch (Exception e) {

		}
	}

	boolean sendLoginInformation(String uid, String upass) {
		boolean flag = false;
		String msg = null;
		try {
			dataOutStream.writeUTF(loginTag + "//" + uid + "//" + upass);
			msg = dataInStream.readUTF(); //서버로부터 로그인 정보가 들어올 때 까지 기다림 
		} catch (Exception e) {

		}

		if (msg.equals("LOGIN_OK")) {
			return true;
		} else {
			return false;
		}
	}

}