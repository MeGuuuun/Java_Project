
public class Operator {
	MyConnector connector = null; //다른 클래스들의 객체 생성 
	LoginFrame lf = null;
	MainFrame mf = null;

	public static void main(String[] args) { //operator가 실행되면 아래와 같은 객체 생성 
		Operator operator = new Operator();
		operator.connector = new MyConnector();
		operator.mf = new MainFrame();
		operator.lf = new LoginFrame(operator); //로그인 프레임 생성자한테 오퍼레이터 객체 넘겨줌 
		

	}
}
