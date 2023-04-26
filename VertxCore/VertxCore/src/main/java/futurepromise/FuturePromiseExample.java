package futurepromise;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

public class FuturePromiseExample
{
    public static void main(String[] args)
    {
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

        Future<Void> moveFile = fs.move("/home/yash/coordinationvertex.txt", "/home/yash/Documents/coordinationvertex.txt");

        CompositeFuture.all(fileCreation, dataInsert, moveFile).onComplete(result ->
        {
            if (result.succeeded())
            {
                System.out.println("All file operations done");
            }
            else
            {
                result.cause().printStackTrace();
            }
        });

        Promise<Void> filePromise = Promise.promise();

        vertx.fileSystem().writeFile("/home/yash/promise.txt", Buffer.buffer("Hello from Yash"), filePromise);

        filePromise.future().onComplete(res ->
        {
            if (res.succeeded())
            {
                System.out.println("File written successfully!");
            }
            else
            {
                System.out.println("Failed to write file: " + res.cause().getMessage());
            }
        });


        vertx.close();
    }
}
