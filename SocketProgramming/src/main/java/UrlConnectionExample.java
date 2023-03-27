import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnectionExample {
    public static void main(String[] args) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://www.google.com/").openConnection();
        httpURLConnection.connect();

        System.out.println(httpURLConnection.getRequestMethod());

        System.out.println(httpURLConnection.getResponseCode());

        System.out.println(httpURLConnection.getResponseMessage()+"\n");

        for (int i = 1; i <= 8; i++) {
            System.out.println(httpURLConnection.getHeaderFieldKey(i) + " = " + httpURLConnection.getHeaderField(i));
        }

        httpURLConnection.disconnect();

    }
}
