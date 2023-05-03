package CRUD;

import io.vertx.core.json.JsonObject;

public class Employee {

    private final int id;
    private final String name;
    private String department;


    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    private long salary;

    public Employee(int id, String name, String department, long salary) {
        this.id = id;

        this.name = name;

        this.department = department;

        this.salary = salary;
    }

    public JsonObject toJSON() {

        JsonObject object = new JsonObject();

        return object.put("id", getId()).put("name", getName()).put("department", getDepartment()).put("salary", getSalary());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public long getSalary() {
        return salary;
    }
}
