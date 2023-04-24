package eventbus;

import eventbus.util.Student;
import eventbus.util.StudentCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class Sender extends AbstractVerticle
{

    public static void main(String[] args)
    {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(Sender.class.getName());
    }

    @Override
    public void start() throws Exception
    {

        EventBus eventBus = getVertx().eventBus();

        eventBus.registerDefaultCodec(Student.class, new StudentCodec());

        Student message = new Student(1, "yash", "1234567890");

        vertx.setPeriodic(2000, id ->

                eventBus.request("student-message", message, reply ->
                {
                    if (reply.succeeded())
                    {
                        Student replyMessage = (Student) reply.result().body();

                        System.out.println("Received message from receiver " + replyMessage.getName());
                    }
                    else
                    {
                        System.out.println("No reply from receiver");
                    }
                }));

        System.out.println("Sender is ready!");

    }
}
