package authentication;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class JwtPem extends AbstractVerticle {
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(JwtPem.class.getName());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        Future<Buffer> privateFuture = vertx.fileSystem().readFile("/home/yash/Desktop/New Folder/private_key.pem");

        Future<Buffer> publicFuture = vertx.fileSystem().readFile("/home/yash/Desktop/New Folder/public.pem");

        CompositeFuture.all(privateFuture, publicFuture).onComplete(handler ->
        {
            if (handler.succeeded()) {

                JWTAuth provider = JWTAuth.create(vertx, new JWTAuthOptions()
                        .addPubSecKey(new PubSecKeyOptions()
                                .setAlgorithm("RS256")
                                .setBuffer(handler.result().resultAt(0).toString()))
                        .addPubSecKey(new PubSecKeyOptions()
                                .setAlgorithm("RS256")
                                .setBuffer(handler.result().resultAt(1).toString())));

                String token = provider.generateToken(new JsonObject().put("some", "token-data"), new JWTOptions().setAlgorithm("RS256"));

                System.out.println(token);

                startPromise.complete();

            } else {
                handler.cause().printStackTrace();
            }
        });

    }
}
