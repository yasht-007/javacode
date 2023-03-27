import java.io.IOException;
import java.net.URL;

public class UrlExample {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https//www.javatpoint.com/java-tutorial");
        System.out.println(url.getAuthority());
        System.out.println(url.getContent());
        System.out.println(url.getPath());
        System.out.println(url.getDefaultPort());
        System.out.println(url.getHost());
        System.out.println(url.getProtocol());
        System.out.println(url.getUserInfo());
        System.out.println(url.getPort());
    }
}
