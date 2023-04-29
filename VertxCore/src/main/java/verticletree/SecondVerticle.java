package verticletree;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.util.concurrent.atomic.AtomicInteger;

public class SecondVerticle extends AbstractVerticle
{
    private static final AtomicInteger index = new AtomicInteger(1);

    @Override
    public void start(Promise promise)
    {
        System.out.println("Second verticle deployed by " + Thread.currentThread().getName());

        vertx.deployVerticle(ThirdVerticle.class.getName()).onSuccess(id -> System.out.println("-----Third Verticle " + (index.getAndIncrement()) + " " + id + "-----"));

        vertx.deployVerticle(ThirdVerticle.class.getName()).onSuccess(id -> System.out.println("-----Third Verticle " + (index.getAndIncrement()) + " " + id + "-----"));

        promise.complete();

    }

    @Override
    public void stop(Promise promise) throws Exception
    {
        System.out.println("Undeployed verticle" + deploymentID());

        promise.complete();

    }
}
