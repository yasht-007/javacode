package pushpull;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public class Server
{
    public static void main(String[] args)
    {
        ZMQ.Socket socket = null;

        try (ZContext context = new ZContext())
        {
            socket = context.createSocket(SocketType.PUSH);

            if (socket.bind("tcp://localhost:9999"))
            {
                System.out.println("server connected at port 9999");
            }

            else
            {
                System.out.println("server not connected");

                System.exit(0);
            }

            Random random = new Random(System.currentTimeMillis());

            while (!Thread.currentThread().isInterrupted())
            {

                long bitcoin, ethereum, solana;

                bitcoin = 28000 + random.nextLong(1000);

                ethereum = 1000 + random.nextLong(200);

                solana = 23 + random.nextLong(3);

                String btcUpdate = "btc " + bitcoin;

                String ethUpdate = "eth " + ethereum;

                String solUpdate = "sol " + solana;

                String update = btcUpdate + " " + ethUpdate + " " + solUpdate;

                socket.send(update, 0);

                Thread.sleep(10000);
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
