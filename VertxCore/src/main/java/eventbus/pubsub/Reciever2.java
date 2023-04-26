package eventbus.pubsub;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Reciever2 extends AbstractVerticle
{
    @Override
    public void start() throws Exception
    {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("solana-price-update", message -> System.out.println(message.body()));

        eventBus.consumer("bitcoin-price-update", message -> System.out.println(message.body()));
    }
}
