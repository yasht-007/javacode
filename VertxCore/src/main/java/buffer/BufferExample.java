package buffer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

public class BufferExample extends AbstractVerticle
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new BufferExample()).onSuccess(res ->
        {
            vertx.undeploy(res).onSuccess(resp ->
            {
                vertx.close();
            });
        });
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {
        Buffer buffer = Buffer.buffer();

        buffer.appendString("Yash from Surat").appendInt(7);

        System.out.println(buffer.getString(0, 15));

        buffer.setInt(19, 5);

        System.out.println(buffer.length());

        System.out.println(buffer.getInt(15));

        startPromise.complete();

    }

    @Override
    public void stop() throws Exception
    {
        System.out.println("undeployed");
    }
}
