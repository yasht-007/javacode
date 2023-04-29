package workerverticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.concurrent.TimeUnit;

public class ExecuteBlockingEx extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new ExecuteBlockingEx(),new DeploymentOptions().setMaxWorkerExecuteTime(6000).setMaxWorkerExecuteTimeUnit(TimeUnit.MILLISECONDS));
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.executeBlocking(promise -> {
            try {
                Thread.sleep(12000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            promise.complete("motadata");

        },false, res -> {
            if (res.succeeded()) {
                System.out.println(res.result());
            } else {
                res.cause().printStackTrace();
            }
        });
    }
}
