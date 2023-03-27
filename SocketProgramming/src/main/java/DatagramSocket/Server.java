package DatagramSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) throws IOException {

        DatagramSocket datagramSocket = new DatagramSocket(6001);

        byte[] buffer = new byte[1024];

        DatagramPacket datagramPacket = new DatagramPacket(buffer, 1024);

        datagramSocket.receive(datagramPacket);

        String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

        System.out.println(message);

        datagramSocket.close();

    }
}
