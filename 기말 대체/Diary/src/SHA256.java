import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//단방향 암호 함수 sha-256
public class SHA256 {

    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //MessageDigest.getInstance() 메소드 인수에 알고리즘 이름 지정해 해당 알고리즘의 해시 값을 계산하는 MessageDigest 구함 
        md.update(text.getBytes()); //digest 값 갱신 / 바이트 코드로 변경 

        return bytesToHex(md.digest()); //16진수로 변환 
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}