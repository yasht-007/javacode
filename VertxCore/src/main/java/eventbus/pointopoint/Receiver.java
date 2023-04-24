package eventbus.pointopoint;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class Receiver extends AbstractVerticle
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

        eventBus.consumer("ping-message", message ->
        {
            System.out.println(message.body());

            message.reply("Ping Received!");
        });
    }
}
