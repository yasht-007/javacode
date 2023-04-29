package verticletree;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ThirdVerticle extends AbstractVerticle
{
    @Override
    public void start(Promise promise) throws Exception
    {
        System.out.println("Third verticle deployed by " + Thread.currentThread().getName());

        promise.complete();
    }

    @Override
    public void stop(Promise promise) throws Exception
    {
        System.out.println("Undeployed verticle" + deploymentID());

        promise.complete();
    }
}
