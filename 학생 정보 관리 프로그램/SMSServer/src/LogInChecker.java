import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LogInChecker {
	ArrayList<User> userInfo = new ArrayList<User>();
	File dataFile = new File("/Users/jiminpark/Desktop/학생 정보 관리 프로그램/SMSServer/users.txt"); //파일 생성자엔 경로 적기 (상대경로)
	String readData = null; //파일에서 한 줄 한 줄 읽어올 객체 
	StringTokenizer st; //문자열 분리 
	
	LogInChecker() {
		//파일 입출력 또한 예외 발생 시킬 수 있음 
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			//외부에서 들어오는 값을 버퍼에서 읽는 것 = BufferedReader
			//BufferedReader를 사용하기 위해서는 FileReader 객체가 반드시 필요 (익명 객체)
			//FileReader 객체를 만들기 위해서는 파일 데이터 필요 (dataFile)
			while((readData=br.readLine())!=null) { //readDate에 readLine으로 한 줄 읽어와 저장 
				st = new StringTokenizer(readData, "//");
				String userId = st.nextToken();
				String userPassword = st.nextToken();
				User user = new User(userId, userPassword);
				userInfo.add(user);
			}
			for(int i=0; i<userInfo.size() ;i++) {
				System.out.println("회원 정보 : "+ userInfo.get(i).id + "//" + userInfo.get(i).pass);
			}
			br.close();
		}catch(Exception e) {
			
		}
		
	}
	
	boolean check(String _id, String _pass) {
		boolean flag = false; //초기화 
		for(int i=0; i<userInfo.size() ;i++) {
			if(userInfo.get(i).id.equals(_id) && userInfo.get(i).pass.equals(_pass)) {
				flag = true;
			} //get 메소드 = i 인덱스를 가진 데이터 값 가져오기 
		}
		return flag;
	}
}
