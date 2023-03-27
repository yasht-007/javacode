package pushpull;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.StringTokenizer;

public class Client
{
    public static void main(String[] args)
    {
        ZMQ.Socket socket = null;

        try (ZContext context = new ZContext())
        {
            socket = context.createSocket(SocketType.PULL);

            if (socket.connect("tcp://localhost:9999"))
            {
                System.out.println("client connected to port 9999");
            }

            else
            {
                System.out.println("client connection failed");

                System.exit(0);
            }

            while (!Thread.currentThread().isInterrupted())
            {
                String message = socket.recvStr(0);

                System.out.println(message);
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
