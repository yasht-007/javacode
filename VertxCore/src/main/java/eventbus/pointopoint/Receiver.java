package eventbus.pointopoint;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class Receiver extends AbstractVerticle
{
    @Override
    public void start() throws Exception
    {
        EventBus eventBus = getVertx().eventBus();

        eventBus.localConsumer("ping-message", message ->
        {
            System.out.println(message.body());

            message.reply("Ping Received!");
        });

    }
}
