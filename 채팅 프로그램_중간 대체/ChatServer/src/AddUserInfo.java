
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.*;

//회원정보를 추가해 파일에 저장하는 용도 
public class AddUserInfo {
	Socket socket;
	StringTokenizer st;
	File dataFile = new File("/Users/jiminpark/Desktop/채팅 프로그램_중간 대체/ChatServer/users.txt"); //파일 생성자엔 경로 적기 (상대경로)
	
	AddUserInfo(Socket _s, String _m){
		Socket socket = _s;
		String msg = _m;
		st = new StringTokenizer(msg,"!");
		String tag = st.nextToken();
		String id = st.nextToken();
		String pass = st.nextToken();
		String name = st.nextToken();
		String number = st.nextToken();
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile,true));
			//BufferedWriter를 이용해 파일에 적기 
			String info = id+"!"+pass+"!"+name+"!"+number+"\r";
			bw.write(info);
			
			
			System.out.println("Server > 회원가입 정보 저장 완료.");
			
			bw.close(); //꼭 닫아줌 
			
			
		}catch(Exception e) {
			System.out.println("Server > 회원가입 정보 저장 실패 ");
			
		}
	}
}
