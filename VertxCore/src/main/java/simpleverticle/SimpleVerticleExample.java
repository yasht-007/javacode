package simpleverticle;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleVerticleExample {


    public static void main(String[] args) throws InterruptedException {

        Vertx vertx = Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(3).setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS));

        vertx.deployVerticle(new SimpleVerticle());

        JsonObject config = new JsonObject().put("foo", "bar");


//        vertx.deployVerticle(SimpleVerticle.class.getName(),new DeploymentOptions().setInstances(3));
//
//        DeploymentOptions options = new DeploymentOptions().setInstances(6);
//
//        vertx.deployVerticle("SimpleVerticle.SimpleVerticle", options);
        // Deploy 10 instances

//        System.out.println(SimpleVerticle.class.getCanonicalName());
//        vertx.deployVerticle(SimpleVerticle.class.getCanonicalName(), new DeploymentOptions().setInstances(5), event -> System.out.println(event.result()));


//        vertx.close();

    }
}
