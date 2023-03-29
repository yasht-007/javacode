package experiment;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;
import java.util.StringTokenizer;

public class SubscriberClient
{
    public static void main(String[] args)
    {
        ZMQ.Socket subscriber = null;

        try (ZContext context = new ZContext())
        {

            subscriber = context.createSocket(SocketType.SUB);

            if (subscriber.bind("tcp://localhost:9999"))
            {
                System.out.println("Subscriber binded to port 9999");
            }

            else
            {
                System.out.println("Subscriber connection failed");

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

                subscriber.send(btcUpdate, 0);

                subscriber.send(ethUpdate, 0);

                subscriber.send(solUpdate, 0);


            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            if (subscriber != null)
            {
                subscriber.close();
            }
        }
    }
}
