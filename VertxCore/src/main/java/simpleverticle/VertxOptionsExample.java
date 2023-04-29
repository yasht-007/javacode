package simpleverticle;

import io.vertx.core.*;

import java.util.concurrent.TimeUnit;

public class VertxOptionsExample extends AbstractVerticle {

    public static void main(String[] args) {

        io.vertx.core.VertxOptions vertxOptions = new io.vertx.core.VertxOptions();

        vertxOptions.setBlockedThreadCheckInterval(5000)
                .setBlockedThreadCheckIntervalUnit(TimeUnit.MILLISECONDS)
                .setHAEnabled(true)
                .setInternalBlockingPoolSize(5)
                .setEventLoopPoolSize(3)
                .setMaxEventLoopExecuteTime(4000000000L)
                .setHAEnabled(true)
                .setWorkerPoolSize(30);

        Vertx vertx = Vertx.vertx(vertxOptions);

        vertx.deployVerticle(new VertxOptionsExample());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
       startPromise.complete();
    }
}
