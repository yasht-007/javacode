package eventbus;

import eventbus.util.Student;
import eventbus.util.StudentCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

class Receiver extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Receiver());
    }

    @Override
    public void start() throws Exception
    {
        EventBus eventBus = vertx.eventBus();

        eventBus.registerDefaultCodec(Student.class, new StudentCodec());


        eventBus.consumer("student-message", message ->
        {
            Student student = (Student) message.body();

            System.out.println("Student Message received from sender " + student.getName());

            message.reply(new Student(2, "ayush", "9876543210"));
        });

        System.out.println("Receiver is ready!");
    }
}
