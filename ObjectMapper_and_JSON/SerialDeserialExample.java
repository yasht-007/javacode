package org.example;

import java.io.*;

class Student implements Serializable {
    int id;
    String name;

    Student() {
    }

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class SerialDeserialExample {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student s1 = new Student(211, "ravi");

        // To Serialize
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/home/yash/Java/ser.txt"));
        out.writeObject(s1);
        out.flush();
        out.close();

        // To Deserialize

        ObjectInputStream oin = new ObjectInputStream(new FileInputStream("/home/yash/Java/ser.txt"));
        Student oS= (Student) oin.readObject();
        System.out.println(oS.id+" "+oS.name);

        oin.close();

    }
}
