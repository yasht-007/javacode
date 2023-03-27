import java.io.IOException;
import java.net.InetAddress;

public class InetAddressExamle {
    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName("10.20.40.158");
        System.out.println(inetAddress.getHostAddress());
        System.out.println(inetAddress.getCanonicalHostName());
        System.out.println(inetAddress.isReachable(10));    }
}
