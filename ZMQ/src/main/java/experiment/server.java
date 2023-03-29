package experiment;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class server
{
    public static void main(String[] args)
    {

        List<Stud> list = new ArrayList<>();
        {

            for (int i = 0; i < 10; i++)
            {
                list.add(new Stud(i, i + 1, "Nikunj"));
                for (int j = 0; j < 100; j++)
                {
                    for (int k = 0; k < 100; k++)
                    {
                        for (int x = 0; x < 10; x++)
                        {
                            list.add(new Stud(i, i + 1, "Nikunj"));
                        }
                    }
                }
            }

//            list.add(new Student(1, 22, "Nikunj"));

        }

        try (ZContext context = new ZContext();

             ZMQ.Socket socket = context.createSocket(ZMQ.REQ))
        {
            System.out.println(socket.connect("tcp://localhost:5555"));

            System.out.println("Sending...");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(list);

            socket.send(baos.toByteArray());

            System.out.println("Message sent...");
        }
        catch (Exception exception)
        {
            System.out.println("Exception: " + exception);

            exception.printStackTrace();
        }
    }
}
