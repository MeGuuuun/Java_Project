import java.util.ArrayList;
import java.util.StringTokenizer;
import java.net.*;
import java.io.*;

//파일로 저장한 회원 정보를 확인하는 용도 
public class FileChecker {
	ArrayList<User> userInfo = new ArrayList<User>();
	File dataFile = new File("/Users/jiminpark/Desktop/채팅 프로그램_중간 대체/ChatServer/users.txt"); //파일 생성자엔 경로 적기
	String readData = null; //파일에서 한 줄 한 줄 읽어올 객체 
	StringTokenizer st; //문자열 분리 
	
	FileChecker(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			//외부에서 들어오는 값을 버퍼에서 읽는 것 = BufferedReader
			//BufferedReader를 사용하기 위해서는 FileReader 객체가 반드시 필요 (익명 객체)
			//FileReader 객체를 만들기 위해서는 파일 데이터 필요 (dataFile)
			while((readData=br.readLine())!=null) { //readDate에 readLine으로 한 줄 읽어와 저장 
				st = new StringTokenizer(readData, "!");
				String userId = st.nextToken();
				String userPassword = st.nextToken();
				String userName = st.nextToken();
				String userNumber = st.nextToken();
				User user = new User(userId, userPassword, userName, userNumber);
				userInfo.add(user);
			}
			br.close();
			System.out.println("Server> 유저 파일 읽기 완료 ");
			
		}catch(Exception e) {
			System.out.println("Server > 유저 파일 읽기 에러");
			e.printStackTrace();
		}
	}
	
}

