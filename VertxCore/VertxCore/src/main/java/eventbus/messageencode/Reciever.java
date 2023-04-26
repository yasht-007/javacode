package eventbus.messageencode;

import eventbus.util.Student;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

class Receiver extends AbstractVerticle
{

    @Override
    public void start() throws Exception
    {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("student-message", message ->
        {
            Student student = (Student) message.body();

            System.out.println("Student Message received from sender " + student.getName());

            message.reply(new Student(2, "ayush", "9876543210"));

        });
    }
}
