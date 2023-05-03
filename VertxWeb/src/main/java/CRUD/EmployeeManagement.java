package CRUD;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;

public class EmployeeManagement extends AbstractVerticle {

    private static final HashMap<Integer, Employee> employeeDB = new HashMap<>();

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(EmployeeManagement.class.getName(), new DeploymentOptions().setInstances(2));
    }

    @Override
    public void start(Promise<Void> startPromise) {

        Router employee = Router.router(vertx);

        employee.post("/create").handler(this::addStudent);

        employee.get("/getAll").handler(this::getStudents);

        employee.get("/get/:empId").handler(this::getStudent);

        employee.put("/update/:empId").handler(this::updateStudentFields);

        employee.delete("/delete/:empId").handler(this::deleteStudent);

        vertx.createHttpServer().requestHandler(employee).listen(8080).onSuccess(ok -> System.out.println("Server listening at Port 8080"));
    }

    private void addStudent(RoutingContext context) {

        HttpServerRequest request = context.request();

        HttpServerResponse response = context.response();

        request.body().onSuccess(buffer -> {

            JsonObject object = buffer.toJsonObject();

            Employee employee = new Employee(object.getInteger("id"), object.getString("name"), object.getString("department"), object.getLong("salary"));

            employeeDB.put(object.getInteger("id"), employee);

            context.response().setChunked(true).putHeader("content-type", "application/json");

            response.end(new JsonObject().put("status", "success").encodePrettily());

        });

    }

    private void getStudents(RoutingContext context) {

        JsonObject jsonObject = new JsonObject();

        employeeDB.values().forEach(v -> jsonObject.put(String.valueOf(v.getId()), v.toJSON()));

        context.response().setChunked(true).putHeader("content-type", "application/json");

        context.response().setChunked(true).end(jsonObject.encodePrettily());
    }

    private void getStudent(RoutingContext context) {

        int id = Integer.parseInt(context.pathParam("empId"));

        JsonObject object = new JsonObject();

        object.put("data", employeeDB.get(id).toJSON());

        context.response().setChunked(true).putHeader("content-type", "application/json");

        context.response().end(object.encodePrettily());
    }

    private void updateStudentFields(RoutingContext context) {

        int id = Integer.parseInt(context.pathParam("empId"));

        context.request().body().onSuccess(buf -> {

            JsonObject object = buf.toJsonObject();

            object.fieldNames().forEach(change -> {

                if (change.equalsIgnoreCase("department")) {

                    employeeDB.get(id).setDepartment(object.getString("department"));
                }

                if (change.equalsIgnoreCase("salary")) {

                    employeeDB.get(id).setSalary(object.getLong("salary"));
                }

            });

            context.response().setChunked(true).putHeader("content-type", "application/json").end(new JsonObject().put("status", "success").encodePrettily());

        });

    }

    private void deleteStudent(RoutingContext context) {

        int id = Integer.parseInt(context.pathParam("empId"));

        employeeDB.remove(id);

        context.response().setChunked(true).putHeader("content-type", "application/json").end(new JsonObject().put("status", "success").encodePrettily());
    }
}
