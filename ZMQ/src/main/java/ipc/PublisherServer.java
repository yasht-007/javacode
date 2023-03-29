package ipc;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public class PublisherServer
{
    public static void main(String[] args)
    {
        ZMQ.Socket publisher = null;

        try (ZContext context = new ZContext())
        {
            publisher = context.createSocket(SocketType.PUB);

            if (publisher.bind("ipc://yash"))
            {
                System.out.println("Publisher binded to port 9999");
            }

            else
            {
                System.out.println("Publisher connection failed");

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

                publisher.send(btcUpdate, 0);

                publisher.send(ethUpdate, 0);

                publisher.send(solUpdate, 0);

                Thread.sleep(10000);
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (publisher != null)
            {
                publisher.close();
            }
        }
    }
}
