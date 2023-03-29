package ipc;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.StringTokenizer;

public class SubscriberClient
{
    public static void main(String[] args)
    {
        ZMQ.Socket subscriber = null;

        try (ZContext context = new ZContext())
        {

            subscriber = context.createSocket(SocketType.SUB);

            if (subscriber.connect("ipc://yash"))
            {
                System.out.println("Subscriber connected to port 9999");
            }

            else
            {
                System.out.println("Subscriber connection failed");

                System.exit(0);
            }

            subscriber.subscribe("btc");

            subscriber.subscribe("sol");

            while (!Thread.currentThread().isInterrupted())
            {
                String message = subscriber.recvStr(0);

                StringTokenizer tokenizer = new StringTokenizer(message, " ");

                String currency = tokenizer.nextToken();

                long price = Long.parseLong(tokenizer.nextToken());

                System.out.println(currency + " " + price);
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
