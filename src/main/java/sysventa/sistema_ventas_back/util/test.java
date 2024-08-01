package sysventa.sistema_ventas_back.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String x(String pass) {
        return this.bCryptPasswordEncoder.encode(pass);
    }

    public static void main(String[] args) {
        test t = new test();
        System.out.println(t.x("admin"));
    }
}
