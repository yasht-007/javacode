package simpleverticle;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

public class SimpleVerticleExample {
    public static void main(String[] args) throws InterruptedException {

        Vertx vertx = Vertx.vertx();

//        vertx.deployVerticle(new VerticleExample(), res -> {
//            if (res.succeeded()) {
//                String id = res.result();
//
//                System.out.println(id);
//
//                vertx.undeploy(id, response -> {
//                    if (response.succeeded()) {
//                        System.out.println("undeploy done");
//                    } else {
//                        System.out.println("Undeploy failed");
//                    }
//                });
//            }
//        });

        JsonObject config = new JsonObject().put("foo", "bar");

        vertx.deployVerticle(new SimpleVerticle());
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
