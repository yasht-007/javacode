package futurepromise;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FuturePromiseExample {
    private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        FileSystem fs = vertx.fileSystem();

//        // Future Composition
//
        Future<Void> future = fs.createFile("/home/yash/vert.txt").compose(data -> fs.writeFile("/home/yash/vert.txt", Buffer.buffer("Hello from yash")));

        future.onComplete(result ->
        {
            if (result.succeeded()) System.out.println("File created and data inserted successfully");

            else result.cause().printStackTrace();
        });

        //Future Coordination ( COmpositeFuture.(All / Any / Join)

        Future<Void> fileCreation = fs.createFile("/home/yash/coordinationvertex.txt");

        Future<Void> dataInsert = fs.writeFile("/home/yash/coordinationvertex.txt", Buffer.buffer("Hello from yash"));

        Future<Void> moveFile = fs.move("/home/yash/coordinationvertex.txt", "/home/yash/Out/coordinationvertex.txt");


        Future<Void> afileCreation = fs.createFile("/home/yash/coordinationvertexa.txt");

        Future<Void> adataInsert = fs.writeFile("/home/yash/coordinationvertexa.txt", Buffer.buffer("Hello from yash"));

        Future<Void> amoveFile = fs.move("/home/yash/coordinationvertexa.txt", "/home/yash/Out/coordinationvertexa.txt");


        Future<Void> bfileCreation = fs.createFile("/home/yash/coordinationvertexb.txt");

        Future<Void> bdataInsert = fs.writeFile("/home/yash/coordinationvertexb.txt", Buffer.buffer("Hello from yash"));

        Future<Void> bmoveFile = fs.move("/home/yash/coordinationvertexb.txt", "/home/yash/Out/coordinationvertexb.txt");


        List<Future> futures = new ArrayList<>(Arrays.asList(fileCreation, dataInsert, moveFile, afileCreation, adataInsert, amoveFile, bfileCreation, bdataInsert, bmoveFile));

        CompositeFuture.all(futures).onComplete(result ->
        {
            if (result.succeeded()) {
                LOG.debug("All file operations done {}", FuturePromiseExample.class.getName());
            } else {
                result.cause().printStackTrace();
            }
        });

        Promise<Void> filePromise = Promise.promise();

        vertx.fileSystem().writeFile("/home/yash/promise.txt", Buffer.buffer("Hello from Yash"), filePromise);

        filePromise.future().onComplete(res ->
        {
            if (res.succeeded()) {
                System.out.println("File written successfully!");
            } else {
                System.out.println("Failed to write file: " + res.cause().getMessage());
            }
        });

        vertx.close();
    }
}
