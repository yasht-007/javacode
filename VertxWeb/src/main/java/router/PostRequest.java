package router;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.nio.file.Files;
import java.nio.file.Path;

public class PostRequest extends AbstractVerticle {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(PostRequest.class.getName(), new DeploymentOptions().setInstances(3));

    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        JsonObject fruits = new JsonObject(new String(Files.readAllBytes(Path.of("/root/IdeaProjects/VertxWeb/src/main/java/Router/Fruits.json"))));

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create().setUploadsDirectory("uploads").setBodyLimit(2147483648L).setMergeFormAttributes(true));

        router.route("/").handler(context ->

                context.response().putHeader("content-type", "text/html").end("""
                        <form action="/getfruit" method="post" enctype="multipart/form-data">
                            <div>
                                <label for="name">Enter Fruit name:</label>
                                <input type="text" id="fruitname" name="name" />
                            </div>
                            <div class="button">
                                <button type="submit">Send</button>
                            </div></form>"""));

        router.route(HttpMethod.POST, "/login").handler(PostRequest::handleLogin);

        router.route(HttpMethod.POST, "/getfruit").handler(context -> {

            context.response().putHeader("content-type", "application/json").setChunked(true);

            String fruit = context.request().getParam("name");

            System.out.println(fruit);

            context.response().write(fruits.getJsonObject(fruit).encodePrettily());

            context.response().end();

        }).failureHandler(context -> context.response().setStatusCode(context.response().getStatusCode()).end());

        router.route("/uploadfile").handler(context ->

                context.response().putHeader("content-type", "text/html").end("""
                        <form action="/uploadstatus" method="post" enctype="multipart/form-data">
                            <div>
                                <label for="name">Select a file:</label>
                                <input type="file" name="file" />
                            </div>
                            <div class="button">
                                <button type="submit">Send</button>
                            </div></form>"""));

//        router.route(HttpMethod.POST, "/fileupload").handler(context -> {
//
//            context.request().setExpectMultipart(true).uploadHandler(uploadHandler -> uploadHandler.streamToFileSystem("uploads/" + uploadHandler.filename()));
//
//            context.request().endHandler(endhandler -> {
//
//                System.out.println("Hello");
//
////                context.response().putHeader("Content-Type", "text/plain");
////
////                context.response().setChunked(true);
////
////                for (FileUpload f : context.fileUploads()) {
////
////                    context.response().write("Filename: " + f.fileName());
////
////                    context.response().write("\n");
////
////                    context.response().write("Size: " + f.size());
////
////                    context.response().end();
////                }
//            });
//        });


        router.route(HttpMethod.POST, "/fileupload").blockingHandler(blockingContext -> {

            blockingContext.response().putHeader("Content-Type", "text/plain");

            blockingContext.response().setChunked(true);

            for (FileUpload f : blockingContext.fileUploads()) {

                blockingContext.response().write("Filename: " + f.fileName());

                blockingContext.response().write("\n");

                blockingContext.response().write("Size: " + f.size());

                blockingContext.response().end();

            }
        });


        vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(ok -> System.out.println("Server listening on PORT 8080"));
    }

    private static void handleLogin(RoutingContext context) {

        HttpServerResponse response = context.response();

        String password = context.request().getParam("password");

        if (password == null) {
            response.setStatusCode(400).end();
        }

        if (password.equals("yash123")) {

            response.putHeader("content-type", "text/plain");

            response.putHeader("content-length", "16");

            response.setStatusCode(200).setStatusMessage("Login Successful");

            response.write("Login Successful");

            response.end();

        } else {
            response.setStatusCode(401).setStatusMessage("Incorrect Password").end();
        }

    }
}
