
public class User {
	//회원가입 시 필요한 정보 =아이디, 비밀번호, 이름, 번호 
	String id = null;
	String pass = null;
	String name = null;
	String number = null;
	
	User(String _id, String _pass, String _name, String _num) {
		id = _id;
		pass = _pass;
		name = _name;
		number = _num;
	}
}
