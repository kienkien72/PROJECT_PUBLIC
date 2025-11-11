import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";
        String encoded = encoder.encode(password);
        System.out.println("Plain: " + password);
        System.out.println("Encoded: " + encoded);
        System.out.println("Matches: " + encoder.matches(password, encoded));
    }
}