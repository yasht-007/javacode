package futurepromise;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.file.FileSystem;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PromiseExample extends AbstractVerticle {

    private static final AtomicBoolean status = new AtomicBoolean(false);

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new PromiseExample(), id -> {
            vertx.undeploy(id.result()).onSuccess(ok -> System.out.println(status.get()));
        });

        vertx.close();
    }

    @Override
    public void start() throws Exception {
        Promise<Boolean> promise = Promise.promise();

        FileSystem fs = vertx.fileSystem();

        fs.exists("/home/yash/iop.txt", promise);

        promise.future().onComplete(res -> {
            status.set(res.result());
        });
    }
}
