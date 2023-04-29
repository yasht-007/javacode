package Router;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class PutRequest extends AbstractVerticle
{
    public static void main(String[] args)
    {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(PutRequest.class.getName(), new DeploymentOptions().setInstances(3));

    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {

        Router router = Router.router(vertx);

        router.route(HttpMethod.POST, "/login").handler(PutRequest::handleLogin);

        vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(ok -> System.out.println("Server listening on PORT 8080"));
    }

    private static void handleLogin(RoutingContext context)
    {
        HttpServerResponse response = context.response();

        String password = context.request().getParam("password");

        if (password == null)
        {
            response.setStatusCode(400).end();
        }

        if (password.equals("yash123"))
        {
            response.putHeader("content-type", "text/plain");

            response.putHeader("content-length", "16");

            response.setStatusCode(200).setStatusMessage("Login Successful");

            response.write("Login Successful");

            response.end();
        }
        else
        {
            response.setStatusCode(401).setStatusMessage("Incorrect Password").end();
        }
    }
}
