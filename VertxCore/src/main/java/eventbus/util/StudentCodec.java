package eventbus.util;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class StudentCodec implements MessageCodec<Student, Student> {

    @Override
    public void encodeToWire(Buffer buffer, Student student) {
        JsonObject json = new JsonObject();

        json.put("id", student.getId());

        json.put("name", student.getName());

        json.put("contactNo", student.getContactNo());

        String jsonData = json.encode();

        buffer.appendInt(jsonData.getBytes().length);

        buffer.appendString(jsonData);
    }

    @Override
    public Student decodeFromWire(int pos, Buffer buffer) {

//        int length = buffer.getInt(pos);

        String jsonStr = buffer.getString(pos + 4, buffer.length());

        JsonObject contentJson = new JsonObject(jsonStr);

        int id = contentJson.getInteger("id");

        String name = contentJson.getString("name");

        String contactNo = contentJson.getString("contactNo");

        return new Student(id, name, contactNo);

    }

    @Override
    public Student transform(Student student) {
        return student;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
