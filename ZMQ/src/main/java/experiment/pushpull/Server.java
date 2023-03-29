package experiment.pushpull;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Server
{
    public static void main(String[] args)
    {
        ZMQ.Socket socket = null;

        try (ZContext context = new ZContext())
        {
            socket = context.createSocket(SocketType.PUSH);

            if (socket.bind("tcp://*:9999"))
            {
                System.out.println("server connected at port 9999");
            }

            else
            {
                System.out.println("server not connected");

                System.exit(0);
            }

            Map<String, String> map = new HashMap<>();

            map.put("1", "yash");
            map.put("2", "nikunj");
            map.put("3", "harsh");

            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(10,20,30,40));

            Random random = new Random(System.currentTimeMillis());

            while (!Thread.currentThread().isInterrupted())
            {

//                long bitcoin, ethereum, solana;
//
//                bitcoin = 28000 + random.nextLong(1000);
//
//                ethereum = 1000 + random.nextLong(200);
//
//                solana = 23 + random.nextLong(3);
//
//                String btcUpdate = "btc " + bitcoin;
//
//                String ethUpdate = "eth " + ethereum;
//
//                String solUpdate = "sol " + solana;

//                String update = btcUpdate + " " + ethUpdate + " " + solUpdate;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ObjectOutputStream oos = new ObjectOutputStream(baos);

                oos.writeObject(map);

                ByteArrayOutputStream bint = new ByteArrayOutputStream();

                ObjectOutputStream oint = new ObjectOutputStream(baos);

                oos.writeObject(list);

                socket.send(baos.toByteArray());

//                Thread.sleep(10000);
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
