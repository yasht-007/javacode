package requestresponse;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class RequestClient
{
    public static void main(String[] args)
    {
        ZMQ.Socket socket = null;

        try (ZContext context = new ZContext())
        {
            System.out.println("Connecting to hello world server");

            socket = context.createSocket(SocketType.REQ);

            socket.connect("tcp://localhost:5555");

            for (int counter = 0; counter != 10; counter++)
            {
                String request = "Hello";

                System.out.println("Sending Hello " + counter);

                socket.send(request.getBytes(ZMQ.CHARSET), 0);

                byte[] reply = socket.recv(0);

                System.out.println("Received " + new String(reply, ZMQ.CHARSET) + " " + counter);
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (socket != null)
            {
                socket.close();
            }
        }
    }
}