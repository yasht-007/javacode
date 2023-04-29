package simpleverticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SimpleVerticle extends AbstractVerticle {

    @Override
    public void start(Promise startPromise) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());

        String PATH = "/home/yash/demo3.txt";

        final List<String> fruits = new ArrayList<>(Arrays.asList("Apple", "Mango", "Banana"));

        FileSystem fs = vertx.fileSystem();

        System.out.println("Verticle is now started!" + Thread.currentThread().getName());

        vertx.executeBlocking(promise -> {

            fs.createFile(PATH, "rwxrw-r--").compose(data -> {

                System.out.println("step1");

                return fs.writeFile(PATH, Buffer.buffer("List of Fruits :")).onSuccess(h -> fs.open(PATH, new OpenOptions().setAppend(true), res -> {

                    AsyncFile as = res.result();

                    fruits.forEach(fruit -> {

                        Buffer chunk = Buffer.buffer(fruit);

                        as.write(chunk);

                    });
                }));

            }).onComplete(h -> {
                if (h.succeeded()) {

                } else {
                    System.out.println("fail");
                }
            });

            promise.complete("All operations successfully done");

        }, true, res -> {

            if (res.succeeded()) {

                System.out.println(res.result());

            } else {

                res.cause().printStackTrace();

            }
        });

//        vertx.setTimer(4000, id -> {
//            System.out.println("timer task 4 seconds");
//        });
//
//        vertx.setPeriodic(0, 3000, id -> {
//            System.out.println("Periodic timer every 3 seconds");
//        });

//        System.out.println(config());

        startPromise.complete();

    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {

        stopPromise.complete();
    }
}
