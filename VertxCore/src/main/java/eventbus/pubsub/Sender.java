package eventbus.pubsub;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Sender extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Sender());

        vertx.deployVerticle(new Receiver());

        vertx.deployVerticle(new Reciever2());
    }

    @Override
    public void start() throws Exception
    {
        AtomicInteger sol = new AtomicInteger(21);

        AtomicLong btc = new AtomicLong(28000);

        EventBus eventBus = vertx.eventBus();

        vertx.setPeriodic(0,1000, message -> eventBus.publish("solana-price-update", sol.getAndIncrement()));

        vertx.setPeriodic(0,1000, message -> eventBus.publish("bitcoin-price-update", btc.addAndGet(300)));
    }
}
