package http.basic;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new Client());
    }

    @Override
    public void start() throws Exception {

//        HttpClient client = vertx.createHttpClient();
//        client.request(HttpMethod.GET, 8080, "localhost", "/")
//                .compose(req -> req.send()
//                        .compose(resp -> {
//                            System.out.println("Got response " + resp.statusCode());
//                            return resp.body();
//                        })).onSuccess(body -> {
//                    System.out.println("Got data " + body.toString("ISO-8859-1"));
//                }).onFailure(Throwable::printStackTrace);
//

        HttpClient client = vertx.createHttpClient();

        client.request(HttpMethod.GET, 8080, "localhost", "/")
                .compose(HttpClientRequest::send)
                .compose(res -> {
                    System.out.println(res.statusCode());

                    return res.body();
                }).onSuccess(body-> System.out.println(body.toString()));
    }
}
