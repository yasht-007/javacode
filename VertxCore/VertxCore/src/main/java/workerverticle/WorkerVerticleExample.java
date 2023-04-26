package workerverticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.impl.cpu.CpuCoreSensor;

class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("[Main] Running in " + Thread.currentThread().getName());

        DeploymentOptions options = new DeploymentOptions().setWorker(true);

        options.setWorkerPoolSize(1);

        System.out.println(options.getWorkerPoolSize());

        vertx.deployVerticle(new WorkerVerticle(),options, res -> {
            if (res.succeeded()) {
                vertx.undeploy(res.result(), result -> {
                    if (result.succeeded()) {
                        System.out.println("Worker undeployed");
                    }
                });
            }
        });
//
//        vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions().setWorker(true), res -> {
//            if (res.succeeded()) {
//                vertx.undeploy(res.result(), result -> {
//                    if (result.succeeded()) {
//                        System.out.println("Worker undeployed");
//                    }
//                });
//            }
//        });
    }
}

public class WorkerVerticleExample extends AbstractVerticle {

    public static void main(String[] args) {

        System.out.println(CpuCoreSensor.availableProcessors());
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new MainVerticle());
    }

}
