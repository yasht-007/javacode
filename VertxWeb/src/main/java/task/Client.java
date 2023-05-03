package task;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    @Override
    public void start() throws Exception {

        vertx.createHttpClient().request(HttpMethod.POST, 8080, "localhost", "/", res -> {

            FileSystem fs = vertx.fileSystem();

            Buffer buffer = Buffer.buffer();

            AsyncFile file = fs.openBlocking("/home/yash/demo3.txt", new OpenOptions());

            file.handler(buffer::appendBuffer).endHandler(handler -> res.result().send(buffer).onSuccess(ok -> System.out.println(buffer.length())));

        });
    }
}
