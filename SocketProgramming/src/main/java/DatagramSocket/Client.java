package DatagramSocket;

import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        DatagramSocket datagramSocket = new DatagramSocket();

        String message = "Hello from Client";

        InetAddress ip = InetAddress.getByName("localhost");

        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), ip, 6001);

        datagramSocket.send(datagramPacket);

        datagramSocket.close();

    }
}
