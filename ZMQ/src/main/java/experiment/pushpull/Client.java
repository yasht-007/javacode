package experiment.pushpull;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

class Student implements Serializable
{
    int id, age;
    String name;

    public Student()
    {
    }

    public Student(int id, int age, String name)
    {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}

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

            HashMap<String, String> map = null;

            while (!Thread.currentThread().isInterrupted())
            {
//                System.out.println("hi");
//
//                System.out.println(new String(socket.recv()));

                byte[] buffer = socket.recv(0);

                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));

                map = (HashMap<String, String>) objectInputStream.readObject();

                System.out.println(map);

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
