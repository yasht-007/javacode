package experiment;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;
import java.util.StringTokenizer;

public class PublisherServer
{
    public static void main(String[] args)
    {
        ZMQ.Socket publisher = null;

        try (ZContext context = new ZContext())
        {
            publisher = context.createSocket(SocketType.PUB);

            if (publisher.connect("tcp://*:9999"))
            {
                System.out.println("Publisher connected to port 9999");
            }

            else
            {
                System.out.println("Publisher connection failed");

                System.exit(0);
            }

            publisher.subscribe("btc");

            publisher.subscribe("sol");


            while (!Thread.currentThread().isInterrupted())
            {

                String message = publisher.recvStr(0);

                StringTokenizer tokenizer = new StringTokenizer(message, " ");

                String currency = tokenizer.nextToken();

                long price = Long.parseLong(tokenizer.nextToken());

                System.out.println(currency + " " + price);

//                Thread.sleep(10000);
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
