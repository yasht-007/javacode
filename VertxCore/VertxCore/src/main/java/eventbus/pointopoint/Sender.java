package eventbus.pointopoint;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class Sender extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Sender());

        vertx.deployVerticle(new Receiver());
    }

    @Override
    public void start() throws Exception
    {
        EventBus eventBus = vertx.eventBus();

        vertx.setPeriodic(3000, 2000, id ->
                eventBus.request("ping-message", "Hi from Sender!", reply ->
                {
                    if (reply.succeeded())
                    {
                        System.out.println("Received acknowledgement message from Sender " + reply.result().body());
                    }

                    else
                    {
                        System.out.println("No acknowledgement received from Receiver");
                    }
                }));

    }
}
