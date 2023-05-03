package verticletree;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;


public class VerticleTree extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new VerticleTree()).onSuccess(id -> System.out.println("-----Main Verticle " + id + "-----"));

        vertx.setTimer(10000, handler -> vertx.close());
    }

    @Override
    public void start(Promise promise) throws Exception
    {
        System.out.println("Main verticle deployed by " + Thread.currentThread().getName() + " ");

        vertx.deployVerticle(SecondVerticle.class.getName()).onSuccess(id -> System.out.println("-----Second Verticle 1 " + id + "-----"));

        vertx.deployVerticle(SecondVerticle.class.getName()).onSuccess(id -> System.out.println("-----Second Verticle 2 " + id + "-----"));

        promise.complete();
    }

    @Override
    public void stop(Promise promise) throws Exception
    {
        System.out.println("Undeployed verticle" + deploymentID());

        promise.complete();

    }
}
