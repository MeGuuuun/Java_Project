import java.io.*;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LoginChecker {
	Socket socket;
	StringTokenizer st;
	
	FileChecker fc = new FileChecker(); //파일에 저장된 회원정보 확인 
	
	String log_confirmTag = "LOGIN_OK";
	String failedTag = "FAILED";
	
	LoginChecker(String _m, Socket _s) {
		String msg = _m;
		Socket socket = _s;
		
		st = new StringTokenizer(msg, "!");
		String tag = st.nextToken();
		String id = st.nextToken();
		String pass = st.nextToken();
		
		
		try {
			OutputStream outStream = socket.getOutputStream();
			DataOutputStream dataOutStream = new DataOutputStream(outStream);
			
			//boolean 함수 값에 따라 클라이언트에게 확인/실패 태그를 보냄 
			if(Check(id, pass)) {
				
				dataOutStream.writeUTF(log_confirmTag);
			}
			else {
				System.out.println("Server > 로그인 확인 실패 ");
				dataOutStream.writeUTF(failedTag);
			}
			
		}catch(Exception e) {
			System.out.println("Server > 로그인 확인 정보 전송 오류 ");
		}
		
		
		
		
	}
	
	//파일 속 회원정보와 대조하는 boolean 함수 
	boolean Check(String _id, String _pass) {
		boolean flag = false;
		for(int i=0; i<fc.userInfo.size() ;i++) {
			if(fc.userInfo.get(i).id.equals(_id) && fc.userInfo.get(i).pass.equals(_pass)) {
				flag = true;
			} //get 메소드 = i 인덱스를 가진 데이터 값 가져오기 
		}
		return flag;
	}
}


