package simpleverticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

class SimpleVerticle extends AbstractVerticle {

    @Override
    public void start(Promise startPromise) {

        System.out.println("Startup tasks are now complete, VerticleExample is now started!");

        vertx.setTimer(4000, id -> {
            System.out.println("timer task 4 seconds");
        });

        vertx.setPeriodic(0, 3000, id -> {
            System.out.println("Periodic timer every 3 seconds");
        });

        System.out.println(config());

        startPromise.complete();
    }

    @Override
    public void stop() {
        System.out.println("Cleanup tasks are now complete, VerticleExample is now stopped!");

    }
}
