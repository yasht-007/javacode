package eventbus.messageencode;

import eventbus.util.Student;
import eventbus.util.StudentCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
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

        Student message = new Student(1, "yash", "7016183012");

        getVertx().deployVerticle(new Receiver(), deployResult ->
        {
            if (deployResult.succeeded())
            {

                getVertx().setPeriodic(3000, id ->

                        eventBus.request("student-message", message,new DeliveryOptions().setSendTimeout(2000)).onSuccess(reply ->
                        {
                            Student replyMessage = (Student) reply.body();

                            System.out.println("Received message from receiver " + replyMessage.getName());
                        }));
            }

            else
            {
                deployResult.cause().printStackTrace();
            }

        });

    }
}
