package authentication;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;


public class JwtExample extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(JwtExample.class.getName());
    }

    @Override
    public void start() {

        Router router = Router.router(vertx);

        JWTAuth jwt = JWTAuth.create(vertx, new JWTAuthOptions()
                .setKeyStore(new KeyStoreOptions()
                        .setType("jceks")
                        .setPath("keystore.jceks")
                        .setPassword("secret")));

        router.get("/api/newToken").handler(ctx -> {

            ctx.response().putHeader("Content-Type", "text/plain");

            ctx.response().end(jwt.generateToken(new JsonObject(), new JWTOptions().setExpiresInSeconds(60)));
        });

        router.route("/api/*").handler(JWTAuthHandler.create(jwt));

        router.get("/api/login").handler(ctx -> {

            ctx.response().putHeader("Content-Type", "text/plain");

            ctx.response().end("a secret you should keep for yourself...");
        });

        router.route().handler(StaticHandler.create());

        vertx.createHttpServer().requestHandler(router).listen(8080);

    }
}
