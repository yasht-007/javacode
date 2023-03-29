package experiment;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Stud implements Serializable
{
    int id, age;
    String name;

    public Stud()
    {
    }

    public Stud(int id, int age, String name)
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

    @Override
    public String toString() {
        return "{id: "+id+", name: "+name+", age: "+age+"}";
    }

}

public class exper
{
    public static void main(String[] args)
    {

        List<String> list = new ArrayList<>();

        try (ZContext context = new ZContext(); ZMQ.Socket socket = context.createSocket(SocketType.REP))
        {
            System.out.println("Connect: " + socket.bind("tcp://10.20.40.158:5555"));

            System.out.println("Listening...");

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(socket.recv()));

            list = (List<String>) objectInputStream.readObject();


//            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(socket.recv()));
//
//            list = (List<Stud>) objectInputStream.readObject();

            FileWriter fileWriter = new FileWriter("output.txt");

            System.out.println("Size: " + list.size());

            for (String str : list)
            {
                fileWriter.write(str+"\n");
            }


            System.out.println("completed");

        }
        catch (Exception exception)
        {
            System.out.println("Exception: " + exception);

            exception.printStackTrace();
        }

    }
}
