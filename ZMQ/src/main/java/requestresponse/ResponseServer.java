package requestresponse;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ResponseServer
{
    public static void main(String[] args)
    {
        ZMQ.Socket socket = null;

        try (ZContext context = new ZContext())
        {
            socket = context.createSocket(SocketType.REP);

            socket.bind("tcp://*:5555");

            while (!Thread.currentThread().isInterrupted())
            {
                byte[] reply = socket.recv(0);

                System.out.println("Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                Thread.sleep(1000);

                String response = "world";

                socket.send(response.getBytes(ZMQ.CHARSET), 0);
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
